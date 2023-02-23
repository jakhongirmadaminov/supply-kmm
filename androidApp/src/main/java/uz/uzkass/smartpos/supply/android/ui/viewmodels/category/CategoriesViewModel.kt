package uz.uzkass.smartpos.supply.android.ui.viewmodels.category

import dev.icerock.moko.mvvm.viewmodel.ViewModel
import dev.icerock.moko.network.generated.apis.MobileBranchResourceApi
import dev.icerock.moko.network.generated.apis.MobileCategoryResourceApi
import dev.icerock.moko.network.generated.models.CategoryTreeDTO
import dev.icerock.moko.paging.IdComparator
import dev.icerock.moko.paging.IdEntity
import dev.icerock.moko.paging.LambdaPagedListDataSource
import dev.icerock.moko.paging.Pagination
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import uz.uzkass.smartpos.supply.core.utils.resultOf
import uz.uzkass.smartpos.supply.viewmodels.home.model.DropdownModel

class CategoriesViewModel constructor(
    private val api: MobileCategoryResourceApi,
    val branchResourceApi: MobileBranchResourceApi
) : ViewModel() {


    private val _screenState = MutableStateFlow(CategoriesScreenState())
    val screenState get() = _screenState.asStateFlow()

    init {
        getBranchList()
    }

    fun getBranchList() {
        viewModelScope.launch {
            resultOf {
                branchResourceApi.lookUpUsingGET66()
            }.onSuccess { list ->
                _screenState.update {
                    it.copy(
                        loading = false,
                        branchList = list.map {
                            DropdownModel(
                                id = it.id.toString(),
                                label = it.name ?: ""
                            )
                        }
                    )
                }
            }.onFailure {

            }
        }
    }

    fun getCategoryList(branchId: Long? = null) {
        viewModelScope.launch {
            resultOf {

                api.treeListUsingGET5(branchId = branchId, status = "ACTIVE")

            }.onSuccess { list ->
                _screenState.update {
                    it.copy(
                        loading = false,
                        categoryList = list
                    )
                }
            }.onFailure {

            }
        }
    }

    fun getSubCategoryList(branchId: Long, parentId: Long? = null) {
        viewModelScope.launch {
            resultOf {

                api.treeListUsingGET5(branchId = branchId, parentId = parentId, status = "ACTIVE")

            }.onSuccess { list ->
                _screenState.update {
                    it.copy(
                        loading = false,
                        categoryList = list
                    )
                }
            }.onFailure {

            }
        }
    }

}

data class CategoriesScreenState(
    val loading: Boolean = false,
    val categoryList: List<CategoryTreeDTO> = emptyList(),
    val branchList: List<DropdownModel> = emptyList()
)
