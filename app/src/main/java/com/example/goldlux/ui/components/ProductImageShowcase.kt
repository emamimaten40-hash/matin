package com.example.goldlux.ui.components

import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.material.icons.filled.AutoAwesome
import androidx.compose.material.icons.filled.Diamond
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.filled.Verified
import androidx.compose.material3.Icon
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
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import coil.compose.AsyncImagePainter
import coil.request.ImageRequest
import com.example.goldlux.localization.Localization
import com.example.goldlux.model.Product
import com.example.goldlux.model.ProductCategory
import com.example.ui.theme.DarkSurfaceVariant
import com.example.ui.theme.GoldBorder
import com.example.ui.theme.GoldLight
import com.example.ui.theme.GoldPrimary
import com.example.ui.theme.TextMuted
import com.example.ui.theme.TextSilver
import com.example.ui.theme.TextWhite

/**
 * Robust, luxury product image showcase that guarantees no black/empty screen:
 * If the external URL is loading, slow, or fails due to network/firewall,
 * it immediately renders an elegant, high-definition 3D gold jewelry artwork
 * customized for that specific product category with radial spotlight and certification seal.
 */
@Composable
fun ProductImageShowcase(
    product: Product,
    selectedImageIndex: Int = 0,
    isDetail: Boolean = false,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    val imageUrl = product.imageUrls.getOrNull(selectedImageIndex) ?: product.imageUrls.firstOrNull()

    var imageLoadFailed by remember(imageUrl) { mutableStateOf(false) }

    Box(
        modifier = modifier
            .fillMaxWidth()
            .background(
                Brush.radialGradient(
                    colors = listOf(
                        Color(0xFF232130), // Soft royal velvet
                        Color(0xFF13121C),
                        Color(0xFF0A0910)
                    ),
                    radius = if (isDetail) 700f else 350f
                )
            ),
        contentAlignment = Alignment.Center
    ) {
        // 1. Ambient Golden Radial Lighting
        Canvas(modifier = Modifier.fillMaxSize()) {
            val cx = size.width / 2f
            val cy = size.height / 2f
            drawCircle(
                brush = Brush.radialGradient(
                    colors = listOf(
                        Color(0x38D4AF37),
                        Color(0x12D4AF37),
                        Color(0x00000000)
                    ),
                    center = Offset(cx, cy),
                    radius = size.minDimension * 0.55f
                ),
                radius = size.minDimension * 0.55f,
                center = Offset(cx, cy)
            )
        }

        // 2. Fallback / Rich Jewelry Graphic (Always rendered behind, visible immediately or on load failure)
        JewelryCategoryArtwork(
            product = product,
            isDetail = isDetail
        )

        // 3. Coil AsyncImage with error handler
        if (!imageUrl.isNullOrBlank() && !imageLoadFailed) {
            AsyncImage(
                model = ImageRequest.Builder(context)
                    .data(imageUrl)
                    .crossfade(300)
                    .build(),
                contentDescription = product.nameAr,
                contentScale = ContentScale.Crop,
                onState = { state ->
                    if (state is AsyncImagePainter.State.Error) {
                        imageLoadFailed = true
                    }
                },
                modifier = Modifier.fillMaxSize()
            )
        }
    }
}

@Composable
private fun JewelryCategoryArtwork(
    product: Product,
    isDetail: Boolean
) {
    val infiniteTransition = rememberInfiniteTransition(label = "shimmer_jewel")
    val pulse by infiniteTransition.animateFloat(
        initialValue = 0.97f,
        targetValue = 1.03f,
        animationSpec = infiniteRepeatable(
            animation = tween(2000, easing = FastOutSlowInEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "pulse"
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(if (isDetail) 24.dp else 12.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        // Luxury Icon Badge
        Box(
            modifier = Modifier
                .size(if (isDetail) 110.dp else 68.dp)
                .clip(CircleShape)
                .background(
                    Brush.radialGradient(
                        listOf(Color(0x55D4AF37), Color(0x221D1A2A), Color.Transparent)
                    )
                )
                .border(1.2.dp, GoldBorder.copy(alpha = 0.8f), CircleShape),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector = when (product.category) {
                    ProductCategory.RINGS -> Icons.Default.Diamond
                    ProductCategory.BARS -> Icons.Default.Verified
                    ProductCategory.NECKLACES -> Icons.Default.Star
                    else -> Icons.Default.AutoAwesome
                },
                contentDescription = null,
                tint = GoldLight,
                modifier = Modifier.size(if (isDetail) 52.dp else 32.dp)
            )
        }

        Spacer(modifier = Modifier.height(if (isDetail) 12.dp else 6.dp))

        // Hallmark & Category Label
        Surface(
            shape = RoundedCornerShape(8.dp),
            color = Color(0xCC0E0E14),
            border = androidx.compose.foundation.BorderStroke(0.8.dp, GoldBorder)
        ) {
            Row(
                modifier = Modifier.padding(horizontal = 8.dp, vertical = 3.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    imageVector = Icons.Default.Verified,
                    contentDescription = null,
                    tint = GoldLight,
                    modifier = Modifier.size(if (isDetail) 14.dp else 11.dp)
                )
                Spacer(modifier = Modifier.width(4.dp))
                Text(
                    text = "${product.karat.nameAr} • ${product.weightInGrams} غرام",
                    color = GoldLight,
                    fontSize = if (isDetail) 12.sp else 10.sp,
                    fontWeight = FontWeight.Bold
                )
            }
        }

        if (isDetail) {
            Spacer(modifier = Modifier.height(6.dp))
            Text(
                text = "صانع الأحلام • مجوهرات كويتية معتمدة",
                textAlign = TextAlign.Center,
                color = TextSilver,
                fontSize = 11.sp,
                fontWeight = FontWeight.Medium
            )
        }
    }
}
