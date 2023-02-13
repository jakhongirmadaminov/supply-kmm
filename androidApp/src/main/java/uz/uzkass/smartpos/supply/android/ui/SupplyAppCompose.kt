package uz.uzkass.smartpos.supply.android.ui

import android.annotation.SuppressLint
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.ramcosta.composedestinations.DestinationsNavHost
import com.ramcosta.composedestinations.navigation.navigate
import com.ramcosta.composedestinations.utils.startDestination
import uz.uzkass.smartpos.supply.android.coreui.SpacerWeight
import uz.uzkass.smartpos.supply.android.ui.destinations.Destination
import uz.uzkass.smartpos.supply.android.ui.destinations.SelectCustomerScreen2Destination
import uz.uzkass.smartpos.supply.android.ui.destinations.SelectCustomerScreenDestination
import uz.uzkass.smartpos.supply.android.ui.main.bottom_bar.BottomBarDestination
import uz.uzkass.smartpos.supply.android.ui.theme.SupplyTheme


@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun SupplyAppCompose() {
    val navController = rememberNavController()

    val currentDestination: Destination = navController.appCurrentDestinationAsState().value
        ?: NavGraphs.root.startAppDestination

    val temp = remember(key1 = Unit) {
        BottomBarDestination.values().map { it.direction.route }
    }

    Scaffold(
        bottomBar = {
            if (currentDestination.route in temp) {
                BottomBar(navController, currentDestination)
            }
        },
        floatingActionButton = {
            if (currentDestination.route in temp) {
                FloatingActionButton(
                    onClick = {
                        navController.navigate(SelectCustomerScreenDestination)
                    },
                    backgroundColor = SupplyTheme.colors.selected
                ) {
                    Text(text = "+", fontSize = 20.sp)
                }
            }
        },
        floatingActionButtonPosition = FabPosition.Center,
        isFloatingActionButtonDocked = true
    ) {
        DestinationsNavHost(
            navGraph = NavGraphs.root,
            navController = navController
        )
    }
}

@Composable
fun BottomBar(
    navController: NavController,
    currentDestination: Destination
) {
    BottomAppBar(
        cutoutShape = MaterialTheme.shapes.small.copy(
            CornerSize(percent = 50)
        ),
        backgroundColor = SupplyTheme.colors.background
    ) {
        BottomNavigation(backgroundColor = SupplyTheme.colors.background) {
            BottomBarDestination.values().forEachIndexed { index, destination ->
                BottomNavigationItem(
                    selected = currentDestination == destination.direction,
                    onClick = {
                        navController.navigate(destination.direction) {
                            popUpTo(NavGraphs.main.startDestination.route) {
                                saveState = true
                            }
                            launchSingleTop = true
                            restoreState = true
                        }
                    },
                    icon = {
                        Icon(
                            painter = painterResource(id = destination.icon),
                            contentDescription = stringResource(destination.label)
                        )
                    },
                    unselectedContentColor = SupplyTheme.colors.unSelected,
                    selectedContentColor = SupplyTheme.colors.selected,
                    label = {
                        Text(
                            stringResource(destination.label),
                            fontSize = 10.sp,
                            maxLines = 1
                        )
                    },
                )


                if (index == 1) {
                    SpacerWeight()
                }
            }
        }

    }
}