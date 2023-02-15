package uz.uzkass.smartpos.supply.android.ui.viewmodels.createorder

import android.util.Log
import androidx.paging.PagingSource
import androidx.paging.PagingState
import coil.network.HttpException
import dev.icerock.moko.network.generated.apis.MobileCustomerResourceApi
import dev.icerock.moko.network.generated.models.CustomerListMobileDTO
import java.io.IOException


class CustomerPageSource(
    val customerApi: MobileCustomerResourceApi,
    val searchQuery: String
) : PagingSource<Int, CustomerListMobileDTO>() {

    override fun getRefreshKey(state: PagingState<Int, CustomerListMobileDTO>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            val anchorPage = state.closestPageToPosition(anchorPosition)
            anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, CustomerListMobileDTO> {

        Log.d("TTT", "load: ${params.key}")
        return try {
            val pageIndex = params.key ?: 0
            val response = customerApi.getListUsingGET89(
                page = pageIndex,
                search = searchQuery
            )

            LoadResult.Page(
                data = response.content ?: emptyList(),
                prevKey = null,
                nextKey = if (!response.content.isNullOrEmpty()) pageIndex + 1 else null
            )
        } catch (exception: IOException) {
            LoadResult.Error(exception)
        } catch (exception: HttpException) {
            LoadResult.Error(exception)
        }
    }
}

