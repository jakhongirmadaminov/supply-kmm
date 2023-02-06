package uz.uzkass.smartpos.supply.android.ui.main.create_order

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
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import org.koin.androidx.compose.koinViewModel
import uz.uzkass.smartpos.supply.android.coreui.FillAvailableSpace
import uz.uzkass.smartpos.supply.android.coreui.SupplyFilledTextButton
import uz.uzkass.smartpos.supply.android.coreui.menu.ExposedDropdownField
import uz.uzkass.smartpos.supply.android.coreui.menu.ExposedDropdownField2
import uz.uzkass.smartpos.supply.android.ui.theme.SupplyTheme
import uz.uzkass.smartpos.supply.viewmodels.home.CreateOrderViewModel
import uz.uzkass.smartpos.supply.viewmodels.home.model.DropdownModel
import uz.uzkassa.smartpos.supply.library.MR

@Composable
@Destination
fun CreateOrderScreen(
    navigator: DestinationsNavigator,
    viewModel: CreateOrderViewModel = koinViewModel()
) {
    val screenState = viewModel.screenStateFlow.collectAsState()
    CreateOrderScreenView(
        customerList = screenState.value.customerList,
        contractList = screenState.value.contractList,
        sellTypeList = screenState.value.sellTypeList,
        branchList = screenState.value.branchList,
        storeList = screenState.value.storageList,

        selectCustomer = viewModel::selectCustomer,
        selectContract = viewModel::selectContract,
        selectSellType = viewModel::selectSellType,

        selectBranch = viewModel::selectBranch,
        selectStorage = viewModel::selectStorage,

        searchCustomer = viewModel::getCustomerByQuery,
        searchBranch = {}
    )

}

@Composable
private fun CreateOrderScreenView(
    loading: Boolean = false,
    customerList: List<DropdownModel>?,
    contractList: List<DropdownModel>?,
    sellTypeList: List<DropdownModel>?,

    branchList: List<DropdownModel>?,
    storeList: List<DropdownModel>?,

    selectCustomer: (DropdownModel) -> Unit,
    selectContract: (DropdownModel) -> Unit,
    selectSellType: (DropdownModel) -> Unit,

    selectBranch: (DropdownModel) -> Unit,
    selectStorage: (DropdownModel) -> Unit,

    searchCustomer: (String) -> Unit,
    searchBranch: (String) -> Unit,
) {
    val verticalScrollState = rememberScrollState()

    Box(
        modifier = Modifier
            .fillMaxSize()
            .systemBarsPadding()
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .verticalScroll(verticalScrollState)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        )
        {

            customerList?.let { list ->
                ExposedDropdownField2(
                    items = list,
                    label = stringResource(id = MR.strings.client.resourceId),
                    readOnly = false,
                    onItemSelected = selectCustomer,
                    onQueryChange = searchCustomer
                )

            }

            contractList?.let { list ->
                ExposedDropdownField2(
                    items = list,
                    label = stringResource(id = MR.strings.contract.resourceId),
                    onItemSelected = selectContract
                )

            }

            sellTypeList?.let { list ->
                ExposedDropdownField2(
                    items = list,
                    label = stringResource(id = MR.strings.type_sell.resourceId),
                    onItemSelected = selectSellType
                )

            }

            branchList?.let { list ->
                ExposedDropdownField2(
                    items = list,
                    label = stringResource(id = MR.strings.branch.resourceId),
                    readOnly = false,
                    onItemSelected = selectBranch,
                    onQueryChange = searchBranch
                )

            }

            storeList?.let { list ->
                ExposedDropdownField2(
                    items = list,
                    label = stringResource(id = MR.strings.store.resourceId),
                    onItemSelected = selectStorage
                )

            }

            FillAvailableSpace()
            SupplyFilledTextButton(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp),
                enabled = !loading,
                buttonBackgroundColor = SupplyTheme.colors.primary,
                text = stringResource(id = MR.strings.confirm.resourceId),
                onClick = {

                })
        }
    }
}