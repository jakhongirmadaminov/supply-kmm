package uz.uzkass.smartpos.supply.viewmodels

import com.kuuurt.paging.multiplatform.Pager
import com.kuuurt.paging.multiplatform.PagingConfig
import com.kuuurt.paging.multiplatform.PagingResult
import dev.icerock.moko.mvvm.viewmodel.ViewModel
import dev.icerock.moko.paging.IdComparator
import dev.icerock.moko.paging.IdEntity
import dev.icerock.moko.paging.LambdaPagedListDataSource
import dev.icerock.moko.paging.Pagination
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class CategoriesViewModel constructor() : ViewModel() {

    private val pagingConfig = PagingConfig(pageSize = 20, enablePlaceholders = false)

    private var currentQuery = ""
    private var currentPage = 0

//    val categoriesPager = Pager(clientScope = viewModelScope, config = pagingConfig, initialKey = 1,
//        getItems = { currentKey, size ->
//            val charactersResponse = getChadilracters(currentKey)
//            PagingResult(
//                items = items,
//                currentKey = currentPage,
//                prevKey = { null },
//                nextKey = { currentPage + 1}
//            )
//        }
//    )


    private val _categoriesList = MutableStateFlow<List<Unit>>(emptyList())
    val categoriesList get() = _categoriesList.asStateFlow()


    fun getCategoriesByQuery(query: String) {

    }

}
