package uz.uzkass.smartpos.supply.android.coreui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import uz.uzkass.smartpos.supply.android.ui.theme.SupplyTheme
import uz.uzkassa.smartpos.supply.library.MR

@Composable
fun ErrorPagingView(
    message: String,
    onClickRetry: () -> Unit
) {
    Column {
        Text(
            modifier = Modifier.fillMaxWidth(),
            text = message,
            textAlign = TextAlign.Center,
            style = SupplyTheme.typography.subtitle1
        )
        Spacer8dp()
        SupplyFilledTextButton(
            modifier = Modifier.padding(horizontal = 8.dp),
            onClick = onClickRetry,
            text = stringResource(id = MR.strings.retry.resourceId)
        )
    }
}

fun <T : Any> LazyListScope.paginationStates(lazyClients: LazyPagingItems<T>) {
    lazyClients.apply {
        when {
            loadState.refresh is LoadState.Loading -> {
                item {
                    LoadingView()
                    Spacer32dp()
                }
            }
            loadState.append is LoadState.Loading -> {
                item {
                    LoadingView()
                    Spacer32dp()
                }
            }
            loadState.append is LoadState.Error -> {
                val e = lazyClients.loadState.append as LoadState.Error
                item {
                    ErrorPagingView(message = e.error.localizedMessage
                        ?: stringResource(id = MR.strings.something_went_wrong.resourceId),
                        onClickRetry = { retry() }
                    )
                    Spacer32dp()
                }
            }
        }
    }
}