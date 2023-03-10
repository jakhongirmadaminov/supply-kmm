package uz.uzkass.smartpos.supply.android

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import java.util.Calendar
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import org.koin.android.ext.android.get
import uz.uzkass.smartpos.supply.android.ui.SupplyAppCompose
import uz.uzkass.smartpos.supply.android.ui.theme.SupplyTheme
import uz.uzkass.smartpos.supply.settings.PreferenceManager
import uz.uzkass.smartpos.supply.utils.ErrorTranslator

class MainActivity : ComponentActivity() {
    val settings: PreferenceManager = get()
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

    override fun onPause() {
        settings.setLastTime(Calendar.getInstance().timeInMillis)
        super.onPause()
    }

    override fun onResume() {

        super.onResume()
    }
    override fun onDestroy() {
        settings.setLastTime(0)
        super.onDestroy()
    }


}