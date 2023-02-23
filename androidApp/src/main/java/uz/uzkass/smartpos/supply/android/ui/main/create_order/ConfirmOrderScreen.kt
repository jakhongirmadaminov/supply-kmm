package uz.uzkass.smartpos.supply.android.ui.main.create_order

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import org.koin.androidx.compose.koinViewModel
import uz.uzkass.smartpos.supply.android.coreui.LabelText
import uz.uzkass.smartpos.supply.android.coreui.SupplyFilledTextButton
import uz.uzkass.smartpos.supply.android.coreui.SupplyTextButton
import uz.uzkass.smartpos.supply.android.ui.theme.LocalShapes
import uz.uzkass.smartpos.supply.viewmodels.ConfirmOrderViewModel

@Composable
@Destination
fun ConfirmOrderScreen(
    navigator: DestinationsNavigator,

    viewModel: ConfirmOrderViewModel = koinViewModel()
) {
    ConfirmOrderScreenView(onConfirm = {
        viewModel.confirmOrder()
    },
        onSaveTo = {

        })


}

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun ConfirmOrderScreenView(onConfirm: () -> Unit, onSaveTo: () -> Unit) {
    Scaffold(topBar = {}) {
        Column(modifier = Modifier.fillMaxSize()) {

            DisableTextWithLabel(
                label = "",
                text = ""
            )

            DisableTextWithLabel(
                label = "",
                text = ""
            )

            SupplyFilledTextButton(
                text = "Confirm",
                onClick = onConfirm
            )

            SupplyTextButton(
                text = "save to ",
                onClick = onSaveTo
            )
        }
    }

}

@Composable
private fun DisableTextWithLabel(
    modifier: Modifier = Modifier,
    label: String, text: String
) {

    Column(modifier = modifier) {
        LabelText(label = label)

        Text(
            modifier = Modifier
                .fillMaxWidth()
                .background(
                    color = Color(0xFFF2F4F7),
                    shape = LocalShapes.current.small8Dp
                ),
            text = text,
            fontSize = 16.sp,
            fontWeight = FontWeight.W500
        )
    }

}