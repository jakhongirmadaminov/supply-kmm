package uz.uzkass.smartpos.supply.android.di

import dev.icerock.moko.network.generated.apis.MobileBranchResourceApi
import dev.icerock.moko.network.generated.apis.MobileCategoryResourceApi
import dev.icerock.moko.network.generated.apis.MobileCustomerBranchResourceApi
import dev.icerock.moko.network.generated.apis.MobileCustomerResourceApi
import dev.icerock.moko.network.generated.apis.MobileOrderResourceApi
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.module.dsl.singleOf
import org.koin.core.qualifier.named
import org.koin.dsl.module
import uz.uzkass.smartpos.supply.android.ui.viewmodels.category.CategoriesViewModel
import uz.uzkass.smartpos.supply.android.ui.viewmodels.category.SubCategoriesViewModel
import uz.uzkass.smartpos.supply.android.ui.viewmodels.createorder.AddProductViewModel
import uz.uzkass.smartpos.supply.android.ui.viewmodels.createorder.SelectCustomerBranchViewModel
import uz.uzkass.smartpos.supply.android.ui.viewmodels.createorder.SelectCustomerViewModel
import uz.uzkass.smartpos.supply.android.ui.viewmodels.createorder.SelectProductViewModel
import uz.uzkass.smartpos.supply.android.ui.viewmodels.createorder.SelectedProductsViewModel
import uz.uzkass.smartpos.supply.core.http.authHttpClient
import uz.uzkass.smartpos.supply.core.http.httpClient
import uz.uzkass.smartpos.supply.core.utils.supplyJson
import uz.uzkass.smartpos.supply.di.AUTH_HTTP_CLIENT_NAME
import uz.uzkass.smartpos.supply.di.HTTP_CLIENT_NAME
import uz.uzkass.smartpos.supply.settings.LocalProductRepository

val androidModule = module {

    viewModel {

        SelectCustomerViewModel(
            customerApi = MobileCustomerResourceApi(
                httpClient = get(named(AUTH_HTTP_CLIENT_NAME)),
                json = get()

            ),
            customerBranchApi = MobileCustomerBranchResourceApi(
                httpClient = get(named(AUTH_HTTP_CLIENT_NAME)),
                json = get()
            ),
            localProductRepository = get()
        )
    }

    viewModel {
        SelectProductViewModel(
            productApi = MobileOrderResourceApi(
                httpClient = get(named(AUTH_HTTP_CLIENT_NAME)),
                json = get()
            ),
            localProductRepository = get()
        )
    }

    viewModel {
        SelectedProductsViewModel(
            localProductRepository = get()
        )
    }

    viewModel {
        CategoriesViewModel(
            api = MobileCategoryResourceApi(
                httpClient = get(named(AUTH_HTTP_CLIENT_NAME)),
                json = get()
            ),
            branchResourceApi = MobileBranchResourceApi(
                httpClient = get(named(AUTH_HTTP_CLIENT_NAME)),
                json = get()
            )
        )
    }

    viewModel {
        SubCategoriesViewModel(
            api = MobileCategoryResourceApi(
                httpClient = get(named(AUTH_HTTP_CLIENT_NAME)),
                json = get()
            )
        )
    }


    viewModel {
        SelectCustomerBranchViewModel(
            customerApi = MobileCustomerBranchResourceApi(
                httpClient = get(named(AUTH_HTTP_CLIENT_NAME)),
                json = get()
            ),
            localProductRepository = get()
        )
    }

    viewModel {
        AddProductViewModel(
            localProductRepository = get()
        )
    }


}