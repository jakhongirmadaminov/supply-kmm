package uz.uzkass.smartpos.supply.viewmodels.clients

import dev.icerock.moko.mvvm.viewmodel.ViewModel
import dev.icerock.moko.network.generated.apis.MobileCustomerResourceApi
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

private const val PAGE_SIZE = 20

class CustomersViewModel(
    private val api: MobileCustomerResourceApi
) : ViewModel() {

    private val _customersEvent = Channel<CustomersEvent>(Channel.BUFFERED)
    val customersEvent = _customersEvent.receiveAsFlow()

    private val _screenState = MutableStateFlow(CustomersState())
    val screenState = _screenState.asStateFlow()

    fun onRefreshCustomers() {
        _screenState.update { it.copy(isRefreshing = true) }
        viewModelScope.launch {
            _customersEvent.send(CustomersEvent.RefreshCustomers)
            delay(500)
            withContext(Main) {
                _screenState.update { it.copy(isRefreshing = false) }
            }
        }
    }

}

data class CustomersState(
    val isRefreshing: Boolean = false
)

sealed class CustomersEvent {
    object RefreshCustomers : CustomersEvent()
}