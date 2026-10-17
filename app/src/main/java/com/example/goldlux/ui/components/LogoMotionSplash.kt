package com.example.goldlux.ui.components

import com.example.ui.theme.LuxDisplay
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.slideInVertically
import androidx.compose.foundation.Image
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.ui.res.painterResource
import com.example.R
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Chat
import androidx.compose.material.icons.filled.Diamond
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.draw.scale
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.drawscope.rotate
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ui.theme.DarkBackground
import com.example.ui.theme.GoldBorder
import com.example.ui.theme.GoldLight
import com.example.ui.theme.GoldPrimary
import com.example.ui.theme.TextMuted
import com.example.ui.theme.TextSilver
import com.example.ui.theme.TextWhite
import kotlinx.coroutines.delay
import kotlin.math.cos
import kotlin.math.sin

/**
 * Animated Logo Motion Screen for "صانع الأحلام" (Sanea Al-Ahlam / Jewelry Dream Maker).
 * Features an animated gold jewel emblem with rotating orbital rings,
 * light rays, floating sparkles, royal Arabic calligraphy, and direct WhatsApp contact.
 */
@Composable
fun LogoMotionSplash(
    onFinish: () -> Unit,
    modifier: Modifier = Modifier,
    autoDismissDelayMs: Long = 2800L
) {
    val scale = remember { Animatable(0.3f) }
    val alpha = remember { Animatable(0f) }
    val glowAlpha = remember { Animatable(0f) }
    var textVisible by remember { mutableStateOf(false) }

    // Continuous subtle animations
    val infiniteTransition = rememberInfiniteTransition(label = "logo_motion_inf")
    val orbitAngle by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 360f,
        animationSpec = infiniteRepeatable(
            animation = tween(8000, easing = LinearEasing),
            repeatMode = RepeatMode.Restart
        ),
        label = "orbit_angle"
    )
    val counterAngle by infiniteTransition.animateFloat(
        initialValue = 360f,
        targetValue = 0f,
        animationSpec = infiniteRepeatable(
            animation = tween(12000, easing = LinearEasing),
            repeatMode = RepeatMode.Restart
        ),
        label = "counter_angle"
    )
    val pulseScale by infiniteTransition.animateFloat(
        initialValue = 0.96f,
        targetValue = 1.04f,
        animationSpec = infiniteRepeatable(
            animation = tween(1500, easing = FastOutSlowInEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "pulse_scale"
    )
    val shimmerOffset by infiniteTransition.animateFloat(
        initialValue = -200f,
        targetValue = 600f,
        animationSpec = infiniteRepeatable(
            animation = tween(2200, easing = LinearEasing),
            repeatMode = RepeatMode.Restart
        ),
        label = "shimmer_sweep"
    )

    LaunchedEffect(Unit) {
        // Step 1: Scale & fade in the emblem with royal easing
        glowAlpha.animateTo(1f, animationSpec = tween(600, easing = FastOutSlowInEasing))
        alpha.animateTo(1f, animationSpec = tween(800, easing = FastOutSlowInEasing))
        scale.animateTo(
            targetValue = 1.0f,
            animationSpec = tween(1000, easing = FastOutSlowInEasing)
        )
        textVisible = true

        // Step 2: Allow auto-transition into boutique app
        delay(autoDismissDelayMs)
        onFinish()
    }

    Box(
        modifier = modifier
            .fillMaxSize()
            .background(
                Brush.radialGradient(
                    colors = listOf(
                        Color(0xFF1E1A2B), // Deep royal obsidian
                        Color(0xFF0F0E17),
                        Color(0xFF06060A)
                    ),
                    center = Offset(540f, 960f),
                    radius = 1200f
                )
            )
            .clickable(
                interactionSource = remember { MutableInteractionSource() },
                indication = null
            ) { onFinish() }
    ) {
        // Floating ambient gold sparkles in the background
        Canvas(modifier = Modifier.fillMaxSize()) {
            val cx = size.width / 2f
            val cy = size.height / 2f

            // Radial ambient gold burst
            drawCircle(
                brush = Brush.radialGradient(
                    colors = listOf(
                        Color(0x33D4AF37),
                        Color(0x15D4AF37),
                        Color(0x00000000)
                    ),
                    center = Offset(cx, cy * 0.85f),
                    radius = size.width * 0.75f
                ),
                radius = size.width * 0.75f,
                center = Offset(cx, cy * 0.85f)
            )

            // Sparkle stars
            val sparkleCoords = listOf(
                Pair(cx - 160f, cy - 280f),
                Pair(cx + 170f, cy - 240f),
                Pair(cx - 200f, cy + 180f),
                Pair(cx + 190f, cy + 220f),
                Pair(cx - 90f, cy + 320f),
                Pair(cx + 110f, cy - 340f)
            )
            sparkleCoords.forEachIndexed { i, coord ->
                val pAlpha = ((sin((orbitAngle + i * 60) * Math.PI / 180).toFloat() + 1f) / 2f).coerceIn(0.2f, 0.9f)
                drawCircle(
                    color = GoldLight.copy(alpha = pAlpha * glowAlpha.value),
                    radius = if (i % 2 == 0) 3.5f else 2f,
                    center = Offset(coord.first, coord.second)
                )
            }
        }

        // Center Content: Emblem + Brand Name + Subtitle
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 24.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            // Animated Royal Gold Emblem
            Box(
                modifier = Modifier
                    .size(220.dp)
                    .scale(scale.value * pulseScale)
                    .alpha(alpha.value),
                contentAlignment = Alignment.Center
            ) {
                // Outer Orbiting Ornamental Ring
                Canvas(modifier = Modifier.fillMaxSize()) {
                    val radius = size.minDimension / 2f - 12f
                    val center = Offset(size.width / 2f, size.height / 2f)

                    // Rotating dashed gold ring
                    rotate(orbitAngle) {
                        drawCircle(
                            brush = Brush.sweepGradient(
                                listOf(
                                    Color(0xFFD4AF37),
                                    Color(0xFFFFF3C4),
                                    Color(0xFF9E7600),
                                    Color(0xFFD4AF37)
                                )
                            ),
                            radius = radius,
                            center = center,
                            style = Stroke(
                                width = 2.2f,
                                cap = StrokeCap.Round
                            )
                        )
                        // 8 ornamental nodes on ring
                        for (i in 0 until 8) {
                            val rad = Math.toRadians((i * 45).toDouble())
                            val nx = center.x + (radius * cos(rad)).toFloat()
                            val ny = center.y + (radius * sin(rad)).toFloat()
                            drawCircle(
                                color = if (i % 2 == 0) GoldLight else GoldPrimary,
                                radius = if (i % 2 == 0) 4.5f else 3f,
                                center = Offset(nx, ny)
                            )
                        }
                    }

                    // Counter-rotating inner orbit ring
                    rotate(counterAngle) {
                        drawCircle(
                            color = GoldBorder.copy(alpha = 0.6f),
                            radius = radius - 16f,
                            center = center,
                            style = Stroke(
                                width = 1.2f,
                                cap = StrokeCap.Round
                            )
                        )
                    }
                }

                // Inner Glow Backdrop
                Box(
                    modifier = Modifier
                        .size(150.dp)
                        .clip(CircleShape)
                        .background(
                            Brush.radialGradient(
                                listOf(
                                    Color(0x55D4AF37),
                                    Color(0x221D1A2A),
                                    Color.Transparent
                                )
                            )
                        )
                        .border(1.2.dp, GoldBorder, CircleShape),
                    contentAlignment = Alignment.Center
                ) {
                    // Center Sane' Al-Ahlam Official Logo Graphic
                    Image(
                        painter = painterResource(id = R.drawable.ic_sane_al_ahlam_logo),
                        contentDescription = "صانع الأحلام",
                        modifier = Modifier.size(130.dp)
                    )
                }
            }

            Spacer(modifier = Modifier.height(28.dp))

            // Brand Typography: صانع الأحلام
            AnimatedVisibility(
                visible = textVisible,
                enter = fadeIn(tween(600)) + slideInVertically(
                    initialOffsetY = { 40 },
                    animationSpec = tween(600, easing = FastOutSlowInEasing)
                )
            ) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text(
                        text = "صانع الأحلام",
                        color = TextWhite,
                        fontSize = 38.sp,
                        fontWeight = FontWeight.Bold,
                        fontFamily = LuxDisplay,
                        letterSpacing = 0.sp,
                        textAlign = TextAlign.Center
                    )

                    Spacer(modifier = Modifier.height(6.dp))

                    Text(
                        text = "JEWELRY DREAM MAKER • KUWAIT",
                        color = GoldLight,
                        fontSize = 12.sp,
                        fontWeight = FontWeight.SemiBold,
                        letterSpacing = 2.5.sp,
                        textAlign = TextAlign.Center
                    )

                    Spacer(modifier = Modifier.height(6.dp))

                    Text(
                        text = "مجوهرات وسبائك الذهب الفاخرة • أسعار البورصة المباشرة",
                        color = TextSilver,
                        fontSize = 13.sp,
                        fontWeight = FontWeight.Normal,
                        textAlign = TextAlign.Center
                    )

                    Spacer(modifier = Modifier.height(22.dp))

                    // WhatsApp Concierge Pill
                    Surface(
                        shape = RoundedCornerShape(24.dp),
                        color = Color(0x33128C7E),
                        border = androidx.compose.foundation.BorderStroke(1.dp, Color(0xFF25D366).copy(alpha = 0.6f)),
                        modifier = Modifier.padding(horizontal = 8.dp)
                    ) {
                        Row(
                            modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Icon(
                                imageVector = Icons.Default.Chat,
                                contentDescription = "WhatsApp",
                                tint = Color(0xFF25D366),
                                modifier = Modifier.size(17.dp)
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            Text(
                                text = "الطلب المباشر في واتساب: 99434920 965+",
                                color = Color(0xFFE8F5E9),
                                fontSize = 12.sp,
                                fontWeight = FontWeight.Bold
                            )
                        }
                    }
                }
            }
        }

        // Bottom: Tap to enter button
        Box(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .fillMaxWidth()
                .padding(bottom = 36.dp, start = 32.dp, end = 32.dp),
            contentAlignment = Alignment.Center
        ) {
            OutlinedButton(
                onClick = onFinish,
                colors = ButtonDefaults.outlinedButtonColors(
                    containerColor = Color(0x22D4AF37),
                    contentColor = GoldLight
                ),
                border = androidx.compose.foundation.BorderStroke(1.dp, GoldBorder.copy(alpha = 0.8f)),
                shape = RoundedCornerShape(20.dp),
                modifier = Modifier.height(42.dp)
            ) {
                Text(
                    text = "دخول المتجر • Enter Boutique",
                    fontSize = 13.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = GoldLight
                )
            }
        }
    }
}
