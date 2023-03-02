package uz.uzkass.smartpos.supply.di

import dev.icerock.moko.network.generated.apis.MobileAccountResourceApi
import dev.icerock.moko.network.generated.apis.MobileBranchResourceApi
import dev.icerock.moko.network.generated.apis.MobileContractResourceApi
import dev.icerock.moko.network.generated.apis.MobileCustomerResourceApi
import dev.icerock.moko.network.generated.apis.MobileDashboardResourceApi
import dev.icerock.moko.network.generated.apis.MobileOrderResourceApi
import dev.icerock.moko.network.generated.apis.MobileWarehouseResourceApi
import org.koin.core.qualifier.named
import org.koin.dsl.module
import public.apis.PublicOrderResourceApi
import uz.uzkass.smartpos.supply.viewmodels.CheckPinCodeViewModel
import uz.uzkass.smartpos.supply.viewmodels.ChooseLanguageViewModel
import uz.uzkass.smartpos.supply.viewmodels.ConfirmOrderViewModel
import uz.uzkass.smartpos.supply.viewmodels.CreateNewPasswordViewModel
import uz.uzkass.smartpos.supply.viewmodels.HomeViewModel
import uz.uzkass.smartpos.supply.viewmodels.LoginViewModel
import uz.uzkass.smartpos.supply.viewmodels.PasswordResetConfirmViewModel
import uz.uzkass.smartpos.supply.viewmodels.PasswordResetViewModel
import uz.uzkass.smartpos.supply.viewmodels.SelectCustomerViewModel2
import uz.uzkass.smartpos.supply.viewmodels.SetPinCodeViewModel
import uz.uzkass.smartpos.supply.viewmodels.SplashViewModel
import uz.uzkass.smartpos.supply.viewmodels.clients.CustomersViewModel
import uz.uzkass.smartpos.supply.viewmodels.home.CreateOrderViewModel
import uz.uzkass.smartpos.supply.viewmodels.home.SelectCustomerViewModel
import uz.uzkass.smartpos.supply.viewmodels.orders.OrdersViewModel

val viewModelModule = module {

    factory { SplashViewModel(preferenceManager = get()) }

    factory { ChooseLanguageViewModel(preferenceManager = get()) }

    factory {
        LoginViewModel(
            api = MobileAccountResourceApi(
                httpClient = get(named(HTTP_CLIENT_NAME)),
                json = get()
            ),
            preferenceManager = get(),
        )
    }

    factory { SetPinCodeViewModel(preferenceManager = get()) }

    factory { CheckPinCodeViewModel(preferenceManager = get()) }

    factory {
        HomeViewModel(
            api = MobileDashboardResourceApi(
                httpClient = get(named(AUTH_HTTP_CLIENT_NAME)),
                json = get()
            )
        )
    }

    factory {
        PasswordResetViewModel(
            api = MobileAccountResourceApi(
                httpClient = get(named(HTTP_CLIENT_NAME)),
                json = get()
            )
        )
    }

    factory {
        PasswordResetConfirmViewModel(
            api = MobileAccountResourceApi(
                httpClient = get(named(HTTP_CLIENT_NAME)),
                json = get()
            )
        )
    }

    factory {
        CreateNewPasswordViewModel(
            api = MobileAccountResourceApi(
                httpClient = get(named(HTTP_CLIENT_NAME)),
                json = get()
            ),
            preferenceManager = get()
        )
    }

    factory {
        CustomersViewModel(
            api = MobileCustomerResourceApi(
                httpClient = get(named(AUTH_HTTP_CLIENT_NAME)),
                json = get()
            )
        )
    }

    factory {
        SelectCustomerViewModel(
            MobileCustomerResourceApi(
                httpClient = get(named(AUTH_HTTP_CLIENT_NAME)),
                json = get()
            )
        )
    }

    factory {
        SelectCustomerViewModel2(
            MobileCustomerResourceApi(
                httpClient = get(named(AUTH_HTTP_CLIENT_NAME)),
                json = get()
            )
        )
    }

    factory {
        CreateOrderViewModel(
            customerApi = MobileCustomerResourceApi(
                httpClient = get(named(AUTH_HTTP_CLIENT_NAME)),
                json = get()
            ),
            branchResourceApi = MobileBranchResourceApi(
                httpClient = get(named(AUTH_HTTP_CLIENT_NAME)),
                json = get()
            ),
            contractResourceApi = MobileContractResourceApi(
                httpClient = get(named(AUTH_HTTP_CLIENT_NAME)),
                json = get()
            ),
            publicOrderResourceApiImpl = PublicOrderResourceApi(
                httpClient = get(named(AUTH_HTTP_CLIENT_NAME)),
                json = get()
            ),
            warehouseResourceApi = MobileWarehouseResourceApi(
                httpClient = get(named(AUTH_HTTP_CLIENT_NAME)),
                json = get()
            ),
            localProductRepository = get()
        )
    }


    factory {
        ConfirmOrderViewModel(
            ordersApi = MobileOrderResourceApi(
                httpClient = get(named(AUTH_HTTP_CLIENT_NAME)),
                json = get()
            ),
            localProductRepository = get(),
            publicOrderResourceApi = PublicOrderResourceApi(
                httpClient = get(named(AUTH_HTTP_CLIENT_NAME)),
                json = get()
            )
        )
    }

    factory {
        OrdersViewModel(
            ordersApi = MobileOrderResourceApi(
                httpClient = get(named(AUTH_HTTP_CLIENT_NAME)),
                json = get()
            )
        )
    }
}