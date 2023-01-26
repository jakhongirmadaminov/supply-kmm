package uz.uzkass.smartpos.supply.android.ui.main.bottom_bar

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes

import com.ramcosta.composedestinations.spec.DirectionDestinationSpec


enum class BottomBarDestination(
    val direction: DirectionDestinationSpec,
    @DrawableRes val icon: Int,
    @StringRes val label: Int
) {
//    Home(HomeScreenDestination, R.drawable.ic_home, R.string.home),
//    Client(ClientsScreenDestination, R.drawable.ic_clients, R.string.client),
//
//    Category(CategoriesScreenDestination, R.drawable.ic_categories, R.string.catagory),
//    Order(CategoriesScreenDestination, R.drawable.ic_orders, R.string.order),
//    Order(GreetingScreenDestination, Icons.Default.ShoppingCartCheckout, R.string.order),
}