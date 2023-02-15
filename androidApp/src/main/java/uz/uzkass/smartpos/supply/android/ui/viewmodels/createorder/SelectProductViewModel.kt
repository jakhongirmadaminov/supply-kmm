package uz.uzkass.smartpos.supply.android.ui.viewmodels.createorder

import androidx.lifecycle.ViewModel
import androidx.paging.Pager
import androidx.paging.PagingConfig
import dev.icerock.moko.network.generated.apis.MobileProductResourceApi
import uz.uzkass.smartpos.supply.settings.LocalProductRepository

class SelectProductViewModel constructor(
    private val productApi: MobileProductResourceApi,
    private val localProductRepository: LocalProductRepository<String>
) :
    ViewModel() {

    private var query: String = ""
    private lateinit var pagingSource: ProductPageSource
    var productPage = Pager(config = PagingConfig(pageSize = 20),
        pagingSourceFactory = {
            ProductPageSource(
                productApi = productApi,
                searchQuery = query
            ).also {
                pagingSource = it
            }
        })
        .flow


    fun getProductByQuery(newQuery: String) {
        query = newQuery
        pagingSource.invalidate()
    }


}

data class ProductItemModel(
    val id: Long?,
    val label: String,
    val price: String,
)