package uz.uzkass.smartpos.supply.android.ui.main.create_order

import android.util.Log
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import dev.icerock.moko.network.generated.models.CompanyBaseDTO
import org.koin.androidx.compose.koinViewModel
import uz.uzkass.smartpos.supply.android.coreui.Spacer16dp
import uz.uzkass.smartpos.supply.android.coreui.textfields.SearchTextField
import uz.uzkass.smartpos.supply.viewmodels.home.SelectCustomerViewModel

@Destination
@Composable

fun SelectCustomerScreen(
    navigator: DestinationsNavigator,

    ) {
    val viewModel: SelectCustomerViewModel = koinViewModel()
    val screenState = viewModel.screenStateFlow.collectAsState()

    SelectCustomerView(
        customerList = screenState.value.customerList,
        onQueryChange = {
            Log.d("TTT", "SelectCustomerScreen:${it} ")
            viewModel.getCustomerByQuery(it)
        },
        onItemClick = { item ->

        }
    )


}

@Composable
private fun SelectCustomerView(
    customerList: List<CompanyBaseDTO>,
    onQueryChange: (String) -> Unit,
    onItemClick: (CompanyBaseDTO) -> Unit
) {

    Box(
        modifier = Modifier
            .fillMaxSize()
            .systemBarsPadding()
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            SearchTextField(
                modifier = Modifier.fillMaxWidth(),
                onQueryChange = onQueryChange
            )
            Spacer16dp()
            LazyColumn(verticalArrangement = Arrangement.spacedBy(8.dp), content = {
                items(customerList) { item ->
                    CustomerItem(item = item, onItemClick)
                }
            })
        }

    }


}

@Composable
fun CustomerItem(item: CompanyBaseDTO, onClick: (CompanyBaseDTO) -> Unit) {
    Column(modifier = Modifier.clickable(onClick = { onClick(item) })) {
        Text(

            text = "${item.name}", color = Color.Black
        )
        Text(
            text = "${item.tin}", color = Color.Black
        )
    }

}