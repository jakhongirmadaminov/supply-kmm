package uz.uzkass.smartpos.supply.android

import android.os.Bundle
import android.window.SplashScreenView
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.ui.Modifier
import uz.uzkass.smartpos.supply.android.theme.SupplyTheme
import uz.uzkass.smartpos.supply.android.ui.SupplyAppCompose

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            SupplyTheme {
               SupplyAppCompose()
            }
        }
    }
}