package com.example.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val GoldLuxColorScheme = darkColorScheme(
    primary = GoldPrimary,
    onPrimary = DarkBackground,
    primaryContainer = GoldDark,
    onPrimaryContainer = GoldLight,
    secondary = GoldLight,
    onSecondary = DarkBackground,
    secondaryContainer = DarkSurfaceVariant,
    onSecondaryContainer = GoldLight,
    tertiary = PriceUpGreen,
    background = DarkBackground,
    onBackground = TextWhite,
    surface = DarkSurface,
    onSurface = TextWhite,
    surfaceVariant = DarkSurfaceVariant,
    onSurfaceVariant = TextSilver,
    outline = GoldBorder,
    outlineVariant = Color(0x1AD4AF37),
    error = PriceDownRed,
    onError = Color.White
)

@Composable
fun GoldLuxTheme(
    content: @Composable () -> Unit
) {
    MaterialTheme(
        colorScheme = GoldLuxColorScheme,
        typography = Typography,
        content = content
    )
}

