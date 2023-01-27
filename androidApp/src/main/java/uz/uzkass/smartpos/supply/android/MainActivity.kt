package uz.uzkass.smartpos.supply.android

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
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