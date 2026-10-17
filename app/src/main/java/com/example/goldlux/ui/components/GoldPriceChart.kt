package com.example.goldlux.ui.components

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.goldlux.localization.Localization
import com.example.goldlux.model.Currency
import com.example.goldlux.model.PricePoint
import com.example.goldlux.model.Timeframe
import com.example.ui.theme.DarkSurfaceElevated
import com.example.ui.theme.DarkSurfaceVariant
import com.example.ui.theme.GoldBorder
import com.example.ui.theme.GoldGradient
import com.example.ui.theme.GoldLight
import com.example.ui.theme.GoldPrimary
import com.example.ui.theme.TextMuted
import com.example.ui.theme.TextSilver
import com.example.ui.theme.TextWhite

@Composable
fun GoldPriceChart(
    points: List<PricePoint>,
    selectedTimeframe: Timeframe,
    onTimeframeSelected: (Timeframe) -> Unit,
    currency: Currency,
    isArabic: Boolean,
    modifier: Modifier = Modifier
) {
    var touchX by remember { mutableStateOf<Float?>(null) }
    var selectedIndex by remember { mutableStateOf<Int?>(null) }

    Column(modifier = modifier.fillMaxWidth()) {
        // Timeframe selector chips (1D, 1W, 1M, 3M, 1Y)
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 4.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Timeframe.values().forEach { timeframe ->
                val isSelected = timeframe == selectedTimeframe
                val chipShape = RoundedCornerShape(12.dp)

                Surface(
                    shape = chipShape,
                    color = if (isSelected) GoldPrimary else DarkSurfaceVariant,
                    border = if (isSelected) null else androidx.compose.foundation.BorderStroke(0.6.dp, GoldBorder),
                    modifier = Modifier
                        .height(36.dp)
                        .weight(1f)
                        .padding(horizontal = 3.dp)
                        .clip(chipShape)
                        .clickable {
                            touchX = null
                            selectedIndex = null
                            onTimeframeSelected(timeframe)
                        }
                ) {
                    Box(contentAlignment = Alignment.Center) {
                        Text(
                            text = if (isArabic) timeframe.labelAr else timeframe.labelEn,
                            color = if (isSelected) Color(0xFF0E0E13) else TextSilver,
                            fontSize = 12.sp,
                            fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Medium
                        )
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(14.dp))

        // Tooltip or Active scrubbing display
        val activePoint = if (selectedIndex != null && selectedIndex!! in points.indices) {
            points[selectedIndex!!]
        } else {
            points.lastOrNull()
        }

        if (activePoint != null) {
            val priceConverted = activePoint.priceUsdPerGram24K * currency.usdRate
            val priceStr = Localization.formatPrice(priceConverted, currency, isArabic)
            val dateLabel = if (isArabic) activePoint.dateLabelAr else activePoint.dateLabelEn

            Surface(
                shape = RoundedCornerShape(12.dp),
                color = DarkSurfaceElevated,
                border = androidx.compose.foundation.BorderStroke(0.6.dp, GoldBorder),
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .padding(bottom = 8.dp)
            ) {
                Row(
                    modifier = Modifier.padding(horizontal = 14.dp, vertical = 6.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Box(
                        modifier = Modifier
                            .width(8.dp)
                            .height(8.dp)
                            .background(GoldPrimary, RoundedCornerShape(4.dp))
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = priceStr,
                        color = GoldLight,
                        fontSize = 13.sp,
                        fontWeight = FontWeight.Bold
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = "•  $dateLabel",
                        color = TextMuted,
                        fontSize = 11.sp
                    )
                }
            }
        }

        // Canvas Area
        if (points.isNotEmpty()) {
            val minPrice = points.minOf { it.priceUsdPerGram24K }
            val maxPrice = points.maxOf { it.priceUsdPerGram24K }
            val priceRange = (maxPrice - minPrice).coerceAtLeast(0.5)

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(190.dp)
                    .pointerInput(points) {
                        detectTapGestures(
                            onPress = { offset ->
                                touchX = offset.x
                                val fraction = (offset.x / size.width).coerceIn(0f, 1f)
                                selectedIndex = (fraction * (points.size - 1)).toInt().coerceIn(0, points.size - 1)
                            }
                        )
                    }
                    .pointerInput(points) {
                        detectDragGestures(
                            onDragStart = { offset ->
                                touchX = offset.x
                                val fraction = (offset.x / size.width).coerceIn(0f, 1f)
                                selectedIndex = (fraction * (points.size - 1)).toInt().coerceIn(0, points.size - 1)
                            },
                            onDrag = { change, _ ->
                                touchX = change.position.x
                                val fraction = (change.position.x / size.width).coerceIn(0f, 1f)
                                selectedIndex = (fraction * (points.size - 1)).toInt().coerceIn(0, points.size - 1)
                            },
                            onDragEnd = {
                                // keep active selection
                            },
                            onDragCancel = {
                                touchX = null
                                selectedIndex = null
                            }
                        )
                    }
            ) {
                Canvas(modifier = Modifier.fillMaxWidth().height(190.dp)) {
                    val width = size.width
                    val height = size.height
                    val paddingBottom = 20f
                    val paddingTop = 15f
                    val chartHeight = height - paddingTop - paddingBottom

                    // Draw subtle dashed horizontal grid guides
                    val dashEffect = PathEffect.dashPathEffect(floatArrayOf(10f, 10f), 0f)
                    val highY = paddingTop
                    val midY = paddingTop + (chartHeight / 2f)
                    val lowY = height - paddingBottom

                    drawLine(
                        color = Color(0x22D4AF37),
                        start = Offset(0f, highY),
                        end = Offset(width, highY),
                        strokeWidth = 1f,
                        pathEffect = dashEffect
                    )
                    drawLine(
                        color = Color(0x1AD4AF37),
                        start = Offset(0f, midY),
                        end = Offset(width, midY),
                        strokeWidth = 1f,
                        pathEffect = dashEffect
                    )
                    drawLine(
                        color = Color(0x22D4AF37),
                        start = Offset(0f, lowY),
                        end = Offset(width, lowY),
                        strokeWidth = 1f,
                        pathEffect = dashEffect
                    )

                    // Compute Coordinates
                    val stepX = width / (points.size - 1).coerceAtLeast(1)
                    val coords = points.mapIndexed { index, pt ->
                        val x = index * stepX
                        val normalizedPrice = (pt.priceUsdPerGram24K - minPrice) / priceRange
                        val y = (height - paddingBottom) - (normalizedPrice.toFloat() * chartHeight)
                        Offset(x, y)
                    }

                    // Build smooth Cubic Bezier Path
                    val strokePath = Path()
                    val fillPath = Path()

                    if (coords.isNotEmpty()) {
                        strokePath.moveTo(coords.first().x, coords.first().y)
                        fillPath.moveTo(coords.first().x, height - paddingBottom)
                        fillPath.lineTo(coords.first().x, coords.first().y)

                        for (i in 0 until coords.size - 1) {
                            val p0 = coords[i]
                            val p1 = coords[i + 1]
                            val cx1 = p0.x + (p1.x - p0.x) / 2f
                            val cy1 = p0.y
                            val cx2 = p0.x + (p1.x - p0.x) / 2f
                            val cy2 = p1.y

                            strokePath.cubicTo(cx1, cy1, cx2, cy2, p1.x, p1.y)
                            fillPath.cubicTo(cx1, cy1, cx2, cy2, p1.x, p1.y)
                        }

                        fillPath.lineTo(coords.last().x, height - paddingBottom)
                        fillPath.close()

                        // 1. Draw Gold Gradient Area Fill under line
                        drawPath(
                            path = fillPath,
                            brush = Brush.verticalGradient(
                                colors = listOf(
                                    Color(0x55F5D76E),
                                    Color(0x22D4AF37),
                                    Color(0x02D4AF37)
                                ),
                                startY = paddingTop,
                                endY = height
                            )
                        )

                        // 2. Draw Golden Line
                        drawPath(
                            path = strokePath,
                            brush = Brush.horizontalGradient(
                                colors = listOf(GoldLight, GoldPrimary)
                            ),
                            style = Stroke(
                                width = 3.dp.toPx(),
                                cap = StrokeCap.Round
                            )
                        )
                    }

                    // 3. Touch Scrubber Indicator
                    if (selectedIndex != null && selectedIndex!! in coords.indices) {
                        val activeCoord = coords[selectedIndex!!]

                        // Vertical Scrubber Line
                        drawLine(
                            color = GoldLight,
                            start = Offset(activeCoord.x, paddingTop),
                            end = Offset(activeCoord.x, height - paddingBottom),
                            strokeWidth = 1.5.dp.toPx(),
                            pathEffect = PathEffect.dashPathEffect(floatArrayOf(6f, 6f), 0f)
                        )

                        // Outer Glow Ring
                        drawCircle(
                            color = Color(0x44F5D76E),
                            radius = 10.dp.toPx(),
                            center = activeCoord
                        )
                        // Inner Solid Dot
                        drawCircle(
                            color = GoldLight,
                            radius = 4.5.dp.toPx(),
                            center = activeCoord
                        )
                        drawCircle(
                            color = Color(0xFF0E0E13),
                            radius = 2.dp.toPx(),
                            center = activeCoord
                        )
                    }
                }
            }
        }
    }
}
