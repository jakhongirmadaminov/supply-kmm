package uz.uzkass.smartpos.supply.android.ui.viewmodels.createorder

import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import dev.icerock.moko.network.generated.apis.MobileProductResourceApi
import dev.icerock.moko.network.generated.models.ProductDetailMobileDTO
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import uz.uzkass.smartpos.supply.core.utils.resultOf
import uz.uzkass.smartpos.supply.settings.LocalProductRepository
import uz.uzkass.smartpos.supply.settings.OrderProductModel
import uz.uzkass.smartpos.supply.viewmodels.home.SelectCustomerScreenState

class SelectedProductsViewModel constructor(
    private val productApi: MobileProductResourceApi,
    private val localProductRepository: LocalProductRepository
) :
    ViewModel() {

    private val _productDateState = MutableStateFlow(SelectedProductsState())
    val productDateState: StateFlow<SelectedProductsState> = _productDateState

    init {
        Log.d("TTT", localProductRepository.toString())
        getSelectedProductList()
    }

    fun getSelectedProductList() {
        _productDateState.update {
            it.copy(
                productList = localProductRepository.getProducts()
            )
        }
    }

}


data class SelectedProductsState(
    val loading: Boolean = false,
    val productList: List<OrderProductModel> = emptyList()

)