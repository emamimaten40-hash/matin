package com.example.goldlux.ui.screens

import com.example.ui.theme.LuxDisplay
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.ui.res.painterResource
import com.example.R
import androidx.compose.foundation.horizontalScroll
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
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AdminPanelSettings
import androidx.compose.material.icons.filled.ArrowDownward
import androidx.compose.material.icons.filled.ArrowUpward
import androidx.compose.material.icons.filled.Diamond
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.filled.ShoppingBag
import androidx.compose.material.icons.filled.Verified
import androidx.compose.material.icons.outlined.Category
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import com.example.goldlux.ui.components.SectionHeader
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.goldlux.localization.Localization
import com.example.goldlux.localization.Strings
import com.example.goldlux.model.AppLanguage
import com.example.goldlux.model.Currency
import com.example.goldlux.model.GoldRateItem
import com.example.goldlux.model.Product
import com.example.goldlux.model.ProductCategory
import com.example.goldlux.ui.components.AnimatedPriceText
import com.example.goldlux.ui.components.GoldCard
import com.example.goldlux.ui.components.GoldGradientButton
import com.example.goldlux.ui.components.GoldPriceChart
import com.example.goldlux.ui.components.ProductImageShowcase
import com.example.goldlux.ui.viewmodel.GoldLuxUiState
import com.example.goldlux.ui.viewmodel.GoldLuxViewModel
import com.example.ui.theme.DarkBackground
import com.example.ui.theme.DarkSurface
import com.example.ui.theme.DarkSurfaceElevated
import com.example.ui.theme.DarkSurfaceVariant
import com.example.ui.theme.GoldBorder
import com.example.ui.theme.GoldLight
import com.example.ui.theme.GoldPrimary
import com.example.ui.theme.GoldShimmer
import com.example.ui.theme.PriceDownRed
import com.example.ui.theme.PriceUpGreen
import com.example.ui.theme.TextMuted
import com.example.ui.theme.TextSilver
import com.example.ui.theme.TextWhite
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@Composable
fun PriceHomeScreen(
    viewModel: GoldLuxViewModel,
    uiState: GoldLuxUiState,
    onNavigateToProduct: (Product) -> Unit,
    onCategoryClick: (ProductCategory) -> Unit,
    onReplayLogoMotion: (() -> Unit)? = null,
    modifier: Modifier = Modifier
) {
    val language = uiState.language
    val isArabic = language == AppLanguage.ARABIC
    val currency = uiState.currency
    val snapshot = uiState.liveSnapshot
    val gram24KUsd = snapshot?.gram24KUsd ?: 88.80
    val gram24KConverted = gram24KUsd * currency.usdRate

    val scrollState = rememberScrollState()

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(DarkBackground)
            .verticalScroll(scrollState)
            .padding(bottom = 16.dp)
    ) {
        // Top Header: Greeting, Date, Brand Emblem
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp, vertical = 16.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column(
                    modifier = Modifier.weight(1f, fill = false)
                ) {
                    Text(
                        text = Strings.greetingMorning(language),
                        color = GoldLight,
                        fontSize = 13.sp,
                        fontWeight = FontWeight.Medium,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                    Spacer(modifier = Modifier.height(2.dp))
                    Text(
                        text = Strings.appName(language),
                        color = TextWhite,
                        fontSize = 26.sp,
                        fontWeight = FontWeight.Bold,
                        fontFamily = LuxDisplay,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                }

                // Right side: Admin Portal Shortcut (Shown only to Authenticated Admin) + Brand Emblem
                Row(
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    // Admin Quick Shortcut - Only shown to authorized admin, hidden from regular users
                    if (uiState.isAuthenticatedAdmin) {
                        Box(
                            modifier = Modifier
                                .size(42.dp)
                                .clip(RoundedCornerShape(12.dp))
                                .background(DarkSurfaceVariant)
                                .border(0.8.dp, GoldBorder.copy(alpha = 0.8f), RoundedCornerShape(12.dp))
                                .clickable { viewModel.toggleAdminPortal(true) },
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(
                                imageVector = Icons.Default.AdminPanelSettings,
                                contentDescription = Strings.adminPortalTitle(language),
                                tint = GoldLight,
                                modifier = Modifier.size(22.dp)
                            )
                        }
                    }

                    // Brand Emblem Badge - Click to Replay Logo Motion
                    Box(
                        modifier = Modifier
                            .size(42.dp)
                            .clip(RoundedCornerShape(12.dp))
                            .background(
                                Brush.linearGradient(
                                    listOf(Color(0xFF26241D), Color(0xFF1B1922))
                                )
                            )
                            .border(1.dp, GoldBorder, RoundedCornerShape(12.dp))
                            .clickable { onReplayLogoMotion?.invoke() },
                        contentAlignment = Alignment.Center
                    ) {
                        Image(
                            painter = painterResource(id = R.drawable.logo_dream_maker),
                            contentDescription = "صانع الأحلام",
                            modifier = Modifier.size(32.dp)
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(6.dp))
            // Date with Hijri in Arabic
            Text(
                text = Localization.getLocalizedCurrentDate(isArabic),
                color = TextMuted,
                fontSize = 12.sp
            )

            Spacer(modifier = Modifier.height(12.dp))

            // Quick Currency Selector (KWD, USD, AED) - Fully Responsive
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                listOf(Currency.KWD, Currency.USD, Currency.AED).forEach { cur ->
                    val isSelected = cur == currency
                    val label = when (cur) {
                        Currency.KWD -> if (isArabic) "د.ك (KWD)" else "KWD (KD)"
                        Currency.USD -> if (isArabic) "$ (USD)" else "USD ($)"
                        Currency.AED -> if (isArabic) "د.إ (AED)" else "AED (Dh)"
                    }
                    Surface(
                        shape = RoundedCornerShape(12.dp),
                        color = if (isSelected) GoldPrimary else DarkSurface,
                        border = if (isSelected) null else androidx.compose.foundation.BorderStroke(0.8.dp, GoldBorder),
                        modifier = Modifier
                            .weight(1f)
                            .height(38.dp)
                            .clip(RoundedCornerShape(12.dp))
                            .clickable { viewModel.setCurrency(cur) }
                    ) {
                        Box(contentAlignment = Alignment.Center, modifier = Modifier.padding(horizontal = 4.dp)) {
                            Text(
                                text = label,
                                color = if (isSelected) Color(0xFF0E0E13) else TextSilver,
                                fontSize = 12.sp,
                                fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Medium,
                                maxLines = 1,
                                overflow = TextOverflow.Ellipsis
                            )
                        }
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(8.dp))

        // Hero Card: Live 24K Gold Price per Gram
        GoldCard(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp),
            backgroundColor = DarkSurface,
            borderColor = GoldBorder,
            borderWidth = 1.dp
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(
                        Brush.verticalGradient(
                            listOf(Color(0xFF262113), Color(0xFF1B1A1E), DarkSurface)
                        )
                    )
                    .padding(20.dp)
            ) {
                // Top row inside card: Live Status + Refresh Icon
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.weight(1f)
                    ) {
                        // Pulsing Live Dot
                        Box(
                            modifier = Modifier
                                .size(8.dp)
                                .clip(CircleShape)
                                .background(if (snapshot?.isOffline == true) Color(0xFFE67E22) else PriceUpGreen)
                        )
                        Spacer(modifier = Modifier.width(6.dp))
                        Text(
                            text = when {
                                isArabic && snapshot?.isOffline == true -> "دار السبائك • آخر سعر متاح"
                                isArabic -> "أسعار دار السبائك • تحديث مباشر كل ١٥ ثانية"
                                snapshot?.isOffline == true -> "Dar Al Sabaek • last known price"
                                else -> "Dar Al Sabaek • live every 15s"
                            },
                            color = if (snapshot?.isOffline == true) Color(0xFFE67E22) else PriceUpGreen,
                            fontSize = 11.sp,
                            fontWeight = FontWeight.SemiBold,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis
                        )
                    }

                    // Rotating Refresh Button
                    val infiniteTransition = rememberInfiniteTransition(label = "refresh_spin")
                    val spinAngle by infiniteTransition.animateFloat(
                        initialValue = 0f,
                        targetValue = 360f,
                        animationSpec = infiniteRepeatable(
                            animation = tween(1000, easing = LinearEasing)
                        ),
                        label = "spin_angle"
                    )

                    IconButton(
                        onClick = { viewModel.refreshPrices() },
                        modifier = Modifier.size(32.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Default.Refresh,
                            contentDescription = Strings.refresh(language),
                            tint = GoldLight,
                            modifier = Modifier
                                .size(20.dp)
                                .then(if (uiState.isRefreshing) Modifier.rotate(spinAngle) else Modifier)
                        )
                    }
                }

                Spacer(modifier = Modifier.height(10.dp))

                Text(
                    text = Strings.gold24kPerGram(language),
                    color = TextSilver,
                    fontSize = 13.sp,
                    fontWeight = FontWeight.Medium
                )

                Spacer(modifier = Modifier.height(4.dp))

                // Price Big Number + Currency
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.fillMaxWidth()
                ) {
                  Row(
                    verticalAlignment = Alignment.Bottom,
                    modifier = Modifier.weight(1f)
                  ) {
                    AnimatedPriceText(
                        targetAmount = gram24KConverted,
                        isArabic = isArabic,
                        decimals = if (currency == Currency.KWD) 3 else 2,
                        modifier = Modifier.alignByBaseline()
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = currency.getSymbol(isArabic),
                        color = GoldLight,
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                        fontFamily = LuxDisplay,
                        modifier = Modifier.alignByBaseline()
                    )
                  }

                    Spacer(modifier = Modifier.width(8.dp))

                    // 24h Change Pill
                    val changePercent = snapshot?.change24hPercent ?: 1.42
                    val isPositive = changePercent >= 0
                    Box(
                        modifier = Modifier
                            .clip(RoundedCornerShape(20.dp))
                            .background(if (isPositive) Color(0x262ECC71) else Color(0x26E74C3C))
                            .border(0.6.dp, if (isPositive) PriceUpGreen else PriceDownRed, RoundedCornerShape(20.dp))
                            .padding(horizontal = 10.dp, vertical = 4.dp)
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(
                                imageVector = if (isPositive) Icons.Default.ArrowUpward else Icons.Default.ArrowDownward,
                                contentDescription = null,
                                tint = if (isPositive) PriceUpGreen else PriceDownRed,
                                modifier = Modifier.size(13.dp)
                            )
                            Spacer(modifier = Modifier.width(3.dp))
                            Text(
                                text = Localization.formatPercent(changePercent, isArabic),
                                color = if (isPositive) PriceUpGreen else PriceDownRed,
                                fontSize = 12.sp,
                                fontWeight = FontWeight.Bold
                            )
                        }
                    }
                }

                Spacer(modifier = Modifier.height(14.dp))

                // High / Low 24h / Last Updated row
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    val highConverted = (snapshot?.high24hUsd ?: (gram24KUsd * 1.018)) * currency.usdRate
                    val lowConverted = (snapshot?.low24hUsd ?: (gram24KUsd * 0.985)) * currency.usdRate

                    Column(modifier = Modifier.weight(1f)) {
                        Text(
                            text = Strings.high24h(language),
                            color = TextMuted,
                            fontSize = 11.sp,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis
                        )
                        Text(
                            text = Localization.formatPrice(highConverted, currency, isArabic),
                            color = TextWhite,
                            fontSize = 12.sp,
                            fontWeight = FontWeight.SemiBold,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis
                        )
                    }

                    Column(modifier = Modifier.weight(1f)) {
                        Text(
                            text = Strings.low24h(language),
                            color = TextMuted,
                            fontSize = 11.sp,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis
                        )
                        Text(
                            text = Localization.formatPrice(lowConverted, currency, isArabic),
                            color = TextWhite,
                            fontSize = 12.sp,
                            fontWeight = FontWeight.SemiBold,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis
                        )
                    }

                    Column(modifier = Modifier.weight(1f)) {
                        Text(
                            text = Strings.lastUpdated(language),
                            color = TextMuted,
                            fontSize = 11.sp,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis
                        )
                        val timeFmt = SimpleDateFormat("h:mm:ss a", if (isArabic) Locale("ar") else Locale.US)
                        val lastTime = timeFmt.format(Date(snapshot?.lastUpdatedTimestamp ?: System.currentTimeMillis()))
                        Text(
                            text = lastTime,
                            color = GoldLight,
                            fontSize = 12.sp,
                            fontWeight = FontWeight.SemiBold,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis
                        )
                    }
                }

                Spacer(modifier = Modifier.height(12.dp))
                HorizontalDivider(color = Color(0x1AD4AF37), thickness = 0.8.dp)
                Spacer(modifier = Modifier.height(10.dp))

                // Official Dar Al Sabaek Kuwait Benchmark Buy / Sell
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    val buyKwd = snapshot?.base24KKwd ?: 42.775
                    val sellKwd = snapshot?.sell24KKwd ?: 42.330
                    Text(
                        text = if (isArabic) "بورصة الذهب: شراء ${Localization.formatNumber(buyKwd, true, 3)} د.ك | بيع ${Localization.formatNumber(sellKwd, true, 3)} د.ك"
                               else "Gold Market: Buy ${Localization.formatNumber(buyKwd, false, 3)} KD | Sell ${Localization.formatNumber(sellKwd, false, 3)} KD",
                        color = GoldLight,
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Medium,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                        modifier = Modifier.weight(1f, fill = false)
                    )

                    Spacer(modifier = Modifier.width(8.dp))

                    Surface(
                        shape = RoundedCornerShape(6.dp),
                        color = Color(0x22D4AF37),
                        border = androidx.compose.foundation.BorderStroke(0.5.dp, GoldPrimary.copy(alpha = 0.6f))
                    ) {
                        Text(
                            text = if (isArabic) "سوق الكويت" else "Kuwait Spot",
                            color = GoldLight,
                            fontSize = 10.sp,
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                        )
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        var showMarketInfoDialog by remember { mutableStateOf(false) }

        // Interactive Price Chart Section
        Column(modifier = Modifier.padding(horizontal = 20.dp)) {
            SectionHeader(
                title = Strings.interactiveChart(language),
                trailing = {
                    IconButton(
                        onClick = { showMarketInfoDialog = true },
                        modifier = Modifier.size(28.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Default.Info,
                            contentDescription = "Market Info",
                            tint = GoldLight,
                            modifier = Modifier.size(18.dp)
                        )
                    }
                    Spacer(modifier = Modifier.width(6.dp))
                    // 10-Second Live Chart Badge
                    Surface(
                        shape = RoundedCornerShape(8.dp),
                        color = Color(0x22D4AF37),
                        border = androidx.compose.foundation.BorderStroke(0.6.dp, GoldBorder.copy(alpha = 0.5f))
                    ) {
                        Row(
                            modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Box(
                                modifier = Modifier
                                    .size(6.dp)
                                    .clip(CircleShape)
                                    .background(PriceUpGreen)
                            )
                            Spacer(modifier = Modifier.width(5.dp))
                            Text(
                                text = Strings.live10sChart(language),
                                color = GoldLight,
                                fontSize = 11.sp,
                                fontWeight = FontWeight.Medium,
                                maxLines = 1
                            )
                        }
                    }
                }
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = Strings.chartDragHint(language),
                color = TextMuted,
                fontSize = 11.sp,
                modifier = Modifier.padding(start = 14.dp)
            )

            Spacer(modifier = Modifier.height(14.dp))

            GoldCard(
                modifier = Modifier.fillMaxWidth(),
                backgroundColor = DarkSurface,
                borderColor = GoldBorder,
                borderWidth = 0.8.dp
            ) {
                Box(modifier = Modifier.padding(14.dp)) {
                    GoldPriceChart(
                        points = uiState.chartPoints,
                        selectedTimeframe = uiState.selectedTimeframe,
                        onTimeframeSelected = { viewModel.setTimeframe(it) },
                        currency = currency,
                        isArabic = isArabic
                    )
                }
            }
        }

        if (showMarketInfoDialog) {
            AlertDialog(
                onDismissRequest = { showMarketInfoDialog = false },
                confirmButton = {
                    GoldGradientButton(
                        text = if (isArabic) "حسناً، فهمت" else "Understood",
                        onClick = { showMarketInfoDialog = false },
                        height = 42.dp
                    )
                },
                title = {
                    Text(
                        text = Strings.chartRealityHeader(language),
                        color = TextWhite,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold,
                        fontFamily = LuxDisplay
                    )
                },
                text = {
                    Text(
                        text = Strings.chartRealityExplanation(language),
                        color = TextSilver,
                        fontSize = 13.sp,
                        lineHeight = 20.sp
                    )
                },
                containerColor = DarkSurface,
                shape = RoundedCornerShape(20.dp)
            )
        }

        Spacer(modifier = Modifier.height(28.dp))

        // Live Rate Cards (24K, 22K, 21K, 18K, 10g Bar, 20g Bar, 50g Bar, Gold Sovereign)
        Column(modifier = Modifier.fillMaxWidth()) {
            SectionHeader(
                title = Strings.liveRatesHeader(language),
                modifier = Modifier.padding(horizontal = 20.dp)
            )

            Spacer(modifier = Modifier.height(12.dp))

            val rates = snapshot?.rates ?: emptyList()
            LazyRow(
                contentPadding = PaddingValues(horizontal = 20.dp),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                items(rates) { rate ->
                    RateCardItem(
                        rate = rate,
                        currency = currency,
                        isArabic = isArabic
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(28.dp))

        // Circular Categories Row
        Column(modifier = Modifier.fillMaxWidth()) {
            SectionHeader(
                title = Strings.shopByCategory(language),
                modifier = Modifier.padding(horizontal = 20.dp)
            )

            Spacer(modifier = Modifier.height(14.dp))

            val categories = listOf(
                ProductCategory.RINGS,
                ProductCategory.NECKLACES,
                ProductCategory.EARRINGS,
                ProductCategory.BRACELETS,
                ProductCategory.SETS,
                ProductCategory.BARS
            )

            LazyRow(
                contentPadding = PaddingValues(horizontal = 20.dp),
                horizontalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                items(categories) { category ->
                    CategoryCircleItem(
                        category = category,
                        isArabic = isArabic,
                        onClick = { onCategoryClick(category) }
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(28.dp))

        // Featured Products Carousel
        Column(modifier = Modifier.fillMaxWidth()) {
            SectionHeader(
                title = Strings.featuredPieces(language),
                modifier = Modifier.padding(horizontal = 20.dp)
            )

            Spacer(modifier = Modifier.height(14.dp))

            val featured = uiState.products.filter { it.isFeatured }
            LazyRow(
                contentPadding = PaddingValues(horizontal = 20.dp),
                horizontalArrangement = Arrangement.spacedBy(14.dp)
            ) {
                items(featured) { product ->
                    FeaturedProductCard(
                        product = product,
                        gram24KUsd = gram24KUsd,
                        currency = currency,
                        language = language,
                        isWishlisted = uiState.wishlistIds.contains(product.id),
                        onToggleWishlist = { viewModel.toggleWishlist(product.id) },
                        onClick = { onNavigateToProduct(product) }
                    )
                }
            }
        }
    }
}

@Composable
fun RateCardItem(
    rate: GoldRateItem,
    currency: Currency,
    isArabic: Boolean
) {
    val priceConverted = rate.basePriceUsd * currency.usdRate
    val isPositive = rate.changePercent >= 0

    GoldCard(
        modifier = Modifier
            .width(164.dp)
            .height(136.dp),
        shape = RoundedCornerShape(20.dp),
        backgroundColor = DarkSurface,
        borderColor = if (rate.isBullion) GoldPrimary.copy(alpha = 0.5f) else GoldBorder,
        borderWidth = if (rate.isBullion) 1.2.dp else 0.8.dp
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(14.dp),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
          Column {
            // Title & Karat
            Text(
                text = if (isArabic) rate.titleAr else rate.titleEn,
                color = TextWhite,
                fontSize = 13.sp,
                fontWeight = FontWeight.Bold,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
            Text(
                text = if (isArabic) rate.subtitleAr else rate.subtitleEn,
                color = TextMuted,
                fontSize = 11.sp,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
          }

          Column {
            // Price
            Text(
                text = Localization.formatPrice(priceConverted, currency, isArabic),
                color = GoldLight,
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
                fontFamily = LuxDisplay,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )

            Spacer(modifier = Modifier.height(4.dp))

            // Change %
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    imageVector = if (isPositive) Icons.Default.ArrowUpward else Icons.Default.ArrowDownward,
                    contentDescription = null,
                    tint = if (isPositive) PriceUpGreen else PriceDownRed,
                    modifier = Modifier.size(11.dp)
                )
                Spacer(modifier = Modifier.width(2.dp))
                Text(
                    text = Localization.formatPercent(rate.changePercent, isArabic),
                    color = if (isPositive) PriceUpGreen else PriceDownRed,
                    fontSize = 11.sp,
                    fontWeight = FontWeight.SemiBold
                )
            }
          }
        }
    }
}

@Composable
fun CategoryCircleItem(
    category: ProductCategory,
    isArabic: Boolean,
    onClick: () -> Unit
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .width(76.dp)
            .clip(RoundedCornerShape(16.dp))
            .clickable(onClick = onClick)
            .padding(vertical = 4.dp)
    ) {
        Box(
            modifier = Modifier
                .size(62.dp)
                .clip(CircleShape)
                .background(
                    Brush.radialGradient(listOf(Color(0xFF2E2919), DarkSurfaceVariant))
                )
                .border(1.dp, GoldBorder, CircleShape),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector = when (category) {
                    ProductCategory.RINGS -> Icons.Default.Diamond
                    ProductCategory.BARS -> Icons.Default.Verified
                    else -> Icons.Default.Diamond
                },
                contentDescription = if (isArabic) category.nameAr else category.nameEn,
                tint = GoldLight,
                modifier = Modifier.size(26.dp)
            )
        }
        Spacer(modifier = Modifier.height(6.dp))
        Text(
            text = if (isArabic) category.nameAr else category.nameEn,
            color = TextSilver,
            fontSize = 12.sp,
            fontWeight = FontWeight.Medium,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
            textAlign = TextAlign.Center,
            modifier = Modifier.fillMaxWidth()
        )
    }
}

@Composable
fun FeaturedProductCard(
    product: Product,
    gram24KUsd: Double,
    currency: Currency,
    language: AppLanguage,
    isWishlisted: Boolean,
    onToggleWishlist: () -> Unit,
    onClick: () -> Unit
) {
    val isArabic = language == AppLanguage.ARABIC
    val price = product.calculateFinalPrice(gram24KUsd, currency)

    GoldCard(
        modifier = Modifier.width(200.dp),
        backgroundColor = DarkSurface,
        borderColor = GoldBorder,
        borderWidth = 0.8.dp,
        onClick = onClick
    ) {
        Column {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(130.dp)
            ) {
                ProductImageShowcase(
                    product = product,
                    selectedImageIndex = 0,
                    isDetail = false,
                    modifier = Modifier.fillMaxSize()
                )

                // Wishlist icon button
                Box(
                    modifier = Modifier
                        .align(Alignment.TopEnd)
                        .padding(8.dp)
                        .size(32.dp)
                        .clip(CircleShape)
                        .background(Color(0x99000000))
                        .clickable(onClick = onToggleWishlist),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = if (isWishlisted) Icons.Default.Favorite else Icons.Default.FavoriteBorder,
                        contentDescription = "Wishlist",
                        tint = if (isWishlisted) Color(0xFFE74C3C) else Color.White,
                        modifier = Modifier.size(18.dp)
                    )
                }

                // Karat & Weight Badge
                Box(
                    modifier = Modifier
                        .align(Alignment.BottomStart)
                        .padding(8.dp)
                        .clip(RoundedCornerShape(6.dp))
                        .background(Color(0xCC0E0E13))
                        .border(0.6.dp, GoldBorder, RoundedCornerShape(6.dp))
                        .padding(horizontal = 6.dp, vertical = 2.dp)
                ) {
                    Text(
                        text = "${if (isArabic) product.karat.nameAr else product.karat.nameEn} • ${Localization.formatWeight(product.weightInGrams, isArabic)}",
                        color = GoldLight,
                        fontSize = 10.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
            }

            Column(modifier = Modifier.padding(12.dp)) {
                Text(
                    text = if (isArabic) product.nameAr else product.nameEn,
                    color = TextWhite,
                    fontSize = 13.sp,
                    fontWeight = FontWeight.SemiBold,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )

                Spacer(modifier = Modifier.height(4.dp))

                Text(
                    text = Localization.formatPrice(price, currency, isArabic),
                    color = GoldLight,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    fontFamily = LuxDisplay,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
            }
        }
    }
}
