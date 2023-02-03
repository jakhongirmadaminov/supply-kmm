package uz.uzkass.smartpos.supply.android.ui.main.create_order

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun CreateOrderScreen() {

    CreateOrderScreenView()

}

@Composable
private fun CreateOrderScreenView() {
    val verticalScrollState = rememberScrollState()

    Box(modifier = Modifier.fillMaxSize()) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .verticalScroll(verticalScrollState)
        )
        {



        }

    }


}