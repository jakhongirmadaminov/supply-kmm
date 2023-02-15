/*
package uz.uzkass.smartpos.supply.android.ui.customers.views

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import dev.icerock.moko.network.generated.models.CustomerListMobileDTO
import uz.uzkass.smartpos.supply.android.coreui.AppBarButton
import uz.uzkass.smartpos.supply.android.coreui.Spacer12dp
import uz.uzkass.smartpos.supply.android.coreui.Spacer8dp
import uz.uzkass.smartpos.supply.android.ui.theme.SupplyTheme

@Composable
internal fun VisitItem(
    visitItem: CustomerListMobileDTO,
    onClickItem: () -> Unit,
    onclickAdd: () -> Unit
) {
    Card(
        modifier = Modifier
            .padding(vertical = 4.dp)
            .clip(RoundedCornerShape(8.dp))
            .background(color = SupplyTheme.colors.background)
            .fillMaxWidth()
            .clickable { onClickItem() },
        content = {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 16.dp, vertical = 8.dp),
                verticalArrangement = Arrangement.SpaceBetween,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        modifier = Modifier.weight(1f),
                        color = SupplyTheme.colors.h2,
                        text = visitItem.name ?: "",
                        fontSize = 14.sp,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                    Spacer8dp()
                    AppBarButton(
                        modifier = Modifier.background(
                            color = SupplyTheme.colors.onBackground,
                            shape = RoundedCornerShape(24.dp)
                        ),
                        imageVector = Icons.Default.Add,
                        onClick = onclickAdd,
                        tint = SupplyTheme.colors.background
                    )
                }
                Spacer12dp()
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        modifier = Modifier.weight(1f),
                        color = SupplyTheme.colors.label,
                        text = visitItem.brand ?: "",
                        fontSize = 12.sp
                    )
                    Spacer8dp()
                    Text(
                        text = visitItem.phone ?: "",
                        color = SupplyTheme.colors.label,
                        fontSize = 12.sp
                    )
                }
            }
        }
    )
}*/