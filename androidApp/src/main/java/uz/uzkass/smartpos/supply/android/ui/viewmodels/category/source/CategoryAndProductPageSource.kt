package uz.uzkass.smartpos.supply.android.ui.viewmodels.category.source

import android.util.Log
import androidx.paging.PagingSource
import androidx.paging.PagingState
import coil.network.HttpException
import dev.icerock.moko.network.generated.apis.MobileCategoryResourceApi
import dev.icerock.moko.network.generated.apis.MobileCustomerResourceApi
import dev.icerock.moko.network.generated.apis.MobileProductResourceApi
import dev.icerock.moko.network.generated.models.CustomerListMobileDTO
import dev.icerock.moko.network.generated.models.ProductListMobileDTO
import java.io.IOException
import uz.uzkass.smartpos.supply.android.ui.viewmodels.category.model.SubCategoryModel


//class CategoryAndProductPageSource(
//    private val productApi: MobileCategoryResourceApi,
//    val searchQuery: String,
//    val categoryId: Long? = null
//) : PagingSource<Int, SubCategoryModel>() {
//
//    override fun getRefreshKey(state: PagingState<Int, SubCategoryModel>): Int? {
//        return state.anchorPosition?.let { anchorPosition ->
//            val anchorPage = state.closestPageToPosition(anchorPosition)
//            anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
//        }
//    }
//
//    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, SubCategoryModel> {
//
//        Log.d("TTT", "load: ${params.key}")
//        return try {
//            val pageIndex = params.key ?: 0
//            val response = productApi.getChildrenUsingGET1(
//                page = pageIndex,
//                search = searchQuery,
//                categoryId = categoryId
//            ).content?.map { item ->
//                SubCategoryModel(
//                    id = item.id,
//                    label = item.name ?: "",
//                    price = (item.price ?: 0).toString()
//                )
//            }
//
//            LoadResult.Page(
//                data = response ?: emptyList(),
//                prevKey = null,
//                nextKey = if (!response.isNullOrEmpty()) pageIndex + 1 else null
//            )
//        } catch (exception: IOException) {
//            LoadResult.Error(exception)
//        } catch (exception: HttpException) {
//            LoadResult.Error(exception)
//        }
//    }
//}

