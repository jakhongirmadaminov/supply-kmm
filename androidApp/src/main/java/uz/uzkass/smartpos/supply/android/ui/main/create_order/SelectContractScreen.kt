package uz.uzkass.smartpos.supply.android.ui.main.create_order

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import org.koin.androidx.compose.koinViewModel
import uz.uzkass.smartpos.supply.android.R
import uz.uzkass.smartpos.supply.android.coreui.AppBarButton
import uz.uzkass.smartpos.supply.android.coreui.AppBarTitle
import uz.uzkass.smartpos.supply.android.coreui.DefaultAppBar
import uz.uzkass.smartpos.supply.android.coreui.FillAvailableSpace
import uz.uzkass.smartpos.supply.android.coreui.Spacer16dp
import uz.uzkass.smartpos.supply.android.coreui.SupplyFilledTextButton
import uz.uzkass.smartpos.supply.android.coreui.menu.ExposedDropdownField2
import uz.uzkass.smartpos.supply.android.ui.destinations.ProductSelectScreenDestination
import uz.uzkass.smartpos.supply.android.ui.theme.SupplyTheme
import uz.uzkass.smartpos.supply.viewmodels.home.CreateOrderViewModel
import uz.uzkass.smartpos.supply.viewmodels.home.model.DropdownModel
import uz.uzkassa.smartpos.supply.library.MR

@Composable
@Destination
fun SelectContractScreen(
    navigator: DestinationsNavigator,
    customerId: Long? = null,
    viewModel: CreateOrderViewModel = koinViewModel()
) {

    LaunchedEffect(key1 = Unit, block = {

        viewModel.getContractByCustomerId(customerId)
    })

    val screenState = viewModel.screenStateFlow.collectAsState()

    var currentContract by remember {
        mutableStateOf<DropdownModel?>(null)
    }

    var currentSellType by remember {
        mutableStateOf<DropdownModel?>(null)
    }

    var currentBranch by remember {
        mutableStateOf<DropdownModel?>(null)
    }

    var currentStore by remember {
        mutableStateOf<DropdownModel?>(null)
    }


    val buttonEnable =
        currentContract != null
                && currentSellType != null
                && currentBranch != null
                && (currentStore != null || screenState.value.storageList?.isEmpty() == true)

    SelectContractScreenView(
        loading = screenState.value.loading,
        buttonEnable = buttonEnable,
        contractList = screenState.value.contractList,
        sellTypeList = screenState.value.sellTypeList,
        branchList = screenState.value.branchList,
        storeList = screenState.value.storageList,

        selectContract = {
            currentContract = it
        },
        selectSellType = {
            currentSellType = it
        },

        selectBranch = {

            currentBranch = it
            viewModel.getStoreByQuery(
                branchId = it.id
            )
        },
        selectStorage = {
            currentStore = it
        },

        searchBranch = {

        },
        nextClick = {

            viewModel.saveToLocal(
                customerId = customerId,
                currentContract?.id,
                currentSellType?.id,
                currentBranch?.id,
                currentStore?.id
            )

            navigator.navigate(ProductSelectScreenDestination)
        },
        onBackPressed = navigator::popBackStack
    )

}

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
private fun SelectContractScreenView(
    loading: Boolean = false,
    buttonEnable: Boolean = false,
    contractList: List<DropdownModel>?,
    sellTypeList: List<DropdownModel>?,

    branchList: List<DropdownModel>?,
    storeList: List<DropdownModel>?,

    selectContract: (DropdownModel) -> Unit,
    selectSellType: (DropdownModel) -> Unit,

    selectBranch: (DropdownModel) -> Unit,
    selectStorage: (DropdownModel) -> Unit,

    searchBranch: (String) -> Unit,
    nextClick: () -> Unit,
    onBackPressed: () -> Unit,
) {
    val verticalScrollState = rememberScrollState()

    Scaffold(
        modifier = Modifier
            .fillMaxSize()
            .systemBarsPadding(),
        topBar = {
            SelectContractAppBar(onBackPressed = onBackPressed)
        }
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        )
        {

            if (!contractList.isNullOrEmpty()) {
                ExposedDropdownField2(
                    items = contractList,
                    label = stringResource(id = MR.strings.contract.resourceId),
                    onItemSelected = selectContract
                )
            }
            if (!sellTypeList.isNullOrEmpty()) {
                ExposedDropdownField2(
                    items = sellTypeList,
                    label = stringResource(id = MR.strings.type_sell.resourceId),
                    onItemSelected = selectSellType
                )
            }

            if (!branchList.isNullOrEmpty()) {
                ExposedDropdownField2(
                    items = branchList,
                    label = stringResource(id = MR.strings.branch.resourceId),
                    onItemSelected = selectBranch,
                )
            }

            if (!storeList.isNullOrEmpty()) {
                ExposedDropdownField2(
                    items = storeList,
                    label = stringResource(id = MR.strings.store.resourceId),
                    onItemSelected = selectStorage
                )
            }

            FillAvailableSpace()
            SupplyFilledTextButton(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp),
                enabled = buttonEnable,
                buttonBackgroundColor = SupplyTheme.colors.primary,
                text = stringResource(id = MR.strings.confirm.resourceId),
                onClick = nextClick
            )
        }
    }
}

@Composable
fun SelectContractAppBar(onBackPressed: () -> Unit) {
    DefaultAppBar {
        AppBarButton(
            painter = painterResource(id = R.drawable.ic_back),
            onClick = onBackPressed
        )
        Spacer16dp()
        AppBarTitle(
            modifier = Modifier.weight(1f),
            title = "Select товар"
        )

    }
}