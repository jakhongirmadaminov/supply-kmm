package uz.uzkass.smartpos.supply.viewmodels.home

import dev.icerock.moko.mvvm.viewmodel.ViewModel
import dev.icerock.moko.network.generated.apis.MobileCustomerResourceApi
import dev.icerock.moko.network.generated.models.CompanyBaseDTO
import dev.icerock.moko.network.generated.models.CustomerListMobileDTO
import io.github.aakira.napier.Napier
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import uz.uzkass.smartpos.supply.core.utils.resultOf
import uz.uzkass.smartpos.supply.utils.coroutines.asCommonFlow
import uz.uzkass.smartpos.supply.viewmodels.LoginNavigator
import uz.uzkass.smartpos.supply.viewmodels.home.model.DropdownModel

private const val PAGE_SIZE = 20

class SelectCustomerViewModel constructor(
    val customerApi: MobileCustomerResourceApi,
) : ViewModel() {

    private val _screenStateFlow = MutableStateFlow(SelectCustomerScreenState())
    val screenStateFlow get() = _screenStateFlow.asStateFlow()

    private val _navigate: Channel<Unit> = Channel(Channel.BUFFERED)
    var navigate = _navigate.receiveAsFlow()


    init {
        Napier.d("INIT $this", tag = "TTT")
    }

    fun getCustomerByQuery(newQuery: String = "") {

        Napier.d("newQuery $this", tag = "TTT")

        viewModelScope.launch(Dispatchers.Default) {
            Napier.d(newQuery, tag = "TTT")

            resultOf {
                customerApi.lookUpUsingGET68(search = newQuery)
            }.onSuccess { list ->

                _screenStateFlow.update {
                    it.copy(
                        customerList = list
                    )
                }

            }.onFailure {


            }

        }


//        query = newQuery
//        viewModelScope.launch {
//            Pager(
//                clientScope = viewModelScope,
//                config = pagingConfig,
//                initialKey = 0,
//                getItems = { currentKey, size ->
//                    val response = customerApi.getListUsingGET89(
//                        page = currentKey,
//                        size = size,
//                        search = newQuery
//                    )
//                    val items = response.content ?: emptyList()
//                    PagingResult(
//                        items = items,
//                        currentKey = currentKey,
//                        prevKey = { if (currentKey == 0) null else currentKey - 1 },
//                        nextKey = {
//                            if ((response.totalPages
//                                    ?: 1) > currentKey.plus(1)
//                            ) currentKey.plus(1) else 1
//                        }
//                    )
//                }
//            ).pagingData.cachedIn(viewModelScope).collectLatest {
//                searchCustomerPager.emit(it)
//            }
//        }
    }

    fun selectCustomer(newCustomer: DropdownModel) {

    }


}

data class SelectCustomerScreenState(
    val isLoading: Boolean = false,
    val customerList: List<CompanyBaseDTO> = emptyList(),


    )



//    private val searchQuery = MutableStateFlow("")
//    private val salom = MutableStateFlow("")
//    val pagingConfig = PagingConfig(
//        pageSize = PAGE_SIZE,
//        initialLoadSize = PAGE_SIZE,
//        enablePlaceholders = false,
//    )
//    val paging = combine(searchQuery, salom) { searchQuery, salom ->
//        Pager(
//                clientScope = viewModelScope,
//                config = pagingConfig,
//                initialKey = 0,
//                getItems = { currentKey, size ->
//                    val response = customerApi.getListUsingGET89(
//                        page = currentKey,
//                        size = size,
//                        search = searchQuery
//                    )
//                    val items = response.content ?: emptyList()
//                    PagingResult(
//                        items = items,
//                        currentKey = currentKey,
//                        prevKey = { if (currentKey == 0) null else currentKey - 1 },
//                        nextKey = {
//                            if ((response.totalPages
//                                    ?: 1) > currentKey.plus(1)
//                            ) currentKey.plus(1) else 1
//                        }
//                    )
//                }
//            ).pagingData.cachedIn(viewModelScope)
//
//    }
//

//
//    val searchCustomerPager = MutableStateFlow<PagingData<CustomerListMobileDTO>?>(null)
//