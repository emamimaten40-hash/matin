package com.example.ui.theme

import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color

// Luxury Dark Palette
val DarkBackground = Color(0xFF0E0E13)
val DarkSurface = Color(0xFF17171F)
val DarkSurfaceVariant = Color(0xFF22222E)
val DarkSurfaceElevated = Color(0xFF2A2A38)

// Gold Lux Accents
val GoldPrimary = Color(0xFFD4AF37)
val GoldLight = Color(0xFFF5D76E)
val GoldDark = Color(0xFFAA8012)
val GoldGlow = Color(0x33D4AF37)
val GoldBorder = Color(0x4DD4AF37) // 30% opacity
val GoldShimmer = Color(0xFFFFF6D8)

// Trend Indicators
val PriceUpGreen = Color(0xFF2ECC71)
val PriceDownRed = Color(0xFFE74C3C)

// Typography Colors
val TextWhite = Color(0xFFFFFFFF)
val TextSilver = Color(0xFFB0B0C4)
val TextMuted = Color(0xFF707086)

// Gold Linear Gradient
val GoldGradient = Brush.linearGradient(
    colors = listOf(Color(0xFFF8E39A), GoldLight, GoldPrimary, Color(0xFFB98C22))
)

val GoldSubtleGradient = Brush.linearGradient(
    colors = listOf(Color(0xFF242220), Color(0xFF19181F))
)

