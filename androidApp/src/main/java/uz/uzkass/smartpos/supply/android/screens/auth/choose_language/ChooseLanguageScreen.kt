package uz.uzkass.smartpos.supply.android.screens.auth.choose_language

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import org.koin.androidx.compose.koinViewModel
import uz.uzkass.smartpos.supply.android.coreui.ColumnButtonImageText
import uz.uzkass.smartpos.supply.android.theme.SupplyTheme
import uz.uzkass.smartpos.supply.android.R
import uz.uzkass.smartpos.supply.viewmodels.DemoViewModel
import uz.uzkassa.smartpos.supply.library.MR

@Composable
fun ChooseLanguageScreen() {
//    val viewModel: ChooseLanguageViewModel = hiltViewModel()
//    ChooseLanguageScreenView(
//        onRussianClick = viewModel::chooseRussian,
//        onUzbekClick = viewModel::chooseUzbek
//    )


    ChooseLanguageScreenView(
        onRussianClick = {},
        onUzbekClick = {}
    )


}

@Composable
private fun ChooseLanguageScreenView(
    onRussianClick: () -> Unit,
    onUzbekClick: () -> Unit,
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
        Spacer(modifier = Modifier.size(50.dp))
        Text(
            text = stringResource(id = MR.strings.choose_language_uz.resourceId),
            fontSize = 26.sp,
            fontWeight = FontWeight.Bold,
            color = SupplyTheme.colors.largeTitle
        )
        Spacer(modifier = Modifier.size(16.dp))
        Text(
            text = stringResource(id = MR.strings.choose_language_ru.resourceId),
            fontSize = 16.sp,
            fontWeight = FontWeight.Medium,
            color = SupplyTheme.colors.mediumTitle
        )
        Spacer(modifier = Modifier.size(8.dp))
        Row {
            ColumnButtonImageText(
                modifier = Modifier
                    .width(140.dp),
                painter = painterResource(id = R.drawable.ic_russian_flag),
                title = stringResource(id = MR.strings.russian.resourceId),
                onClick = onRussianClick
            )
            Spacer(modifier = Modifier.size(16.dp))
            ColumnButtonImageText(
                modifier = Modifier
                    .width(140.dp),
                painter = painterResource(id = R.drawable.ic_uzbek_flag),
                title = stringResource(id = MR.strings.uzbek.resourceId),
                onClick = onUzbekClick
            )

        }
    }
}

@Composable
@Preview
fun ChooseLanguageScreenPreview() {

    ChooseLanguageScreenView(
        onRussianClick = {

        },
        onUzbekClick = {

        }
    )

}