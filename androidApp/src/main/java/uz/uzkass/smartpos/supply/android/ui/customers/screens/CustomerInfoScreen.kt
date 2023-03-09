package uz.uzkass.smartpos.supply.android.ui.customers.screens

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import org.koin.androidx.compose.koinViewModel
import uz.uzkass.smartpos.supply.android.R
import uz.uzkass.smartpos.supply.android.coreui.AppBarButton
import uz.uzkass.smartpos.supply.android.coreui.AppBarTitle
import uz.uzkass.smartpos.supply.android.coreui.DefaultAppBar
import uz.uzkass.smartpos.supply.android.coreui.FillAvailableSpace
import uz.uzkass.smartpos.supply.android.coreui.LabelText
import uz.uzkass.smartpos.supply.android.coreui.Spacer16dp
import uz.uzkass.smartpos.supply.android.coreui.Spacer3dp
import uz.uzkass.smartpos.supply.android.coreui.SupplyFilledTextButton
import uz.uzkass.smartpos.supply.android.ui.theme.LocalColors
import uz.uzkass.smartpos.supply.viewmodels.customer.CustomerDetailState
import uz.uzkass.smartpos.supply.viewmodels.customer.CustomerDetailViewModel
import uz.uzkassa.smartpos.supply.library.MR

@Composable
@Destination
fun CustomerInfoScreen(
    customerId: Long,
    navigator: DestinationsNavigator,
    viewModel: CustomerDetailViewModel = koinViewModel()
) {

    val screenState = viewModel.screenState.collectAsStateWithLifecycle()

    LaunchedEffect(key1 = Unit, block = {
        viewModel.getCustomerById(customerId)
    })

    CustomerInfoScreenView(
        screenState.value,
        onBackPressed = navigator::popBackStack,
        createOrder = {

        }
    )

}


@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
@Destination
fun CustomerInfoScreenView(
    screenState: CustomerDetailState,
    onBackPressed: () -> Unit,
    createOrder: () -> Unit,
) {
    Scaffold(topBar = {
        CustomerInfoScreenAppBar(
            title = "${screenState.name} ${screenState.tin}",
            onBackPressed = onBackPressed
        )
    }) {

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {

            LabeledText(
                label = stringResource(id = MR.strings.vat.resourceId),
                value = screenState.tin
            )

            LabeledText(
                label = stringResource(id = MR.strings.brand_name.resourceId),
                value = screenState.brand
            )

            LabeledText(
                label = stringResource(id = MR.strings.official_name.resourceId),
                value = screenState.name
            )

            LabeledText(
                label = stringResource(id = MR.strings.activity_type.resourceId),
                value = screenState.activityType
            )

            LabeledText(
                label = stringResource(id = MR.strings.vat.resourceId),
                value = screenState.tin
            )


            FillAvailableSpace()

            SupplyFilledTextButton(
                text = stringResource(id = MR.strings.create_order.resourceId),
                onClick = createOrder
            )

        }
    }

}

@Composable
private fun LabeledText(label: String, value: String) {
    Column(modifier = Modifier.fillMaxWidth()) {
        LabelText(label = label)
        Spacer3dp()
        Text(
            text = value,
            fontSize = 14.sp,
            color = LocalColors.current.subtitle2
        )
    }
}

@Composable
fun CustomerInfoScreenAppBar(
    title: String,
    onBackPressed: () -> Unit
) {
    DefaultAppBar {
        AppBarButton(
            painter = painterResource(id = R.drawable.ic_back),
            onClick = onBackPressed
        )
        Spacer16dp()
        AppBarTitle(
            modifier = Modifier.weight(1f),
            title = title
        )
    }
}