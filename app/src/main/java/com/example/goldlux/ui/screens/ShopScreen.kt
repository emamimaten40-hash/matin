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
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Chat
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import com.example.goldlux.ui.components.ProductImageShowcase
import com.example.goldlux.util.ContactUtils
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
import com.example.goldlux.model.Karat
import com.example.goldlux.model.Product
import com.example.goldlux.model.ProductCategory
import com.example.goldlux.ui.components.GoldCard
import com.example.goldlux.ui.components.GoldGradientButton
import com.example.goldlux.ui.viewmodel.GoldLuxUiState
import com.example.goldlux.ui.viewmodel.GoldLuxViewModel
import com.example.ui.theme.DarkBackground
import com.example.ui.theme.DarkSurface
import com.example.ui.theme.DarkSurfaceVariant
import com.example.ui.theme.GoldBorder
import com.example.ui.theme.GoldLight
import com.example.ui.theme.GoldPrimary
import com.example.ui.theme.TextMuted
import com.example.ui.theme.TextSilver
import com.example.ui.theme.TextWhite

@Composable
fun ShopScreen(
    viewModel: GoldLuxViewModel,
    uiState: GoldLuxUiState,
    onNavigateToProduct: (Product) -> Unit,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    val language = uiState.language
    val isArabic = language == AppLanguage.ARABIC
    val currency = uiState.currency
    val gram24KUsd = uiState.liveSnapshot?.gram24KUsd ?: 88.80

    // Filter products by category, karat, and search query
    val filteredProducts = uiState.products.filter { product ->
        val matchesCategory = uiState.selectedCategory == ProductCategory.ALL || product.category == uiState.selectedCategory
        val matchesKarat = uiState.selectedKaratFilter == null || product.karat == uiState.selectedKaratFilter
        val matchesQuery = uiState.searchQuery.isBlank() ||
                product.nameEn.contains(uiState.searchQuery, ignoreCase = true) ||
                product.nameAr.contains(uiState.searchQuery, ignoreCase = true) ||
                product.descriptionEn.contains(uiState.searchQuery, ignoreCase = true) ||
                product.descriptionAr.contains(uiState.searchQuery, ignoreCase = true) ||
                product.displayCode.contains(uiState.searchQuery.trim(), ignoreCase = true)
        matchesCategory && matchesKarat && matchesQuery
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(DarkBackground)
    ) {
        // Screen Title
        Column(modifier = Modifier.padding(horizontal = 20.dp, vertical = 14.dp)) {
            Text(
                text = Strings.tabShop(language),
                color = TextWhite,
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                fontFamily = LuxDisplay
            )
            Spacer(modifier = Modifier.height(2.dp))
            Text(
                text = "${Localization.formatInt(filteredProducts.size, isArabic)} ${Strings.itemsFound(language)}",
                color = TextMuted,
                fontSize = 12.sp
            )
        }

        // Search Bar
        OutlinedTextField(
            value = uiState.searchQuery,
            onValueChange = { viewModel.setSearchQuery(it) },
            placeholder = {
                Text(
                    text = Strings.searchPlaceholder(language),
                    color = TextMuted,
                    fontSize = 13.sp
                )
            },
            leadingIcon = {
                Icon(
                    imageVector = Icons.Default.Search,
                    contentDescription = "Search",
                    tint = GoldLight,
                    modifier = Modifier.size(20.dp)
                )
            },
            trailingIcon = {
                if (uiState.searchQuery.isNotEmpty()) {
                    IconButton(onClick = { viewModel.setSearchQuery("") }) {
                        Icon(
                            imageVector = Icons.Default.Clear,
                            contentDescription = "Clear",
                            tint = TextMuted,
                            modifier = Modifier.size(18.dp)
                        )
                    }
                }
            },
            shape = RoundedCornerShape(16.dp),
            colors = TextFieldDefaults.colors(
                focusedContainerColor = DarkSurface,
                unfocusedContainerColor = DarkSurface,
                focusedTextColor = TextWhite,
                unfocusedTextColor = TextWhite,
                focusedIndicatorColor = GoldPrimary,
                unfocusedIndicatorColor = GoldBorder
            ),
            singleLine = true,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp)
        )

        Spacer(modifier = Modifier.height(14.dp))

        // Category Filter Chips
        val categories = ProductCategory.values()
        LazyRow(
            contentPadding = PaddingValues(horizontal = 20.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(categories) { category ->
                val isSelected = category == uiState.selectedCategory
                Surface(
                    shape = RoundedCornerShape(20.dp),
                    color = if (isSelected) GoldPrimary else DarkSurface,
                    border = if (isSelected) null else androidx.compose.foundation.BorderStroke(0.6.dp, GoldBorder),
                    modifier = Modifier
                        .height(36.dp)
                        .clip(RoundedCornerShape(20.dp))
                        .clickable { viewModel.setSelectedCategory(category) }
                ) {
                    Box(
                        contentAlignment = Alignment.Center,
                        modifier = Modifier.padding(horizontal = 14.dp)
                    ) {
                        Text(
                            text = if (isArabic) category.nameAr else category.nameEn,
                            color = if (isSelected) Color(0xFF0E0E13) else TextSilver,
                            fontSize = 12.sp,
                            fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Medium
                        )
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(10.dp))

        // Karat Filter Chips Row (All, 24K, 22K, 21K, 18K)
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = Strings.filterKarat(language),
                color = TextMuted,
                fontSize = 11.sp,
                fontWeight = FontWeight.Medium
            )
            Spacer(modifier = Modifier.width(8.dp))

            val karats = listOf<Karat?>(null, Karat.K24, Karat.K22, Karat.K21, Karat.K18)
            LazyRow(horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                items(karats) { karat ->
                    val isSelected = karat == uiState.selectedKaratFilter
                    val label = if (karat == null) {
                        Strings.allCategories(language)
                    } else {
                        if (isArabic) karat.nameAr else karat.nameEn
                    }

                    Box(
                        modifier = Modifier
                            .clip(RoundedCornerShape(8.dp))
                            .background(if (isSelected) GoldLight.copy(alpha = 0.2f) else Color.Transparent)
                            .border(
                                0.6.dp,
                                if (isSelected) GoldLight else Color(0x33D4AF37),
                                RoundedCornerShape(8.dp)
                            )
                            .clickable { viewModel.setSelectedKaratFilter(karat) }
                            .padding(horizontal = 8.dp, vertical = 3.dp)
                    ) {
                        Text(
                            text = label,
                            color = if (isSelected) GoldLight else TextSilver,
                            fontSize = 11.sp,
                            fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal
                        )
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(14.dp))

        // Product Grid or Empty State
        if (filteredProducts.isEmpty()) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(32.dp),
                contentAlignment = Alignment.Center
            ) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text(
                        text = Strings.noProductsFound(language),
                        textAlign = TextAlign.Center,
                        color = TextWhite,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.SemiBold
                    )
                    Spacer(modifier = Modifier.height(14.dp))
                    GoldGradientButton(
                        text = Strings.resetFilters(language),
                        onClick = {
                            viewModel.setSearchQuery("")
                            viewModel.setSelectedCategory(ProductCategory.ALL)
                            viewModel.setSelectedKaratFilter(null)
                        },
                        modifier = Modifier.width(180.dp),
                        height = 44.dp
                    )
                }
            }
        } else {
            LazyVerticalGrid(
                columns = GridCells.Fixed(2),
                contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp),
                horizontalArrangement = Arrangement.spacedBy(12.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp),
                modifier = Modifier.fillMaxSize()
            ) {
                items(filteredProducts, key = { it.id }) { product ->
                    ProductGridCard(
                        product = product,
                        gram24KUsd = gram24KUsd,
                        currency = currency,
                        language = language,
                        isWishlisted = uiState.wishlistIds.contains(product.id),
                        onToggleWishlist = { viewModel.toggleWishlist(product.id) },
                        onAddToCart = {
                            val currentPrice = product.calculateFinalPrice(gram24KUsd, currency)
                            ContactUtils.openWhatsApp(
                                context = context,
                                product = product,
                                livePrice = currentPrice,
                                currency = currency,
                                isArabic = isArabic
                            )
                        },
                        onClick = { onNavigateToProduct(product) }
                    )
                }
            }
        }
    }
}

@Composable
fun ProductGridCard(
    product: Product,
    gram24KUsd: Double,
    currency: Currency,
    language: AppLanguage,
    isWishlisted: Boolean,
    onToggleWishlist: () -> Unit,
    onAddToCart: () -> Unit,
    onClick: () -> Unit
) {
    val isArabic = language == AppLanguage.ARABIC
    val price = product.calculateFinalPrice(gram24KUsd, currency)
    val cardShape = RoundedCornerShape(16.dp)

    GoldCard(
        modifier = Modifier
            .fillMaxWidth()
            .height(268.dp)
            .clip(cardShape),
        shape = cardShape,
        backgroundColor = DarkSurface,
        borderColor = GoldBorder,
        borderWidth = 0.8.dp,
        onClick = onClick
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .clip(cardShape),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Column(modifier = Modifier.fillMaxWidth()) {
                // Product Image with badges & wishlist (Guaranteed visual, never black)
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(148.dp)
                        .clip(RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp))
                ) {
                    ProductImageShowcase(
                        product = product,
                        selectedImageIndex = 0,
                        isDetail = false,
                        modifier = Modifier.fillMaxSize()
                    )

                    // Wishlist Heart Button
                    Box(
                        modifier = Modifier
                            .align(Alignment.TopEnd)
                            .padding(8.dp)
                            .size(32.dp)
                            .clip(CircleShape)
                            .background(Color(0xAA0E0E13))
                            .border(0.5.dp, GoldBorder.copy(alpha = 0.5f), CircleShape)
                            .clickable(onClick = onToggleWishlist),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = if (isWishlisted) Icons.Default.Favorite else Icons.Default.FavoriteBorder,
                            contentDescription = "Wishlist",
                            tint = if (isWishlisted) Color(0xFFE74C3C) else Color.White,
                            modifier = Modifier.size(17.dp)
                        )
                    }

                    // Karat & Weight pill
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
                            text = "${product.displayCode} • ${if (isArabic) product.karat.nameAr else product.karat.nameEn}",
                            color = GoldLight,
                            fontSize = 10.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }

                // Name container strictly 40dp height for identical alignment
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 10.dp, vertical = 6.dp),
                    contentAlignment = Alignment.CenterStart
                ) {
                    Text(
                        text = if (isArabic) product.nameAr else product.nameEn,
                        color = TextWhite,
                        fontSize = 13.sp,
                        fontWeight = FontWeight.SemiBold,
                        maxLines = 2,
                        overflow = TextOverflow.Ellipsis,
                        lineHeight = 18.sp,
                        modifier = Modifier.height(38.dp)
                    )
                }
            }

            // Price & Quick Action Row anchored at the bottom
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 10.dp, end = 10.dp, bottom = 10.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = Localization.formatPrice(price, currency, isArabic),
                    color = GoldLight,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Bold,
                    fontFamily = LuxDisplay,
                    modifier = Modifier.weight(1f, fill = false),
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )

                // Quick WhatsApp Contact Button
                Surface(
                    shape = RoundedCornerShape(10.dp),
                    color = Color(0xFF25D366),
                    modifier = Modifier
                        .clip(RoundedCornerShape(10.dp))
                        .clickable(onClick = onAddToCart)
                ) {
                    Row(
                        modifier = Modifier.padding(horizontal = 8.dp, vertical = 5.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            imageVector = Icons.Default.Chat,
                            contentDescription = "WhatsApp",
                            tint = Color.White,
                            modifier = Modifier.size(13.dp)
                        )
                        Spacer(modifier = Modifier.width(4.dp))
                        Text(
                            text = if (isArabic) "واتساب" else "WhatsApp",
                            color = Color.White,
                            fontSize = 11.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }
            }
        }
    }
}
