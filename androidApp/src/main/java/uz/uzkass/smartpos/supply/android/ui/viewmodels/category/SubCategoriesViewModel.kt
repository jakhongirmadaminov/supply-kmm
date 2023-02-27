package uz.uzkass.smartpos.supply.android.ui.viewmodels.category

import dev.icerock.moko.mvvm.viewmodel.ViewModel
import dev.icerock.moko.network.generated.apis.MobileBranchResourceApi
import dev.icerock.moko.network.generated.apis.MobileCategoryResourceApi
import dev.icerock.moko.network.generated.models.CategoryTreeDTO
import dev.icerock.moko.network.generated.models.CustomerListMobileDTO
import dev.icerock.moko.paging.IdComparator
import dev.icerock.moko.paging.IdEntity
import dev.icerock.moko.paging.LambdaPagedListDataSource
import dev.icerock.moko.paging.Pagination
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import uz.uzkass.smartpos.supply.android.ui.viewmodels.category.model.CategoryModel
import uz.uzkass.smartpos.supply.android.ui.viewmodels.category.model.SubCategoryModel
import uz.uzkass.smartpos.supply.core.utils.resultOf
import uz.uzkass.smartpos.supply.viewmodels.home.model.DropdownModel

class SubCategoriesViewModel constructor(
    private val api: MobileCategoryResourceApi
) : ViewModel() {

    private val _screenState = MutableStateFlow(SubCategoriesScreenState())
    val screenState get() = _screenState.asStateFlow()


    private var _pagedData = MutableStateFlow<List<SubCategoryModel>>(listOf())

    val pagedData: StateFlow<List<SubCategoryModel>>
        get() = _pagedData.asStateFlow()

    var pagination: Pagination<SubCategoryModel> = createPagination()

    private var pageIndex = 0
    var searchQuery: String? = null
    var branchId: Long? = null
    var categoryId: Long? = null
    private fun createPagination() = Pagination(
        parentScope = viewModelScope,
        dataSource = LambdaPagedListDataSource { currentPage ->
            var somt: List<SubCategoryModel> = listOf()
            resultOf {
                api.getChildrenUsingGET1(
                    page = pageIndex,
                    search = searchQuery,
                    parentId = categoryId,
                    branchId = branchId,
                    status = "ACTIVE"
                ).content!!
            }.onSuccess {
                somt = it.map { item ->
                    SubCategoryModel(
                        id = item.id,
                        name = item.name,
                        category = item.category
                    )
                }
                pageIndex++
            }.onFailure {
                println("PAGINATION ERROR ${it.message}")
            }

            currentPage?.let {
                val merged = currentPage.plus(somt)
                _pagedData.emit(merged)
                merged
            } ?: run {
                _pagedData.emit(somt)
                somt
            }
        },
        comparator = { a: SubCategoryModel, b: SubCategoryModel ->
            if (a.id == b.id) 0 else 1
        },
        nextPageListener = { result: Result<List<SubCategoryModel>> ->
            if (result.isSuccess) {
                println("Next page successful loaded")
            } else {
                println("Next page loading failed")
            }
        },
        refreshListener = { result: Result<List<SubCategoryModel>> ->
            if (result.isSuccess) {
                println("Refresh successful")
            } else {
                println("Refresh failed")
            }
        },
        initValue = listOf()
    )


    fun getSubCategoryList(newBranchId: Long, newParentId: Long? = null) {
        branchId = newBranchId
        categoryId = newParentId
        pagination.loadFirstPage()
    }

    fun onRefresh() {


    }

    fun loadNextPage() {
        pagination.loadNextPage()
    }

}

data class SubCategoriesScreenState(
    val loading: Boolean = false,

    )
