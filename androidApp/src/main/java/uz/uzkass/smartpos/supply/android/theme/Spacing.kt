package uz.uzkass.smartpos.supply.android.theme

import androidx.compose.runtime.Composable
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

data class Spacing(
    val default:Dp = 0.dp,
    val extraSmall4dp: Dp = 4.dp,
    val small8Dp: Dp = 8.dp,
    val medium16Dp: Dp = 16.dp,
    val large32Dp: Dp = 32.dp,
    val extraLarge64Dp: Dp = 64.dp,
)

val LocalSpacing = compositionLocalOf { Spacing() }


val  spacing: Spacing
@Composable
@ReadOnlyComposable
get() = LocalSpacing.current