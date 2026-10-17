package com.example.goldlux.ui.components

import com.example.ui.theme.LuxDisplay
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.example.goldlux.localization.Localization
import com.example.ui.theme.TextWhite

@Composable
fun AnimatedPriceText(
    targetAmount: Double,
    isArabic: Boolean,
    modifier: Modifier = Modifier,
    decimals: Int = 2,
    style: TextStyle = TextStyle(
        fontFamily = LuxDisplay,
        fontWeight = FontWeight.Bold,
        fontSize = 32.sp,
        color = TextWhite
    )
) {
    val animatedValue by animateFloatAsState(
        targetValue = targetAmount.toFloat(),
        animationSpec = tween(durationMillis = 650),
        label = "price_count_anim"
    )

    val formattedStr = Localization.formatNumber(
        number = animatedValue.toDouble(),
        isArabic = isArabic,
        decimals = decimals
    )

    Text(
        text = formattedStr,
        style = style,
        modifier = modifier
    )
}
