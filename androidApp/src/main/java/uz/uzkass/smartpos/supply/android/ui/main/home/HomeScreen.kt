package uz.uzkass.smartpos.supply.android.ui.main.home

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Icon
import androidx.compose.material.IconButton

import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.ramcosta.composedestinations.annotation.Destination
import org.koin.androidx.compose.koinViewModel
import uz.uzkass.smartpos.supply.android.R
import uz.uzkass.smartpos.supply.android.coreui.FillAvailableSpace
import uz.uzkass.smartpos.supply.android.coreui.Spacer16dp
import uz.uzkass.smartpos.supply.android.ui.main.navigation.MainNavGraph
import uz.uzkass.smartpos.supply.android.ui.theme.LocalColors
import uz.uzkass.smartpos.supply.android.ui.theme.LocalShapes
import uz.uzkass.smartpos.supply.android.ui.theme.LocalSpacing
import uz.uzkass.smartpos.supply.android.ui.theme.SupplyTheme
import uz.uzkass.smartpos.supply.viewmodels.HomeDataState
import uz.uzkass.smartpos.supply.viewmodels.HomeViewModel
import uz.uzkassa.smartpos.supply.library.MR

@MainNavGraph(true)
@Destination
@Composable
fun HomeScreen(
    viewModel: HomeViewModel = koinViewModel()
) {

    HomeScreenView(
        homeDate = viewModel.homeData.collectAsState().value
    )
}

@Composable
private fun HomeScreenView(
    homeDate: HomeDataState
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .systemBarsPadding()
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = LocalSpacing.current.medium16Dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {

            Row(
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                Image(
                    painter = painterResource(id = R.drawable.logo_smartpos),
                    contentDescription = null
                )
                FillAvailableSpace()
                AsyncImage(
                    model = ImageRequest.Builder(LocalContext.current)
                        .data(homeDate.avatarImageUrl)
                        .crossfade(true)
                        .build(),
                    placeholder = painterResource(R.drawable.logo_smartpos),
                    contentDescription = null,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .size(60.dp)
                        .clip(CircleShape)
                )

            }

            Row(
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                Text(text = stringResource(id = MR.strings.statistic_by_order.resourceId))
                FillAvailableSpace()
                IconButton(onClick = { /*TODO*/ }) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_refresh_rotate),
                        contentDescription = null
                    )
                }
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
                    title = stringResource(id = MR.strings.today_order_count.resourceId),
                    value = homeDate.todayOrderCount
                )
                Spacer(modifier = Modifier.size(LocalSpacing.current.small8Dp))
                TextBox(
                    modifier = Modifier
                        .fillMaxHeight()
                        .weight(1f),
                    title = stringResource(id = MR.strings.today_order_summa.resourceId),
                    value = homeDate.summaTodayOrders
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
                    title = stringResource(id = MR.strings.order_count.resourceId),
                    value = homeDate.orderCount
                )
                Spacer(modifier = Modifier.size(LocalSpacing.current.small8Dp))
                TextBox(
                    modifier = Modifier
                        .fillMaxHeight()
                        .weight(1f),
                    title = stringResource(id = MR.strings.summa_order.resourceId),
                    value = homeDate.summaAllOrders
                )

            }
            TextBox(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(96.dp)
                    .padding(horizontal = LocalSpacing.current.medium16Dp),
                title = stringResource(id = MR.strings.plan_over.resourceId),
                value = "${homeDate.completedOrder}/${homeDate.countOrderPlane}"
            )
        }

    }


}


@Composable
private fun TextBox(
    modifier: Modifier,
    title: String,
    value: String
) {
    val titleTextColor = SupplyTheme.colors.statisticTitle
    val titleTextStyle = remember {
        TextStyle(
            color = titleTextColor,
            fontFamily = FontFamily.Default,
            fontWeight = FontWeight.Normal,
            fontSize = 14.sp,
            textAlign = TextAlign.Center
        )
    }

    val valueTextColor = SupplyTheme.colors.statisticText
    val valueTextStyle = remember {
        TextStyle(
            color = valueTextColor,
            fontFamily = FontFamily.Default,
            fontWeight = FontWeight.Bold,
            fontSize = 20.sp
        )
    }



    Column(
        modifier = modifier
            .background(
                LocalColors.current.statisticTextBackground,
                shape = LocalShapes.current.small8Dp
            )
            .border(
                1.dp,
                LocalColors.current.statisticText,
                shape = LocalShapes.current.small8Dp
            ),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = title,
            style = titleTextStyle
        )
        Spacer16dp()
        Text(
            style = valueTextStyle,
            text = value,
        )
    }
}


@Composable
fun HomeScreenPreview() {

}