package uz.uzkass.smartpos.supply.android.theme

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.LocalOverscrollConfiguration
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.CornerBasedShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Typography
import androidx.compose.runtime.*
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp


object SupplyTheme {

    val colors: SupplyColors
        @ReadOnlyComposable
        @Composable
        get() = LocalColors.current

    val typography: Typography
        @Composable
        @ReadOnlyComposable
        get() = MaterialTheme.typography

    val shapes: SupplyShapes
        @Composable
        @ReadOnlyComposable
        get() = LocalShapes.current

    val spacing: Spacing
        @Composable
        @ReadOnlyComposable
        get() = LocalSpacing.current

}


@OptIn(ExperimentalFoundationApi::class)
@Composable
fun SupplyTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    colors: SupplyColors = getColors(darkTheme),
    content: @Composable () -> Unit
) {
    CompositionLocalProvider(
        LocalColors provides colors,
        LocalShapes provides SupplyShapes(),
        LocalSpacing provides Spacing(),
        LocalOverscrollConfiguration provides null
    ) {
        MaterialTheme(
            colors = MaterialTheme.colors.copy(
                primary = colors.primary,
                primaryVariant = colors.primaryVariant,
                surface = colors.surface,
                onPrimary = colors.onPrimary,
                onSurface = colors.onSurface,
                onBackground = colors.onBackground,
                background = colors.background,
            ),
            typography = getTypography(colors),
//            typography = getTypography(colors),
            content = content
        )
    }
}

fun getColors(darkTheme: Boolean): SupplyColors {
    return SupplyColors(
        primary = Color(0xFF6B3CEF),
        primaryVariant = Color(0xFF9B8AFB),
        surface = Color.White,
        onPrimary = Color.White,
        onSurface = Color(0xFFF4F3FF),
        onBackground = Color(0xFF6B3CEF),
        background = Color(0xFF6B3CEF),
        lineColor = Color(0xFFD0D5DD),
        lineActiveColor = Color(0xFF528BFF),
        h1 = Color(0xFF1D1B33),
        h2 = Color(0xFF1A202E),
        h3 = Color.Black,
        h4 = Color(0xFF1D1B33),


        largeTitle = Color(0xFF1D1B33),
        mediumTitle = Color(0xFF1D1B33),
        subtitle1 = Color(0xFF1D2939),
        subtitle2 = Color(0xFF1D2939),

        buttonText = Color.White,
        imageTitle = Color.Black,


        textFieldBorder = Color(0xFFD0D5DD),

        textButtonText = Color(0xFF1D1B33)
    )
}

@Immutable
data class SupplyColors(
    val primary: Color,
    val primaryVariant: Color,
    val surface: Color,
    val onPrimary: Color,
    val onSurface: Color,
    val onBackground: Color,
    val background: Color,
    val lineColor: Color,
    val lineActiveColor: Color,
    val h1: Color,
    val h2: Color,
    val h3: Color,
    val h4: Color,

    val largeTitle: Color,
    val mediumTitle: Color,
    val subtitle1: Color,
    val subtitle2: Color,
    val buttonText: Color,
    val imageTitle: Color,


    val textFieldBorder: Color,

    val textButtonText: Color,

    ) {

}

val LocalColors = staticCompositionLocalOf<SupplyColors> {
    error("No LocalColors specified")
}


@Immutable
data class SupplyShapes(
    val default0Dp: CornerBasedShape = RoundedCornerShape(0.dp),
    val extraSmall4dp: CornerBasedShape = RoundedCornerShape(4.dp),
    val small8Dp: CornerBasedShape = RoundedCornerShape(8.dp),
    val medium12Dp: CornerBasedShape = RoundedCornerShape(12.dp),
    val large16Dp: CornerBasedShape = RoundedCornerShape(16.dp),
    val extraLarge24Dp: CornerBasedShape = RoundedCornerShape(24.dp),
    val circleShape: CornerBasedShape = CircleShape,
    val bottomSheetShape: CornerBasedShape = RoundedCornerShape(
        topStart = 18.dp,
        topEnd = 18.dp,
        bottomStart = 0.dp,
        bottomEnd = 0.dp
    )
)

val LocalShapes = staticCompositionLocalOf<SupplyShapes> {
    error("error")
}
