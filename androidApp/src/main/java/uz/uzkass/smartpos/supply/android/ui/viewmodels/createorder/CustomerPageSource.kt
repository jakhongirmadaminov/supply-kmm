package uz.uzkass.smartpos.supply.android.ui.viewmodels.createorder

import android.util.Log
import androidx.paging.PagingSource
import androidx.paging.PagingState
import dev.icerock.moko.network.generated.apis.MobileCustomerResourceApi
import dev.icerock.moko.network.generated.models.CustomerDetailDTO
import dev.icerock.moko.network.generated.models.CustomerListMobileDTO
import dev.icerock.moko.network.generated.models.PageCustomerListMobileDTO
import uz.uzkass.smartpos.supply.core.utils.resultOf


class CustomerPageSource(
    val customerApi: MobileCustomerResourceApi,
    val searchQuery: String
) : PagingSource<Int, CustomerListMobileDTO>() {

    override fun getRefreshKey(state: PagingState<Int, CustomerListMobileDTO>): Int? {
        return state.anchorPosition?.let {
            state.closestPageToPosition(it)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(it)?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, CustomerListMobileDTO> {
        val pageIndex = params.key ?: 0

        return resultOf {
            Log.d("TTT", "resultOf: ")
            customerApi.getListUsingGET89(
                page = pageIndex,
                search = searchQuery
            )

        }.fold({ responce ->
            LoadResult.Page(
                data = responce.content ?: emptyList(),
                prevKey = if (pageIndex == 0) null else pageIndex,
                nextKey = pageIndex.plus(1)
            )
        }, onFailure = {
            LoadResult.Error(Throwable(""))
        })
    }
}

