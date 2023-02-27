package uz.uzkass.smartpos.supply.android.ui.main.create_order

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.RadioButton
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import org.koin.androidx.compose.koinViewModel
import uz.uzkass.smartpos.supply.android.R
import uz.uzkass.smartpos.supply.android.coreui.AppBarButton
import uz.uzkass.smartpos.supply.android.coreui.AppBarTitle
import uz.uzkass.smartpos.supply.android.coreui.DefaultAppBar
import uz.uzkass.smartpos.supply.android.coreui.DividerMin
import uz.uzkass.smartpos.supply.android.coreui.FillAvailableSpace
import uz.uzkass.smartpos.supply.android.coreui.LabelText
import uz.uzkass.smartpos.supply.android.coreui.Spacer16dp
import uz.uzkass.smartpos.supply.android.coreui.Spacer3dp
import uz.uzkass.smartpos.supply.android.coreui.Spacer8dp
import uz.uzkass.smartpos.supply.android.coreui.SupplyFilledTextButton
import uz.uzkass.smartpos.supply.android.coreui.SupplyTextButton
import uz.uzkass.smartpos.supply.android.ui.theme.LocalShapes
import uz.uzkass.smartpos.supply.viewmodels.ConfirmOrderState
import uz.uzkass.smartpos.supply.viewmodels.ConfirmOrderViewModel
import uz.uzkass.smartpos.supply.viewmodels.profil.ProfileScreenState

@Composable
@Destination
fun ConfirmOrderScreen(
    navigator: DestinationsNavigator,

    viewModel: ConfirmOrderViewModel = koinViewModel()
) {

    val screenState = viewModel.screenState.collectAsState()


    ConfirmOrderScreenView(
        screenState = screenState.value,
        onConfirm = {
            viewModel.confirmOrder()
        },
        onSaveTo = {

        },
        onClickBack = navigator::popBackStack
    )


}

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun ConfirmOrderScreenView(
    screenState: ConfirmOrderState,
    onConfirm: () -> Unit,
    onSaveTo: () -> Unit,
    onClickBack: () -> Unit
) {
    Scaffold(topBar = {
        ConfirmOrderAppBar(onClickBack)
    }) {

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {

            Row(modifier = Modifier.fillMaxWidth()) {
                Text(text = "Кол-во товаров:")
                FillAvailableSpace()
                Text(text = screenState.productCount)
            }

            Spacer8dp()
            DividerMin()
            Spacer8dp()

            Row(modifier = Modifier.fillMaxWidth()) {
                Text(text = "Общая сумма:")
                FillAvailableSpace()
                Text(text = screenState.summa)
            }

            Spacer3dp()

            Row(modifier = Modifier.fillMaxWidth()) {
                Text(text = "НДС 10%")
                FillAvailableSpace()
                Text(text = screenState.vatAmount)
            }

            Spacer16dp()

            ChoosePaymentTypeRadioButton(onItemSelect = {

            })

            FillAvailableSpace()
            SupplyFilledTextButton(
                text = "Оформить заказ",
                onClick = onConfirm
            )

            SupplyTextButton(
                text = "Сохранить черновик",
                onClick = onSaveTo
            )
        }
    }

}

const val firstRadioId = "firstRadioId"
const val secondRadioId = "secondRadioId"
const val threeRadioId = "threeRadioId"

@Composable
fun ChoosePaymentTypeRadioButton(onItemSelect: (String) -> Unit) {
    var currentRadio by remember {
        mutableStateOf(firstRadioId)
    }

    Column(modifier = Modifier.fillMaxWidth()) {
        Text(text = "Тип оплаты")
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            RadioButton(modifier = Modifier,
                selected = currentRadio == firstRadioId,
                onClick = {
                    onItemSelect(firstRadioId)
                    currentRadio = firstRadioId
                })
            Text(text = "Консигнация")
            FillAvailableSpace()
            RadioButton(modifier = Modifier,
                selected = currentRadio == secondRadioId,
                onClick = {
                    onItemSelect(secondRadioId)
                    currentRadio = secondRadioId
                })
            Text(text = "Другое")
            FillAvailableSpace()
        }
        Row(modifier = Modifier, verticalAlignment = Alignment.CenterVertically) {
            RadioButton(modifier = Modifier,
                selected = currentRadio == threeRadioId,
                onClick = {
                    onItemSelect(threeRadioId)
                    currentRadio = threeRadioId
                })

            Text(text = "Предоплата")
        }
    }
}


@Composable
private fun ConfirmOrderAppBar(onClickBack: () -> Unit) {
    DefaultAppBar {
        AppBarButton(
            painter = painterResource(id = R.drawable.ic_back),
            onClick = onClickBack
        )
        Spacer16dp()
        AppBarTitle(
            modifier = Modifier.weight(1f),
            title = "Confirm Order"
        )

    }
}


@Composable
fun DisableTextWithLabel(
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