package uz.uzkass.smartpos.supply.android.ui.main.create_order

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import org.koin.androidx.compose.koinViewModel
import uz.uzkass.smartpos.supply.android.coreui.menu.ExposedDropdownField
import uz.uzkass.smartpos.supply.viewmodels.home.CreateOrderViewModel
import uz.uzkass.smartpos.supply.viewmodels.home.model.DropdownModel

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

        searchCustomer = viewModel::getCustomerByQuery,
        selectCustomer = viewModel::selectCustomer,
        selectContract = viewModel::selectContract,
        selectSellType = viewModel::selectSellType,

        selectBranch = viewModel::selectBranch,
        selectStorage = viewModel::selectStorage,
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

    searchCustomer: (String) -> Unit,

    selectCustomer: (DropdownModel) -> Unit,
    selectContract: (DropdownModel) -> Unit,
    selectSellType: (DropdownModel) -> Unit,

    selectBranch: (DropdownModel) -> Unit,
    selectStorage: (DropdownModel) -> Unit,
) {
    val verticalScrollState = rememberScrollState()

    Box(modifier = Modifier.fillMaxSize()) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .verticalScroll(verticalScrollState)
        )
        {

            customerList?.let { list ->
                ExposedDropdownField(
                    items = list,
                    readOnly = false,
                    onItemSelected = selectCustomer,
                    onQueryChange = searchCustomer
                )

            }

            contractList?.let { list ->
                ExposedDropdownField(
                    items = list,
                    onItemSelected = selectContract
                )

            }

            sellTypeList?.let { list ->
                ExposedDropdownField(
                    items = list,
                    onItemSelected = selectSellType
                )

            }

            branchList?.let { list ->
                ExposedDropdownField(
                    items = list,
                    onItemSelected = selectBranch
                )

            }

            storeList?.let { list ->
                ExposedDropdownField(
                    items = list,
                    onItemSelected = selectStorage
                )

            }

        }
    }
}