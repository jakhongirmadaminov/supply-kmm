package uz.uzkass.smartpos.supply.android.ui.auth.splash

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.annotation.RootNavGraph
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import kotlinx.coroutines.flow.collectLatest
import org.koin.androidx.compose.koinViewModel
import uz.uzkass.smartpos.supply.android.R
import uz.uzkass.smartpos.supply.android.ui.NavGraphs
import uz.uzkass.smartpos.supply.android.ui.destinations.CheckPinCodeScreenDestination
import uz.uzkass.smartpos.supply.android.ui.destinations.ChooseLanguageScreenDestination
import uz.uzkass.smartpos.supply.viewmodels.SplashNavigator
import uz.uzkass.smartpos.supply.viewmodels.SplashViewModel


@RootNavGraph(start = true)
@Destination
@Composable
fun SplashScreen(
    navigator: DestinationsNavigator,
    viewModel: SplashViewModel = koinViewModel()
) {
    LaunchedEffect(key1 = Unit, block = {
        viewModel.navigate.collectLatest { direction ->
            when (direction) {
                SplashNavigator.ToCheckPinCode -> {
                    navigator.navigate(CheckPinCodeScreenDestination) {
                        popUpTo(NavGraphs.root.route)
                    }
                }
                SplashNavigator.ToChooseLanguage -> {
                    navigator.navigate(ChooseLanguageScreenDestination) {
                        popUpTo(NavGraphs.root.route)
                    }
                }
            }
        }
    })
    SplashScreenView(

    )
}

@Composable
private fun SplashScreenView(
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(
                color = Color.White
            ),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            painter = painterResource(id = R.drawable.logo_smartpos),
            contentDescription = null
        )
    }
}

@Composable
@Preview
fun SplashScreenPreview() {

    SplashScreenView(
    )

}