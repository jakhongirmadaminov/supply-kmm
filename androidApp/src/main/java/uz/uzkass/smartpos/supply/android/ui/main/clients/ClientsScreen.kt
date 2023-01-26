package uz.uzkass.smartpos.supply.android.ui.main.clients

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.ramcosta.composedestinations.annotation.Destination
import uz.uzkass.smartpos.supply.android.R
import uz.uzkass.smartpos.supply.android.ui.main.navigation.MainNavGraph
import uz.uzkass.smartpos.supply.android.ui.theme.LocalColors
import uz.uzkass.smartpos.supply.android.ui.theme.LocalShapes
import uz.uzkass.smartpos.supply.android.ui.theme.LocalSpacing

@MainNavGraph
@Destination
@Composable
fun ClientsScreen() {

    ClientsScreenView()
}

@Composable
private fun ClientsScreenView() {

    Column(
        modifier = Modifier
            .fillMaxSize()
            .systemBarsPadding(),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = LocalSpacing.current.medium16Dp)
        ) {
            Text(text = "Статистика по заказам")
            Image(
                painter = painterResource(id = R.drawable.logo_smartpos),
                contentDescription = null
            )

        }

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(96.dp)
                .padding(horizontal = LocalSpacing.current.medium16Dp)
        ) {
            TextBox(
                modifier = Modifier
                    .fillMaxHeight()
                    .weight(1f),
                title = "План посещений",
                value = "10/20"
            )
            Spacer(modifier = Modifier.size(LocalSpacing.current.small8Dp))
            TextBox(
                modifier = Modifier
                    .fillMaxHeight()
                    .weight(1f),
                title = "План посещений",
                value = "10/20"
            )

        }

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(96.dp)
                .padding(horizontal = LocalSpacing.current.medium16Dp)
        ) {
            TextBox(
                modifier = Modifier
                    .fillMaxHeight()
                    .weight(1f),
                title = "План посещений",
                value = "10/20"
            )
            Spacer(modifier = Modifier.size(LocalSpacing.current.small8Dp))
            TextBox(
                modifier = Modifier
                    .fillMaxHeight()
                    .weight(1f),
                title = "План посещений",
                value = "10/20"
            )

        }
        TextBox(
            modifier = Modifier
                .fillMaxWidth()
                .height(96.dp)
                .padding(horizontal = LocalSpacing.current.medium16Dp),
            title = "План посещений",
            value = "10/20"
        )

    }

}


@Composable
private fun TextBox(
    modifier: Modifier,
    title: String,
    value: String
) {
    Column(
        modifier = modifier
            .background(
                LocalColors.current.lineColor,
                shape = LocalShapes.current.small8Dp
            )
            .border(
                1.dp,
                LocalColors.current.lineActiveColor,
                shape = LocalShapes.current.small8Dp
            ),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(text = title)
        Text(text = value)
    }
}


@Composable
fun ClientsScreenPreview() {

}