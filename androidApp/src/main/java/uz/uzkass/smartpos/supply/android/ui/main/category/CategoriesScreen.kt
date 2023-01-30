package uz.uzkass.smartpos.supply.android.ui.main.category

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.ramcosta.composedestinations.annotation.Destination
import uz.uzkass.smartpos.supply.android.ui.main.navigation.MainNavGraph

@MainNavGraph
@Composable
@Destination
fun CategoriesScreen() {

    CategoriesScreenView()

}

@Composable
private fun CategoriesScreenView() {
    Box(modifier = Modifier.systemBarsPadding()) {


    }

}

@Composable
fun CategoriesScreenPreview() {

}