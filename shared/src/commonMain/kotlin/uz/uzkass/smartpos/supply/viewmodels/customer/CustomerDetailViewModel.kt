package uz.uzkass.smartpos.supply.viewmodels.customer

import dev.icerock.moko.mvvm.viewmodel.ViewModel
import dev.icerock.moko.network.generated.apis.MobileCustomerResourceApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import uz.uzkass.smartpos.supply.core.utils.resultOf
import uz.uzkass.smartpos.supply.viewmodels.HomeDataState

class CustomerDetailViewModel
constructor(private val customerApi: MobileCustomerResourceApi) : ViewModel() {

    private val _screenState = MutableStateFlow(CustomerDetailState())
    val screenState get() = _screenState.asStateFlow()

    fun getCustomerById(customerId: Long) {

        viewModelScope.launch {
            _screenState.update {
                it.copy(loading = true)
            }
            resultOf {
                customerApi.getUsingGET95(customerId)
            }.onSuccess { detailInfo ->

                _screenState.update {
                    it.copy(
                        loading = false,
                        tin = detailInfo.tin ?: "",
                        name = detailInfo.name ?: "",
                        brand = detailInfo.brand ?: "",
                        activityType = detailInfo.activityType?.name ?: "",

                    )
                }

            }.onFailure {
                _screenState.update {
                    it.copy(loading = false)
                }
            }
        }
    }
}

data class CustomerDetailState(
    val loading: Boolean = false,
    val tin: kotlin.String = "",
    val name: kotlin.String = "",
    val brand: kotlin.String = "",
    val activityType: String = "",

//
//    val accountant: kotlin.String? = null,
//
//    val address: AddressDetailDTO? = null,
//    val bankDetail: CompanyBankDetailDTO? = null,
//
//    val contactPerson: kotlin.String? = null,
//    val director: kotlin.String? = null,
//    val financialDiscountProduct: ProductLookUpDTO? = null,
//    val hasFinancialDiscount: kotlin.Boolean? = null,
//    val id: kotlin.Long? = null,
//
//    val phone: kotlin.String? = null,

)