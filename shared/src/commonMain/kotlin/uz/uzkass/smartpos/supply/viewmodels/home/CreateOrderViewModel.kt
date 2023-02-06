package uz.uzkass.smartpos.supply.viewmodels.home

import dev.icerock.moko.mvvm.viewmodel.ViewModel
import dev.icerock.moko.network.generated.apis.MobileBranchResourceApi
import dev.icerock.moko.network.generated.apis.MobileContractResourceApi
import dev.icerock.moko.network.generated.apis.MobileCustomerResourceApi
import dev.icerock.moko.network.generated.apis.MobileWarehouseResourceApi
import dev.icerock.moko.network.generated.apis.PublicOrderResourceApiImpl
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import uz.uzkass.smartpos.supply.core.utils.resultOf
import uz.uzkass.smartpos.supply.viewmodels.home.model.DropdownModel

class CreateOrderViewModel constructor(
    val customerApi: MobileCustomerResourceApi,
    val branchResourceApi: MobileBranchResourceApi,
    val contractResourceApi: MobileContractResourceApi,
    val publicOrderResourceApiImpl: PublicOrderResourceApiImpl,
    val warehouseResourceApi: MobileWarehouseResourceApi
) : ViewModel() {

    private val _loading = MutableStateFlow(false)
    val loading get() = _loading.asStateFlow()

    private val _showError = MutableStateFlow(false)
    val showError get() = _showError.asStateFlow()

    private val _screenStateFlow = MutableStateFlow(CreateOrderScreenState())
    val screenStateFlow get() = _screenStateFlow.asStateFlow()


    private var customer: DropdownModel? = null
    private var contract: DropdownModel? = null
    private var sellType: DropdownModel? = null

    private var branch: DropdownModel? = null
    private var storage: DropdownModel? = null

    fun getCustomerByQuery(query: String = "") {
        viewModelScope.launch {
            resultOf {
                customerApi.lookUpUsingGET68(search = query)
            }.onSuccess {
                val temp = it.map { item ->
                    DropdownModel(
                        id = item.id.toString(),
                        label = item.name ?: ""
                    )
                }
                _screenStateFlow.update {
                    it.copy(
                        customerList = temp
                    )
                }

            }.onFailure {

            }
        }

    }

    fun getContractByQuery(query: String = "") {
        viewModelScope.launch {
            resultOf {
                contractResourceApi.lookUpUsingGET67(
                    search = query,
                    customerId = customer?.id?.toLongOrNull()
                )
            }.onSuccess {
                val temp = it.map { item ->
                    DropdownModel(
                        id = item.id.toString(),
                        label = item.contractNumber ?: ""
                    )
                }
                _screenStateFlow.update {
                    it.copy(
                        contractList = temp
                    )
                }

            }.onFailure {

            }
        }

    }


    fun getSellType() {
        viewModelScope.launch {
            resultOf {
                publicOrderResourceApiImpl.getSaleTypesUsingGET3()
            }.onSuccess {

                val temp = it.map { item ->
                    DropdownModel(
                        id = item.code.toString(),
                        label = item.name.toString()
                    )
                }
                _screenStateFlow.update {
                    it.copy(
                        sellTypeList = temp
                    )
                }
            }.onFailure {

            }.let {

            }
        }

    }

    fun getBranchByQuery(query: String = "") {
        viewModelScope.launch {
            resultOf {
                branchResourceApi.lookUpUsingGET66(search = query)
            }.onSuccess {
                val temp = it.map { item ->
                    DropdownModel(
                        id = item.id.toString(),
                        label = item.name.toString()
                    )
                }

                _screenStateFlow.update {
                    it.copy(
                        branchList = temp
                    )
                }
            }.onFailure {

            }.let {

            }

        }
    }

    fun getStoreByQuery(query: String = "") {
        viewModelScope.launch {
            resultOf {
                warehouseResourceApi.lookUpUsingGET69(search = query)
            }.onSuccess {
                val temp = it.map { item ->
                    DropdownModel(
                        id = item.id.toString(),
                        label = item.name.toString()
                    )
                }

                _screenStateFlow.update {
                    it.copy(
                        storageList = temp
                    )
                }
            }.onFailure {

            }.let {

            }

        }
    }

    fun selectCustomer(newCustomer: DropdownModel) {


    }


    fun selectContract(newContract: DropdownModel) {


    }


    fun selectBranch(newBranch: DropdownModel) {


    }


    fun selectStorage(newStorage: DropdownModel) {


    }

}

data class CreateOrderScreenState(
    val customerList: List<DropdownModel>? = null,
    val contractList: List<DropdownModel>? = null,

    val sellTypeList: List<DropdownModel>? = null,

    val branchList: List<DropdownModel> = emptyList(),
    val storageList: List<DropdownModel> = emptyList(),


    )