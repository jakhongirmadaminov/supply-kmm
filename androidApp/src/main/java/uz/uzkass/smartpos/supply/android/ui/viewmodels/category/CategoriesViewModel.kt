package uz.uzkass.smartpos.supply.android.ui.viewmodels.category

import dev.icerock.moko.mvvm.viewmodel.ViewModel
import dev.icerock.moko.network.generated.apis.MobileBranchResourceApi
import dev.icerock.moko.network.generated.apis.MobileCategoryResourceApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import uz.uzkass.smartpos.supply.android.ui.viewmodels.category.model.CategoryModel
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
            _screenState.update {
                it.copy(
                    loading = true
                )
            }
            resultOf {
                branchResourceApi.lookUpUsingGET72()
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

                api.getParentListUsingGET1(branchId = branchId, status = "ACTIVE")

            }.onSuccess { list ->
                _screenState.update {
                    it.copy(
                        loading = false,
                        categoryList = list.map {
                            CategoryModel(
                                id = it.id,
                                name = it.name
                            )
                        }
                    )
                }
            }.onFailure {

            }
        }
    }

    fun getSubCategoryList(branchId: Long, parentId: Long? = null) {
        viewModelScope.launch {
            resultOf {

                api.getChildrenUsingGET1(
                    branchId = branchId,
                    parentId = parentId,
                    status = "ACTIVE"
                )

            }.onSuccess { list ->
                list.content
//                _screenState.update {
//                    it.copy(
//                        loading = false,
//                        categoryList = list.map {
//                            CategoryModel(
//                                id = it.id,
//                                name = it.name
//                            )
//                        }
//                    )
//                }
            }.onFailure {

            }
        }
    }

    fun onRefresh() {

//        getBranchList()
    }

    fun selectBranch(newBranch: DropdownModel?) {

        _screenState.update {
            it.copy(
                currentBranch = newBranch
            )
        }
    }

}

data class CategoriesScreenState(
    val loading: Boolean = false,
    val categoryList: List<CategoryModel> = emptyList(),
    val branchList: List<DropdownModel> = emptyList(),
    val currentBranch: DropdownModel? = null
)
