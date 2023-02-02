package uz.uzkass.smartpos.supply.android.ui.main.bottom_bar

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes

import com.ramcosta.composedestinations.spec.DirectionDestinationSpec
import uz.uzkass.smartpos.supply.android.R
import uz.uzkass.smartpos.supply.android.ui.destinations.CategoriesScreenDestination
import uz.uzkass.smartpos.supply.android.ui.destinations.ClientsScreenDestination
import uz.uzkass.smartpos.supply.android.ui.destinations.HomeScreenDestination
import uz.uzkass.smartpos.supply.android.ui.destinations.OrdersScreenDestination
import uz.uzkassa.smartpos.supply.library.MR


enum class BottomBarDestination(
    val direction: DirectionDestinationSpec,
    @DrawableRes val icon: Int,
    @StringRes val label: Int
) {
    Home(HomeScreenDestination, R.drawable.ic_home, MR.strings.home.resourceId),
    Client(ClientsScreenDestination, R.drawable.ic_clients, MR.strings.client.resourceId),

    Category(CategoriesScreenDestination, R.drawable.ic_categories, MR.strings.catagory.resourceId),
    Order(OrdersScreenDestination, R.drawable.ic_orders, MR.strings.order.resourceId),
}