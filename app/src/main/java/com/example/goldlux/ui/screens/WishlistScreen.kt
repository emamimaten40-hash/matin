package com.example.goldlux.ui.screens

import com.example.ui.theme.LuxDisplay
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.ShoppingBag
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.goldlux.localization.Localization
import com.example.goldlux.localization.Strings
import com.example.goldlux.model.AppLanguage
import com.example.goldlux.model.Currency
import com.example.goldlux.model.Product
import com.example.goldlux.ui.components.GoldCard
import com.example.goldlux.ui.components.GoldGradientButton
import com.example.goldlux.ui.viewmodel.GoldLuxUiState
import com.example.goldlux.ui.viewmodel.GoldLuxViewModel
import com.example.ui.theme.DarkBackground
import com.example.ui.theme.DarkSurface
import com.example.ui.theme.DarkSurfaceVariant
import com.example.ui.theme.GoldBorder
import com.example.ui.theme.GoldLight
import com.example.ui.theme.TextMuted
import com.example.ui.theme.TextSilver
import com.example.ui.theme.TextWhite

@Composable
fun WishlistScreen(
    viewModel: GoldLuxViewModel,
    uiState: GoldLuxUiState,
    onNavigateToProduct: (Product) -> Unit,
    onExploreClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val language = uiState.language
    val isArabic = language == AppLanguage.ARABIC
    val currency = uiState.currency
    val gram24KUsd = uiState.liveSnapshot?.gram24KUsd ?: 88.80

    val wishlistedProducts = uiState.products.filter { uiState.wishlistIds.contains(it.id) }

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(DarkBackground)
    ) {
        // Screen Header
        Column(modifier = Modifier.padding(horizontal = 20.dp, vertical = 14.dp)) {
            Text(
                text = Strings.wishlistTitle(language),
                color = TextWhite,
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                fontFamily = LuxDisplay
            )
            Spacer(modifier = Modifier.height(2.dp))
            Text(
                text = "${Localization.formatInt(wishlistedProducts.size, isArabic)} ${if (isArabic) "قطع مفضلة" else "saved items"}",
                color = TextMuted,
                fontSize = 12.sp
            )
        }

        if (wishlistedProducts.isEmpty()) {
            // Empty State
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(32.dp),
                contentAlignment = Alignment.Center
            ) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Box(
                        modifier = Modifier
                            .size(90.dp)
                            .clip(CircleShape)
                            .background(DarkSurfaceVariant)
                            .border(1.dp, GoldBorder, CircleShape),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = Icons.Default.Favorite,
                            contentDescription = null,
                            tint = Color(0xFFE74C3C),
                            modifier = Modifier.size(44.dp)
                        )
                    }

                    Spacer(modifier = Modifier.height(20.dp))

                    Text(
                        text = Strings.emptyWishlistTitle(language),
                        textAlign = TextAlign.Center,
                        color = TextWhite,
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold,
                        fontFamily = LuxDisplay
                    )

                    Spacer(modifier = Modifier.height(6.dp))

                    Text(
                        text = Strings.emptyWishlistSubtitle(language),
                        textAlign = TextAlign.Center,
                        color = TextMuted,
                        fontSize = 13.sp,
                        lineHeight = 18.sp,
                        modifier = Modifier.padding(horizontal = 20.dp)
                    )

                    Spacer(modifier = Modifier.height(24.dp))

                    GoldGradientButton(
                        text = Strings.startShopping(language),
                        onClick = onExploreClick,
                        modifier = Modifier.width(220.dp),
                        height = 48.dp
                    )
                }
            }
        } else {
            LazyVerticalGrid(
                columns = GridCells.Fixed(2),
                contentPadding = PaddingValues(horizontal = 20.dp, vertical = 10.dp),
                horizontalArrangement = Arrangement.spacedBy(14.dp),
                verticalArrangement = Arrangement.spacedBy(14.dp),
                modifier = Modifier.fillMaxSize()
            ) {
                items(wishlistedProducts) { product ->
                    WishlistProductCard(
                        product = product,
                        gram24KUsd = gram24KUsd,
                        currency = currency,
                        language = language,
                        onMoveToBag = { viewModel.moveWishlistToCart(product.id) },
                        onRemove = { viewModel.toggleWishlist(product.id) },
                        onClick = { onNavigateToProduct(product) }
                    )
                }
            }
        }
    }
}

@Composable
fun WishlistProductCard(
    product: Product,
    gram24KUsd: Double,
    currency: Currency,
    language: AppLanguage,
    onMoveToBag: () -> Unit,
    onRemove: () -> Unit,
    onClick: () -> Unit
) {
    val isArabic = language == AppLanguage.ARABIC
    val price = product.calculateFinalPrice(gram24KUsd, currency)

    GoldCard(
        modifier = Modifier
            .fillMaxWidth()
            .height(275.dp),
        backgroundColor = DarkSurface,
        borderColor = GoldBorder,
        borderWidth = 0.8.dp,
        onClick = onClick
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Column {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(140.dp)
                        .background(Color(0xFF1B1B24))
                ) {
                    AsyncImage(
                        model = ImageRequest.Builder(LocalContext.current)
                            .data(product.imageUrls.firstOrNull())
                            .crossfade(true)
                            .build(),
                        contentDescription = if (isArabic) product.nameAr else product.nameEn,
                        contentScale = ContentScale.Crop,
                        modifier = Modifier.fillMaxSize()
                    )

                    // Remove from wishlist button
                    Box(
                        modifier = Modifier
                            .align(Alignment.TopEnd)
                            .padding(8.dp)
                            .size(30.dp)
                            .clip(CircleShape)
                            .background(Color(0x99000000))
                            .clickable(onClick = onRemove),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = Icons.Default.Close,
                            contentDescription = "Remove",
                            tint = Color.White,
                            modifier = Modifier.size(16.dp)
                        )
                    }
                }

                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 12.dp, vertical = 6.dp)
                ) {
                    Text(
                        text = if (isArabic) product.nameAr else product.nameEn,
                        color = TextWhite,
                        fontSize = 13.sp,
                        fontWeight = FontWeight.SemiBold,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                }
            }

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 12.dp, vertical = 10.dp)
            ) {
                Text(
                    text = Localization.formatPrice(price, currency, isArabic),
                    color = GoldLight,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Bold,
                    fontFamily = LuxDisplay
                )

                Spacer(modifier = Modifier.height(8.dp))

                // Move to Bag Action
                GoldGradientButton(
                    text = Strings.moveToBag(language),
                    onClick = onMoveToBag,
                    height = 36.dp,
                    icon = {
                        Icon(
                            imageVector = Icons.Default.ShoppingBag,
                            contentDescription = null,
                            tint = Color(0xFF0E0E13),
                            modifier = Modifier.size(14.dp)
                        )
                    }
                )
            }
        }
    }
}
