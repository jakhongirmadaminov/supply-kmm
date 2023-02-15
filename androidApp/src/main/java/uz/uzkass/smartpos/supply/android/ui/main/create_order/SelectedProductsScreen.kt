package uz.uzkass.smartpos.supply.android.ui.main.create_order

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator

@Composable
@Destination
fun SelectedProductsScreen(
    navigator: DestinationsNavigator,
) {

//    SelectedProductsView()

}

@Composable
private fun SelectedProductsView(
    selectedProducts: List<String>,
    addProductPress: () -> Unit,
    removeProductPress: (String) -> Unit,
    onClickNext: () -> Unit,
) {

    LazyColumn {
        items(selectedProducts) { item ->
            SelectedItemContent(item = item)
        }
    }

}


@Composable
private fun SelectedItemContent(item: String) {
    Text(text = item)
}



