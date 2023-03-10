package uz.uzkass.smartpos.supply.android

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import org.koin.android.ext.android.get
import uz.uzkass.smartpos.supply.android.ui.SupplyAppCompose
import uz.uzkass.smartpos.supply.android.ui.theme.SupplyTheme
import uz.uzkass.smartpos.supply.utils.ErrorTranslator

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        lifecycleScope.launch {
            ErrorTranslator.errorMessage.collectLatest {
                Toast.makeText(this@MainActivity, it, Toast.LENGTH_LONG).show()
            }
        }

        setContent {
            SupplyTheme {
                SupplyAppCompose()
            }
        }
    }
}