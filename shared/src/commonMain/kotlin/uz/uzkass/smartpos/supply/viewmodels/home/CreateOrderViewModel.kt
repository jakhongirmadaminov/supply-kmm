package uz.uzkass.smartpos.supply.viewmodels.home

import dev.icerock.moko.mvvm.viewmodel.ViewModel
import dev.icerock.moko.network.generated.apis.MobileBranchResourceApi
import dev.icerock.moko.network.generated.apis.MobileContractResourceApi
import dev.icerock.moko.network.generated.apis.MobileCustomerResourceApi
import dev.icerock.moko.network.generated.apis.MobileWarehouseResourceApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import public.apis.PublicOrderResourceApi
import uz.uzkass.smartpos.supply.core.utils.resultOf
import uz.uzkass.smartpos.supply.settings.LocalProductRepository
import uz.uzkass.smartpos.supply.viewmodels.home.model.DropdownModel

class CreateOrderViewModel constructor(
    val customerApi: MobileCustomerResourceApi,
    val branchResourceApi: MobileBranchResourceApi,
    val contractResourceApi: MobileContractResourceApi,
    val publicOrderResourceApiImpl: PublicOrderResourceApi,
    val warehouseResourceApi: MobileWarehouseResourceApi,
    val localProductRepository: LocalProductRepository
) : ViewModel() {

    private val _loading = MutableStateFlow(false)
    val loading get() = _loading.asStateFlow()

    private val _showError = MutableStateFlow(false)
    val showError get() = _showError.asStateFlow()

    private val _screenStateFlow = MutableStateFlow(CreateOrderScreenState())
    val screenStateFlow get() = _screenStateFlow.asStateFlow()


    private var customerId: Long? = null

    fun getContractByCustomerId(id: Long? = null) {
        customerId = id
        _screenStateFlow.update {
            it.copy(loading = true)
        }
        getContractByQuery()
        getSellType()
        getBranchByQuery()

    }

    fun getContractByQuery(query: String = "") {

        viewModelScope.launch {
            resultOf {
                contractResourceApi.lookUpUsingGET73(
                    search = query,
                    customerId = customerId
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
                _screenStateFlow.update {
                    it.copy(
                        contractList = emptyList()
                    )
                }
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
                branchResourceApi.lookUpUsingGET72(search = query)
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

    fun getStoreByQuery(branchId: String, query: String = "") {
        viewModelScope.launch {
            resultOf {
                warehouseResourceApi.lookUpUsingGET76(
                    search = query,
                    branchId = branchId.toLongOrNull()
                )
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

    fun saveToLocal(
        customerId: Long?,
    ) {
        localProductRepository.customerId = customerId
        localProductRepository.contractId = screenStateFlow.value.currentContract?.id
        localProductRepository.companyBranchId = screenStateFlow.value.currentBranch?.id
        localProductRepository.companyWarehouseId = screenStateFlow.value.currentWarehouse?.id
        localProductRepository.sellTypeId = screenStateFlow.value.currentSellType?.id
        localProductRepository.orderDate = screenStateFlow.value.currentTime
    }

    fun selectContract(newItem: DropdownModel?) {
        _screenStateFlow.update {
            it.copy(
                currentContract = newItem
            )
        }
    }

    fun selectBranch(newBranch: DropdownModel?) {
        _screenStateFlow.update {
            it.copy(
                currentBranch = newBranch
            )
        }

        newBranch?.id?.let {
            getStoreByQuery(
                branchId = it
            )
        }
    }

    fun selectWarehouse(newWarehouse: DropdownModel?) {
        _screenStateFlow.update {
            it.copy(
                currentWarehouse = newWarehouse
            )
        }
    }

    fun selectSellType(newSellType: DropdownModel?) {
        _screenStateFlow.update {
            it.copy(
                currentSellType = newSellType
            )
        }
    }

    fun selectCurrentTime(newTime: String) {
        _screenStateFlow.update {
            it.copy(
                currentTime = newTime
            )
        }
    }

}

data class CreateOrderScreenState(
    val loading: Boolean = false,
    val contractList: List<DropdownModel>? = null,
    val sellTypeList: List<DropdownModel>? = null,
    val branchList: List<DropdownModel>? = null,
    val storageList: List<DropdownModel>? = null,

    val currentContract: DropdownModel? = null,
    val currentBranch: DropdownModel? = null,
    val currentWarehouse: DropdownModel? = null,
    val currentSellType: DropdownModel? = null,

    val currentTime: String = ""

)