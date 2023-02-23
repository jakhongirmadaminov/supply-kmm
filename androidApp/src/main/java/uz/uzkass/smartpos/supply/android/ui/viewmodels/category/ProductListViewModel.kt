package uz.uzkass.smartpos.supply.android.ui.viewmodels.category

import androidx.lifecycle.ViewModel
import androidx.paging.Pager
import androidx.paging.PagingConfig
import dev.icerock.moko.network.generated.apis.MobileProductResourceApi
import uz.uzkass.smartpos.supply.android.ui.viewmodels.createorder.ProductPageSource

class ProductListViewModel constructor(
    private val productApi: MobileProductResourceApi,
) : ViewModel() {


    private var query: String = ""
    private lateinit var pagingSource: ProductPageSource

    var productPage = Pager(config = PagingConfig(pageSize = 20),
        pagingSourceFactory = {
            ProductPageSource(
                productApi = productApi,
                searchQuery = query,
                categoryId =4
            ).also {
                pagingSource = it
            }
        })
        .flow


    fun getProductListByCategoryId(categoryId: Long) {

        pagingSource

    }


}