package uz.uzkass.smartpos.supply.android.coreui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

private const val APP_BAR_HEIGHT = 56

@Composable
fun DefaultAppBar(
    modifier: Modifier = Modifier,
    backgroundColor: Color = Color.White,
    content: @Composable RowScope.() -> Unit
) {
    Row(
        modifier = modifier
            .background(color = backgroundColor)
            .fillMaxWidth()
            .padding(horizontal = 24.dp)
            .height(APP_BAR_HEIGHT.dp),
        verticalAlignment = Alignment.CenterVertically,
        content = {
            content()
        }
    )
}

@Composable
fun AppBarTitle(
    modifier: Modifier = Modifier,
    title: String,
    maxLines: Int = 1,
    textAlign: TextAlign = TextAlign.Start,
    fontSize: TextUnit = 16.sp,
    fontWeight: FontWeight = FontWeight.W500,
    titleColor: Color = Color.Black
) {
    Text(
        text = title,
        fontSize = fontSize,
        color = titleColor,
        maxLines = maxLines,
        overflow = TextOverflow.Ellipsis,
        fontWeight = fontWeight,
        textAlign = textAlign,
        modifier = modifier
    )
}

@Composable
fun AppBarButton(
    modifier: Modifier = Modifier,
    imageVector: ImageVector,
    tint: Color = Color.Black,
    enabled: Boolean = true,
    onClick: () -> Unit
) {
    multipleEventsCutter {
        IconButton(
            modifier = Modifier.size(24.dp),
            onClick = { it.processEvent(onClick) },
            enabled = enabled,
            content = {
                Icon(
                    modifier = modifier,
                    imageVector = imageVector,
                    contentDescription = null,
                    tint = tint,
                )
            }
        )
    }
}
