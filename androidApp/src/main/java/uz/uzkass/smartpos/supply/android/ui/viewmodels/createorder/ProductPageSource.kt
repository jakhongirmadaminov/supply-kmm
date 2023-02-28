package uz.uzkass.smartpos.supply.android.ui.viewmodels.createorder

import android.util.Log
import androidx.paging.PagingSource
import androidx.paging.PagingState
import coil.network.HttpException
import dev.icerock.moko.network.generated.apis.MobileCustomerResourceApi
import dev.icerock.moko.network.generated.apis.MobileProductResourceApi
import dev.icerock.moko.network.generated.models.CustomerListMobileDTO
import dev.icerock.moko.network.generated.models.ProductListMobileDTO
import java.io.IOException
import uz.uzkass.smartpos.supply.android.ui.viewmodels.createorder.models.ProductItemModel


class ProductPageSource(
    private val productApi: MobileProductResourceApi,
    val searchQuery: String,
    val categoryId: Long? = null
) : PagingSource<Int, ProductItemModel>() {

    override fun getRefreshKey(state: PagingState<Int, ProductItemModel>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            val anchorPage = state.closestPageToPosition(anchorPosition)
            anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, ProductItemModel> {

        Log.d("TTT", "load: ${params.key}")
        return try {
            val pageIndex = params.key ?: 0
            val response = productApi.getListUsingGET103(
                page = pageIndex,
                search = searchQuery,
                categoryId = categoryId
            ).content?.map { item ->
                ProductItemModel(
                    id = item.id,
                    label = item.name ?: "",
                    price = (item.price ?: 0).toString()
                )
            }

            LoadResult.Page(
                data = response ?: emptyList(),
                prevKey = null,
                nextKey = if (!response.isNullOrEmpty()) pageIndex + 1 else null
            )
        } catch (exception: IOException) {
            LoadResult.Error(exception)
        } catch (exception: HttpException) {
            LoadResult.Error(exception)
        }
    }
}

