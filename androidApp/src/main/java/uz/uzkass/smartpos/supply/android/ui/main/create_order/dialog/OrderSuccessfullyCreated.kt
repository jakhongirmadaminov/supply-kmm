package uz.uzkass.smartpos.supply.android.ui.main.create_order.dialog

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import uz.uzkass.smartpos.supply.android.R
import uz.uzkass.smartpos.supply.android.coreui.DividerMax
import uz.uzkass.smartpos.supply.android.coreui.DividerMin
import uz.uzkass.smartpos.supply.android.coreui.Spacer12dp
import uz.uzkass.smartpos.supply.android.coreui.Spacer3dp
import uz.uzkass.smartpos.supply.android.coreui.SupplyFilledTextButton
import uz.uzkass.smartpos.supply.android.ui.main.create_order.TwoSiteText
import uz.uzkass.smartpos.supply.android.ui.theme.SupplyTheme

@Composable
fun OrderSuccessfullyCreated(

    modifier: Modifier = Modifier,
    goToHome: () -> Unit
) {

    Column(
        modifier = Modifier
            .padding(16.dp)
            .fillMaxWidth()
            .background(SupplyTheme.colors.background, shape = SupplyTheme.shapes.medium12Dp),
        verticalArrangement = Arrangement.spacedBy(12.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(
                    color = SupplyTheme.colors.primary,
                    shape = RoundedCornerShape(topStart = 12.dp, topEnd = 12.dp)
                )
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Image(
                painter = painterResource(id = R.drawable.ic_success_order_create),
                contentDescription = null
            )
            Spacer12dp()
            Text(
                text = "Заказ создан!",
                color = Color.White,
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold
            )

        }

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp),
        ) {
            Text(
                modifier = Modifier.fillMaxWidth(),
                text = "Наименование компании может быть в 2 строки ",
                fontSize = 16.sp,
                color = Color.Black,
                textAlign = TextAlign.Center
            )

            DividerMin()

            TwoSiteText(
                modifier = Modifier.fillMaxWidth(),
                label = "Общая сумма:",
                value = "21212"
            )

            TwoSiteText(
                modifier = Modifier.fillMaxWidth(),
                label = "НДС 10%",
                value = "6556"
            )

            SupplyFilledTextButton(
                text = "Go Home",
                onClick = goToHome,
                buttonBackgroundColor = SupplyTheme.colors.primary

            )
        }

    }
}