package com.example.goldlux.ui.screens

import com.example.ui.theme.LuxDisplay
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.CardGiftcard
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.DeleteOutline
import androidx.compose.material.icons.filled.LocalShipping
import androidx.compose.material.icons.filled.Payment
import androidx.compose.material.icons.filled.Remove
import androidx.compose.material.icons.filled.ShoppingBag
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.RadioButton
import androidx.compose.material3.RadioButtonDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
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
import com.example.goldlux.model.CartItem
import com.example.goldlux.model.Currency
import com.example.goldlux.ui.components.GoldCard
import com.example.goldlux.ui.components.GoldGradientButton
import com.example.goldlux.ui.viewmodel.GoldLuxUiState
import com.example.goldlux.ui.viewmodel.GoldLuxViewModel
import com.example.ui.theme.DarkBackground
import com.example.ui.theme.DarkSurface
import com.example.ui.theme.DarkSurfaceElevated
import com.example.ui.theme.DarkSurfaceVariant
import com.example.ui.theme.GoldBorder
import com.example.ui.theme.GoldLight
import com.example.ui.theme.GoldPrimary
import com.example.ui.theme.PriceUpGreen
import com.example.ui.theme.TextMuted
import com.example.ui.theme.TextSilver
import com.example.ui.theme.TextWhite

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CartScreen(
    viewModel: GoldLuxViewModel,
    uiState: GoldLuxUiState,
    onExploreClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val language = uiState.language
    val isArabic = language == AppLanguage.ARABIC
    val currency = uiState.currency
    val gram24KUsd = uiState.liveSnapshot?.gram24KUsd ?: 88.80
    var showCheckoutSheet by remember { mutableStateOf(false) }

    // Computations across cart items
    val totalGoldCost = uiState.cartItems.sumOf { it.product.calculateGoldCost(gram24KUsd, currency) * it.quantity }
    val totalMakingFees = uiState.cartItems.sumOf { it.product.calculateMakingFee(currency) * it.quantity }
    val subtotal = totalGoldCost + totalMakingFees
    val totalVat = subtotal * 0.15
    val grandTotal = subtotal + totalVat

    Box(
        modifier = modifier
            .fillMaxSize()
            .background(DarkBackground)
            .padding(bottom = 90.dp)
    ) {
        if (uiState.cartItems.isEmpty()) {
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
                            imageVector = Icons.Default.ShoppingBag,
                            contentDescription = null,
                            tint = GoldLight,
                            modifier = Modifier.size(44.dp)
                        )
                    }

                    Spacer(modifier = Modifier.height(20.dp))

                    Text(
                        text = Strings.emptyCartTitle(language),
                        textAlign = TextAlign.Center,
                        color = TextWhite,
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold,
                        fontFamily = LuxDisplay
                    )

                    Spacer(modifier = Modifier.height(6.dp))

                    Text(
                        text = Strings.emptyCartSubtitle(language),
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
            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                contentPadding = androidx.compose.foundation.layout.PaddingValues(
                    start = 20.dp,
                    end = 20.dp,
                    top = 16.dp,
                    bottom = 30.dp
                )
            ) {
                // Header
                item {
                    Text(
                        text = Strings.shoppingCart(language),
                        color = TextWhite,
                        fontSize = 24.sp,
                        fontWeight = FontWeight.Bold,
                        fontFamily = LuxDisplay
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = "${Localization.formatInt(uiState.cartItems.sumOf { it.quantity }, isArabic)} ${if (isArabic) "قطع مجوهرات" else "items"}",
                        color = TextMuted,
                        fontSize = 13.sp
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                }

                // Cart Item Rows
                items(uiState.cartItems, key = { it.product.id }) { item ->
                    CartItemRow(
                        item = item,
                        gram24KUsd = gram24KUsd,
                        currency = currency,
                        language = language,
                        onQuantityChange = { delta ->
                            viewModel.updateCartQuantity(item.product.id, delta)
                        },
                        onRemove = {
                            viewModel.removeFromCart(item.product.id)
                        }
                    )
                    Spacer(modifier = Modifier.height(12.dp))
                }

                // Complimentary Gift Packaging Note
                item {
                    Spacer(modifier = Modifier.height(8.dp))
                    GoldCard(
                        modifier = Modifier.fillMaxWidth(),
                        backgroundColor = Color(0x1AD4AF37),
                        borderColor = GoldBorder,
                        borderWidth = 0.8.dp
                    ) {
                        Row(
                            modifier = Modifier.padding(14.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Icon(
                                imageVector = Icons.Default.CardGiftcard,
                                contentDescription = null,
                                tint = GoldLight,
                                modifier = Modifier.size(24.dp)
                            )
                            Spacer(modifier = Modifier.width(12.dp))
                            Text(
                                text = Strings.giftPackaging(language),
                                color = GoldLight,
                                fontSize = 12.sp,
                                fontWeight = FontWeight.Medium
                            )
                        }
                    }
                    Spacer(modifier = Modifier.height(16.dp))
                }

                // Order Summary Card
                item {
                    GoldCard(
                        modifier = Modifier.fillMaxWidth(),
                        backgroundColor = DarkSurface,
                        borderColor = GoldBorder,
                        borderWidth = 1.dp
                    ) {
                        Column(modifier = Modifier.padding(18.dp)) {
                            Text(
                                text = Strings.orderSummary(language),
                                color = TextWhite,
                                fontSize = 16.sp,
                                fontWeight = FontWeight.Bold,
                                fontFamily = LuxDisplay
                            )

                            Spacer(modifier = Modifier.height(14.dp))

                            SummaryRow(
                                title = Strings.subtotal(language),
                                value = Localization.formatPrice(totalGoldCost, currency, isArabic)
                            )
                            Spacer(modifier = Modifier.height(8.dp))
                            SummaryRow(
                                title = Strings.totalMakingCharges(language),
                                value = Localization.formatPrice(totalMakingFees, currency, isArabic)
                            )
                            Spacer(modifier = Modifier.height(8.dp))
                            SummaryRow(
                                title = Strings.totalVat(language),
                                value = Localization.formatPrice(totalVat, currency, isArabic)
                            )
                            Spacer(modifier = Modifier.height(8.dp))
                            SummaryRow(
                                title = Strings.deliveryFee(language),
                                value = Strings.free(language),
                                highlightColor = PriceUpGreen
                            )

                            Spacer(modifier = Modifier.height(14.dp))
                            HorizontalDivider(color = Color(0x33D4AF37), thickness = 0.8.dp)
                            Spacer(modifier = Modifier.height(12.dp))

                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Text(
                                    text = Strings.grandTotal(language),
                                    color = TextWhite,
                                    fontSize = 16.sp,
                                    fontWeight = FontWeight.Bold
                                )
                                Text(
                                    text = Localization.formatPrice(grandTotal, currency, isArabic),
                                    color = GoldLight,
                                    fontSize = 20.sp,
                                    fontWeight = FontWeight.Bold,
                                    fontFamily = LuxDisplay
                                )
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(20.dp))

                    // Checkout Button
                    GoldGradientButton(
                        text = Strings.proceedToCheckout(language),
                        onClick = { showCheckoutSheet = true },
                        modifier = Modifier.fillMaxWidth(),
                        height = 54.dp
                    )
                }
            }
        }

        // Checkout Bottom Sheet
        if (showCheckoutSheet) {
            ModalBottomSheet(
                onDismissRequest = { showCheckoutSheet = false },
                sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true),
                containerColor = DarkSurfaceElevated,
                dragHandle = null
            ) {
                CheckoutSheetContent(
                    grandTotal = grandTotal,
                    currency = currency,
                    language = language,
                    onConfirmOrder = {
                        showCheckoutSheet = false
                        viewModel.checkoutOrder()
                    }
                )
            }
        }

        // Order Success Celebratory Dialog
        if (uiState.showCheckoutSuccessDialog) {
            AlertDialog(
                onDismissRequest = { viewModel.dismissCheckoutSuccessDialog() },
                confirmButton = {
                    GoldGradientButton(
                        text = Strings.backToHome(language),
                        onClick = {
                            viewModel.dismissCheckoutSuccessDialog()
                            onExploreClick()
                        },
                        height = 46.dp
                    )
                },
                icon = {
                    Box(
                        modifier = Modifier
                            .size(70.dp)
                            .clip(CircleShape)
                            .background(Color(0x262ECC71)),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = Icons.Default.CheckCircle,
                            contentDescription = null,
                            tint = PriceUpGreen,
                            modifier = Modifier.size(46.dp)
                        )
                    }
                },
                title = {
                    Text(
                        text = Strings.orderSuccessTitle(language),
                        color = TextWhite,
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                        fontFamily = LuxDisplay
                    )
                },
                text = {
                    Text(
                        text = Strings.orderSuccessDesc(language),
                        color = TextSilver,
                        fontSize = 13.sp,
                        lineHeight = 20.sp
                    )
                },
                containerColor = DarkSurface,
                shape = RoundedCornerShape(24.dp)
            )
        }
    }
}

@Composable
fun CartItemRow(
    item: CartItem,
    gram24KUsd: Double,
    currency: Currency,
    language: AppLanguage,
    onQuantityChange: (Int) -> Unit,
    onRemove: () -> Unit
) {
    val isArabic = language == AppLanguage.ARABIC
    val unitPrice = item.product.calculateFinalPrice(gram24KUsd, currency)
    val itemTotal = unitPrice * item.quantity

    GoldCard(
        modifier = Modifier.fillMaxWidth(),
        backgroundColor = DarkSurface,
        borderColor = GoldBorder,
        borderWidth = 0.8.dp
    ) {
        Row(
            modifier = Modifier.padding(12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Product Thumbnail
            AsyncImage(
                model = ImageRequest.Builder(LocalContext.current)
                    .data(item.product.imageUrls.firstOrNull())
                    .crossfade(true)
                    .build(),
                contentDescription = if (isArabic) item.product.nameAr else item.product.nameEn,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .size(74.dp)
                    .clip(RoundedCornerShape(12.dp))
                    .background(DarkSurfaceVariant)
            )

            Spacer(modifier = Modifier.width(12.dp))

            // Details
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = if (isArabic) item.product.nameAr else item.product.nameEn,
                    color = TextWhite,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.SemiBold,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )

                Spacer(modifier = Modifier.height(2.dp))

                Text(
                    text = "${if (isArabic) item.product.karat.nameAr else item.product.karat.nameEn} • ${Localization.formatWeight(item.product.weightInGrams, isArabic)}",
                    color = TextMuted,
                    fontSize = 11.sp
                )

                Spacer(modifier = Modifier.height(6.dp))

                Text(
                    text = Localization.formatPrice(itemTotal, currency, isArabic),
                    color = GoldLight,
                    fontSize = 15.sp,
                    fontWeight = FontWeight.Bold,
                    fontFamily = LuxDisplay
                )
            }

            Spacer(modifier = Modifier.width(8.dp))

            // Quantity stepper & delete button
            Column(horizontalAlignment = Alignment.End) {
                IconButton(
                    onClick = onRemove,
                    modifier = Modifier.size(28.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.DeleteOutline,
                        contentDescription = "Delete",
                        tint = Color(0xFFE74C3C),
                        modifier = Modifier.size(18.dp)
                    )
                }

                Spacer(modifier = Modifier.height(8.dp))

                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .clip(RoundedCornerShape(8.dp))
                        .background(DarkSurfaceVariant)
                        .border(0.6.dp, GoldBorder, RoundedCornerShape(8.dp))
                        .padding(horizontal = 4.dp, vertical = 2.dp)
                ) {
                    IconButton(
                        onClick = { onQuantityChange(-1) },
                        modifier = Modifier.size(24.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Default.Remove,
                            contentDescription = "-",
                            tint = GoldLight,
                            modifier = Modifier.size(13.dp)
                        )
                    }

                    Text(
                        text = Localization.formatInt(item.quantity, isArabic),
                        color = TextWhite,
                        fontSize = 13.sp,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(horizontal = 6.dp)
                    )

                    IconButton(
                        onClick = { onQuantityChange(1) },
                        modifier = Modifier.size(24.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Default.Add,
                            contentDescription = "+",
                            tint = GoldLight,
                            modifier = Modifier.size(13.dp)
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun SummaryRow(
    title: String,
    value: String,
    highlightColor: Color = TextWhite
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = title,
            color = TextSilver,
            fontSize = 13.sp
        )
        Text(
            text = value,
            color = highlightColor,
            fontSize = 13.sp,
            fontWeight = FontWeight.SemiBold
        )
    }
}

@Composable
fun CheckoutSheetContent(
    grandTotal: Double,
    currency: Currency,
    language: AppLanguage,
    onConfirmOrder: () -> Unit
) {
    val isArabic = language == AppLanguage.ARABIC

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(24.dp)
    ) {
        Text(
            text = Strings.checkoutTitle(language),
            color = TextWhite,
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            fontFamily = LuxDisplay
        )

        Spacer(modifier = Modifier.height(18.dp))

        // Delivery Address
        GoldCard(
            modifier = Modifier.fillMaxWidth(),
            backgroundColor = DarkSurface,
            borderColor = GoldBorder,
            borderWidth = 0.8.dp
        ) {
            Row(
                modifier = Modifier.padding(14.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    imageVector = Icons.Default.LocalShipping,
                    contentDescription = null,
                    tint = GoldLight,
                    modifier = Modifier.size(22.dp)
                )
                Spacer(modifier = Modifier.width(12.dp))
                Column {
                    Text(
                        text = Strings.deliveryAddress(language),
                        color = TextWhite,
                        fontSize = 13.sp,
                        fontWeight = FontWeight.SemiBold
                    )
                    Spacer(modifier = Modifier.height(2.dp))
                    Text(
                        text = Strings.defaultAddress(language),
                        color = TextMuted,
                        fontSize = 11.sp
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(12.dp))

        // Payment Method
        GoldCard(
            modifier = Modifier.fillMaxWidth(),
            backgroundColor = DarkSurface,
            borderColor = GoldBorder,
            borderWidth = 0.8.dp
        ) {
            Row(
                modifier = Modifier.padding(14.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    imageVector = Icons.Default.Payment,
                    contentDescription = null,
                    tint = GoldLight,
                    modifier = Modifier.size(22.dp)
                )
                Spacer(modifier = Modifier.width(12.dp))
                Column {
                    Text(
                        text = Strings.paymentMethod(language),
                        color = TextWhite,
                        fontSize = 13.sp,
                        fontWeight = FontWeight.SemiBold
                    )
                    Spacer(modifier = Modifier.height(2.dp))
                    Text(
                        text = Strings.applePayMada(language),
                        color = GoldLight,
                        fontSize = 11.sp
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(20.dp))

        // Total Row
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = Strings.grandTotal(language),
                color = TextWhite,
                fontSize = 15.sp,
                fontWeight = FontWeight.SemiBold
            )
            Text(
                text = Localization.formatPrice(grandTotal, currency, isArabic),
                color = GoldLight,
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                fontFamily = LuxDisplay
            )
        }

        Spacer(modifier = Modifier.height(20.dp))

        // Confirm Button
        GoldGradientButton(
            text = Strings.placeOrder(language),
            onClick = onConfirmOrder,
            modifier = Modifier.fillMaxWidth(),
            height = 52.dp
        )

        Spacer(modifier = Modifier.height(10.dp))
    }
}
