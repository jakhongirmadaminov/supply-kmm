package uz.uzkass.smartpos.supply.android.di

import dev.icerock.moko.network.generated.apis.MobileCustomerResourceApi
import dev.icerock.moko.network.generated.apis.MobileProductResourceApi
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.module.dsl.singleOf
import org.koin.core.qualifier.named
import org.koin.dsl.module
import uz.uzkass.smartpos.supply.android.ui.viewmodels.createorder.SelectCustomerViewModel
import uz.uzkass.smartpos.supply.android.ui.viewmodels.createorder.SelectProductViewModel
import uz.uzkass.smartpos.supply.core.http.authHttpClient
import uz.uzkass.smartpos.supply.core.http.httpClient
import uz.uzkass.smartpos.supply.core.utils.supplyJson
import uz.uzkass.smartpos.supply.di.AUTH_HTTP_CLIENT_NAME
import uz.uzkass.smartpos.supply.di.HTTP_CLIENT_NAME

val androidModule = module {
    viewModel {

        SelectCustomerViewModel(
            customerApi = MobileCustomerResourceApi(
                httpClient = get(named(AUTH_HTTP_CLIENT_NAME)),
                json = get()
            )
        )
    }

    viewModel {
        SelectProductViewModel(
            productApi = MobileProductResourceApi(
                httpClient = get(named(AUTH_HTTP_CLIENT_NAME)),
                json = get()
            ),
            localProductRepository = get()
        )
    }
}