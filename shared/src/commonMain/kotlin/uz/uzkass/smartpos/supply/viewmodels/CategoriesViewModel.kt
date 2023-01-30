package uz.uzkass.smartpos.supply.viewmodels

import dev.icerock.moko.mvvm.viewmodel.ViewModel
import dev.icerock.moko.paging.IdComparator
import dev.icerock.moko.paging.IdEntity
import dev.icerock.moko.paging.LambdaPagedListDataSource
import dev.icerock.moko.paging.Pagination
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class CategoriesViewModel : ViewModel() {

    private val _categoriesList = MutableStateFlow<List<Unit>>(emptyList())
    val categoriesList get() = _categoriesList.asStateFlow()

    private val pagination: Pagination<Product> = Pagination(
        parentScope = viewModelScope,
        dataSource = LambdaPagedListDataSource {
            delay(1000)
            it?.plus(generatePack(it.size.toLong())) ?: generatePack()
        },
        comparator = IdComparator(),
        nextPageListener = ::onNextPageResult,
        refreshListener = ::onRefreshResult,
        initValue = generatePack()
    )

    private fun onNextPageResult(result: Result<List<Product>>) {
        
        if (result.isSuccess) {
            println("next page successful loaded")
        } else {
            println("next page loading failed ${result.exceptionOrNull()}")
        }
    }

    private fun onRefreshResult(result: Result<List<Product>>) {
        if (result.isSuccess) {
            println("refresh successful")
        } else {
            println("refresh failed ${result.exceptionOrNull()}")
        }
    }

    fun onRetryPressed() {
        pagination.loadFirstPage()
    }

    fun onLoadNextPage() {
        pagination.loadNextPage()
    }

    fun onRefresh() {
        pagination.refresh()
    }

    @Suppress("MagicNumber")
    private fun generatePack(startId: Long = 0): List<Product> {
        return List(20) { idx ->
            val id = startId + idx
            Product(
                id = id,
                title = "Product $id"
            )
        }
    }

}

data class Product(
    override val id: Long,
    val title: String
) : IdEntity

//interface UnitsFactory {
//    fun createProductUnit(id: Long, title: String): TableUnitItem
//    fun createLoading(): TableUnitItem
//}

