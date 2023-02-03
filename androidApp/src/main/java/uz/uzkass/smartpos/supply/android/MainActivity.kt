package uz.uzkass.smartpos.supply.android

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import dev.icerock.moko.errors.mappers.ExceptionMappersStorage
import dev.icerock.moko.network.errors.registerAllNetworkMappers
import uz.uzkass.smartpos.supply.android.coreui.TimerTextButton
import uz.uzkass.smartpos.supply.android.coreui.menu.ExposedDropdown
import uz.uzkass.smartpos.supply.android.coreui.menu.ExposedDropdownField
import uz.uzkass.smartpos.supply.android.ui.SupplyAppCompose
import uz.uzkass.smartpos.supply.android.ui.theme.SupplyTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            var expendable by remember {
                mutableStateOf(false)
            }
            SupplyTheme {
//
//                ExposedDropdownField(
//                    modifier = Modifier.fillMaxWidth(),
//                    expanded = expendable,
//                    items = listOf(
//                        ExposedDropdown("", "label 1"),
//                        ExposedDropdown("", "label 2"),
//                        ExposedDropdown("", "label 3"),
//                        ExposedDropdown("", "label 4"),
//                        ExposedDropdown("", "label 5"),
//                        ExposedDropdown("", "label 6"),
//
//
//                        ),
//                    onDismissRequest = {
//                        expendable = expendable.not()
//                    },
//                    onItemSelected = {
//
//                    }
//                )
                SupplyAppCompose()
            }
        }
    }
}