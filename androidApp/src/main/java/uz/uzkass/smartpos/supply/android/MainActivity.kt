package uz.uzkass.smartpos.supply.android

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import dev.icerock.moko.errors.mappers.ExceptionMappersStorage
import dev.icerock.moko.network.errors.registerAllNetworkMappers
import uz.uzkass.smartpos.supply.android.coreui.TimerTextButton
import uz.uzkass.smartpos.supply.android.ui.SupplyAppCompose
import uz.uzkass.smartpos.supply.android.ui.theme.SupplyTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            SupplyTheme() {
                SupplyAppCompose()
            }
        }
    }
}