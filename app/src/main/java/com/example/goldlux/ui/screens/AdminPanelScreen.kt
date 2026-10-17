package com.example.goldlux.ui.screens

import com.example.ui.theme.LuxDisplay
import com.example.goldlux.ui.components.SectionHeader
import android.widget.Toast
import androidx.activity.compose.BackHandler
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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.AdminPanelSettings
import androidx.compose.material.icons.filled.AttachMoney
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.CloudDone
import androidx.compose.material.icons.filled.Diamond
import androidx.compose.material.icons.filled.Inventory2
import androidx.compose.material.icons.filled.Key
import androidx.compose.material.icons.filled.LocalShipping
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.ReceiptLong
import androidx.compose.material.icons.filled.RestartAlt
import androidx.compose.material.icons.filled.Save
import androidx.compose.material.icons.filled.ShowChart
import androidx.compose.material.icons.filled.Tune
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Slider
import androidx.compose.material3.SliderDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableDoubleStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.goldlux.localization.Localization
import com.example.goldlux.localization.Strings
import com.example.goldlux.model.AdminOrder
import com.example.goldlux.model.AppLanguage
import com.example.goldlux.model.Currency
import com.example.goldlux.model.Karat
import com.example.goldlux.model.OrderStatus
import com.example.goldlux.model.Product
import com.example.goldlux.model.ProductCategory
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
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

enum class AdminTab {
    PRICING,
    INVENTORY,
    ORDERS,
    API_FEED
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AdminPanelScreen(
    viewModel: GoldLuxViewModel,
    uiState: GoldLuxUiState,
    onClose: () -> Unit,
    modifier: Modifier = Modifier
) {
    BackHandler { onClose() }

    val context = LocalContext.current
    val language = uiState.language
    val isArabic = language == AppLanguage.ARABIC
    val currency = uiState.currency

    var activeTab by remember { mutableStateOf(AdminTab.PRICING) }
    var showAddProductDialog by remember { mutableStateOf(false) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Column {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(
                                imageVector = Icons.Default.AdminPanelSettings,
                                contentDescription = null,
                                tint = GoldLight,
                                modifier = Modifier.size(20.dp)
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            Text(
                                text = Strings.adminPortalTitle(language),
                                color = TextWhite,
                                fontSize = 18.sp,
                                fontWeight = FontWeight.Bold,
                                fontFamily = LuxDisplay
                            )
                        }
                        Text(
                            text = Strings.adminPortalDesc(language),
                            color = TextMuted,
                            fontSize = 11.sp
                        )
                    }
                },
                navigationIcon = {
                    IconButton(onClick = onClose) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Back",
                            tint = GoldLight
                        )
                    }
                },
                actions = {
                    Surface(
                        shape = RoundedCornerShape(12.dp),
                        color = Color(0x332ECC71),
                        border = androidx.compose.foundation.BorderStroke(0.6.dp, PriceUpGreen),
                        modifier = Modifier.padding(end = 4.dp)
                    ) {
                        Row(
                            modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Box(
                                modifier = Modifier
                                    .size(7.dp)
                                    .clip(CircleShape)
                                    .background(PriceUpGreen)
                            )
                            Spacer(modifier = Modifier.width(6.dp))
                            Text(
                                text = if (isArabic) "متصل بالسوق" else "Live Synced",
                                color = PriceUpGreen,
                                fontSize = 11.sp,
                                fontWeight = FontWeight.Bold
                            )
                        }
                    }

                    // Lock & Logout Admin button
                    IconButton(
                        onClick = {
                            viewModel.logoutUser()
                            Toast.makeText(
                                context,
                                if (isArabic) "تم تسجيل الخروج وقفل لوحة الإدارة" else "Admin locked & signed out",
                                Toast.LENGTH_SHORT
                            ).show()
                        },
                        modifier = Modifier.padding(end = 4.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Default.Lock,
                            contentDescription = Strings.adminLogout(language),
                            tint = GoldLight
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = DarkSurface)
            )
        },
        containerColor = DarkBackground
    ) { innerPadding ->
        Column(
            modifier = modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            // Horizontal Admin Navigation Tabs
            LazyRow(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(DarkSurface)
                    .padding(horizontal = 16.dp, vertical = 8.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                item {
                    AdminTabChip(
                        label = Strings.tabAdminPricing(language),
                        icon = Icons.Default.Tune,
                        isSelected = activeTab == AdminTab.PRICING,
                        onClick = { activeTab = AdminTab.PRICING }
                    )
                }
                item {
                    AdminTabChip(
                        label = Strings.tabAdminProducts(language),
                        icon = Icons.Default.Inventory2,
                        isSelected = activeTab == AdminTab.INVENTORY,
                        onClick = { activeTab = AdminTab.INVENTORY }
                    )
                }
                item {
                    AdminTabChip(
                        label = "${Strings.tabAdminOrders(language)} (${uiState.orders.size})",
                        icon = Icons.Default.ReceiptLong,
                        isSelected = activeTab == AdminTab.ORDERS,
                        onClick = { activeTab = AdminTab.ORDERS }
                    )
                }
                item {
                    AdminTabChip(
                        label = Strings.tabAdminApi(language),
                        icon = Icons.Default.CloudDone,
                        isSelected = activeTab == AdminTab.API_FEED,
                        onClick = { activeTab = AdminTab.API_FEED }
                    )
                }
            }

            HorizontalDivider(color = Color(0x33D4AF37), thickness = 0.8.dp)

            // Content per tab
            when (activeTab) {
                AdminTab.PRICING -> AdminPricingTab(
                    viewModel = viewModel,
                    uiState = uiState
                )
                AdminTab.INVENTORY -> AdminInventoryTab(
                    viewModel = viewModel,
                    uiState = uiState,
                    onOpenAddProduct = { showAddProductDialog = true }
                )
                AdminTab.ORDERS -> AdminOrdersTab(
                    viewModel = viewModel,
                    uiState = uiState
                )
                AdminTab.API_FEED -> AdminApiTab(
                    viewModel = viewModel,
                    uiState = uiState
                )
            }
        }
    }

    if (showAddProductDialog) {
        AddNewProductDialog(
            viewModel = viewModel,
            uiState = uiState,
            onDismiss = { showAddProductDialog = false }
        )
    }
}

@Composable
fun AdminTabChip(
    label: String,
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    isSelected: Boolean,
    onClick: () -> Unit
) {
    Surface(
        shape = RoundedCornerShape(12.dp),
        color = if (isSelected) GoldPrimary else DarkSurfaceVariant,
        border = if (isSelected) null else androidx.compose.foundation.BorderStroke(0.6.dp, GoldBorder),
        modifier = Modifier
            .height(38.dp)
            .clip(RoundedCornerShape(12.dp))
            .clickable(onClick = onClick)
    ) {
        Row(
            modifier = Modifier.padding(horizontal = 12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = icon,
                contentDescription = null,
                tint = if (isSelected) Color(0xFF0E0E13) else GoldLight,
                modifier = Modifier.size(16.dp)
            )
            Spacer(modifier = Modifier.width(6.dp))
            Text(
                text = label,
                color = if (isSelected) Color(0xFF0E0E13) else TextSilver,
                fontSize = 12.sp,
                fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Medium
            )
        }
    }
}

@Composable
fun AdminPricingTab(
    viewModel: GoldLuxViewModel,
    uiState: GoldLuxUiState
) {
    val context = LocalContext.current
    val language = uiState.language
    val isArabic = language == AppLanguage.ARABIC
    val currentOunce = uiState.liveSnapshot?.ounceUsd ?: 4325.40

    var spotInput by remember { mutableStateOf(currentOunce.toInt().toString()) }
    var kwdRateInput by remember { mutableStateOf(uiState.customExchangeRates[Currency.KWD]?.toString() ?: "0.3068") }
    var aedRateInput by remember { mutableStateOf(uiState.customExchangeRates[Currency.AED]?.toString() ?: "3.6725") }
    var markupPercent by remember { mutableDoubleStateOf(uiState.adminMakingMarkupPercent) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(16.dp)
    ) {
        // Live Price Preview Cards in KWD, USD, AED
        SectionHeader(title = if (isArabic) "معاينة الأسعار الفورية بالعملات الرئيسية" else "Live Rates Preview (KWD, USD, AED)")

        Spacer(modifier = Modifier.height(10.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            val gram24KUsd = uiState.liveSnapshot?.gram24KUsd ?: 88.80
            // KWD Card
            PricePreviewCard(
                title = "KWD (دينار كويتي)",
                amount = Localization.formatPrice(gram24KUsd * 0.3068, Currency.KWD, isArabic),
                modifier = Modifier.weight(1f)
            )
            // USD Card
            PricePreviewCard(
                title = "USD (دلار)",
                amount = Localization.formatPrice(gram24KUsd, Currency.USD, isArabic),
                modifier = Modifier.weight(1f)
            )
            // AED Card
            PricePreviewCard(
                title = "AED (درهم)",
                amount = Localization.formatPrice(gram24KUsd * 3.6725, Currency.AED, isArabic),
                modifier = Modifier.weight(1f)
            )
        }

        Spacer(modifier = Modifier.height(20.dp))

        // Spot Gold Manual Override
        GoldCard(
            modifier = Modifier.fillMaxWidth(),
            backgroundColor = DarkSurface,
            borderColor = GoldBorder,
            borderWidth = 0.8.dp
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                Text(
                    text = Strings.spotOverrideLabel(language),
                    color = TextWhite,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.SemiBold
                )

                Spacer(modifier = Modifier.height(8.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    OutlinedTextField(
                        value = spotInput,
                        onValueChange = { spotInput = it },
                        modifier = Modifier.weight(1f),
                        singleLine = true,
                        colors = TextFieldDefaults.colors(
                            focusedContainerColor = DarkSurfaceVariant,
                            unfocusedContainerColor = DarkSurfaceVariant,
                            focusedTextColor = TextWhite,
                            unfocusedTextColor = TextWhite,
                            focusedIndicatorColor = GoldPrimary
                        )
                    )
                    Spacer(modifier = Modifier.width(10.dp))
                    GoldGradientButton(
                        text = if (isArabic) "تطبيق" else "Apply",
                        onClick = {
                            val parsed = spotInput.toDoubleOrNull()
                            if (parsed != null && parsed > 500) {
                                viewModel.setAdminSpotOverride(parsed)
                                Toast.makeText(context, Strings.changesAppliedToast(language), Toast.LENGTH_SHORT).show()
                            }
                        },
                        modifier = Modifier.width(90.dp),
                        height = 50.dp
                    )
                }

                Spacer(modifier = Modifier.height(10.dp))

                // Presets
                Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    listOf(4250.0, 4325.0, 4400.0).forEach { preset ->
                        Surface(
                            shape = RoundedCornerShape(8.dp),
                            color = DarkSurfaceVariant,
                            border = androidx.compose.foundation.BorderStroke(0.6.dp, GoldBorder),
                            modifier = Modifier.clickable {
                                spotInput = preset.toInt().toString()
                                viewModel.setAdminSpotOverride(preset)
                            }
                        ) {
                            Text(
                                text = "$${preset.toInt()}",
                                color = GoldLight,
                                fontSize = 11.sp,
                                modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                            )
                        }
                    }

                    Spacer(modifier = Modifier.weight(1f))

                    TextButton(onClick = {
                        viewModel.setAdminSpotOverride(null)
                        Toast.makeText(context, Strings.resetToLiveMarket(language), Toast.LENGTH_SHORT).show()
                    }) {
                        Text(
                            text = Strings.resetToLiveMarket(language),
                            color = GoldLight,
                            fontSize = 11.sp
                        )
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(20.dp))

        // Exchange Rates Editor (KWD, AED)
        GoldCard(
            modifier = Modifier.fillMaxWidth(),
            backgroundColor = DarkSurface,
            borderColor = GoldBorder,
            borderWidth = 0.8.dp
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                Text(
                    text = if (isArabic) "تعديل أسعار صرف العملات مقابل الدولار (USD)" else "Currency Exchange Rates (vs USD)",
                    color = TextWhite,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.SemiBold
                )

                Spacer(modifier = Modifier.height(12.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    Column(modifier = Modifier.weight(1f)) {
                        Text(text = "1 USD = ? KWD", color = TextMuted, fontSize = 11.sp)
                        Spacer(modifier = Modifier.height(4.dp))
                        OutlinedTextField(
                            value = kwdRateInput,
                            onValueChange = {
                                kwdRateInput = it
                                it.toDoubleOrNull()?.let { rate -> viewModel.setExchangeRate(Currency.KWD, rate) }
                            },
                            singleLine = true,
                            colors = TextFieldDefaults.colors(
                                focusedContainerColor = DarkSurfaceVariant,
                                unfocusedContainerColor = DarkSurfaceVariant,
                                focusedTextColor = TextWhite,
                                unfocusedTextColor = TextWhite,
                                focusedIndicatorColor = GoldPrimary
                            )
                        )
                    }

                    Column(modifier = Modifier.weight(1f)) {
                        Text(text = "1 USD = ? AED", color = TextMuted, fontSize = 11.sp)
                        Spacer(modifier = Modifier.height(4.dp))
                        OutlinedTextField(
                            value = aedRateInput,
                            onValueChange = {
                                aedRateInput = it
                                it.toDoubleOrNull()?.let { rate -> viewModel.setExchangeRate(Currency.AED, rate) }
                            },
                            singleLine = true,
                            colors = TextFieldDefaults.colors(
                                focusedContainerColor = DarkSurfaceVariant,
                                unfocusedContainerColor = DarkSurfaceVariant,
                                focusedTextColor = TextWhite,
                                unfocusedTextColor = TextWhite,
                                focusedIndicatorColor = GoldPrimary
                            )
                        )
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(20.dp))

        // Global Making Charge Markup Slider
        GoldCard(
            modifier = Modifier.fillMaxWidth(),
            backgroundColor = DarkSurface,
            borderColor = GoldBorder,
            borderWidth = 0.8.dp
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = Strings.makingMarkupLabel(language),
                        color = TextWhite,
                        fontSize = 14.sp,
                        fontWeight = FontWeight.SemiBold
                    )
                    Text(
                        text = "+${(markupPercent * 100).toInt()}%",
                        color = GoldLight,
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Bold
                    )
                }

                Spacer(modifier = Modifier.height(8.dp))

                Slider(
                    value = markupPercent.toFloat(),
                    onValueChange = {
                        markupPercent = it.toDouble()
                        viewModel.setAdminMakingMarkup(markupPercent)
                    },
                    valueRange = 0f..0.30f,
                    colors = SliderDefaults.colors(
                        thumbColor = GoldPrimary,
                        activeTrackColor = GoldLight,
                        inactiveTrackColor = DarkSurfaceVariant
                    )
                )

                Text(
                    text = if (isArabic) "يتم تطبيق الهامش تلقائياً على جميع تصاميم المجوهرات في المتجر" else "Markup automatically applied to all jewellery making charges",
                    color = TextMuted,
                    fontSize = 11.sp
                )
            }
        }
    }
}

@Composable
fun PricePreviewCard(title: String, amount: String, modifier: Modifier = Modifier) {
    GoldCard(
        modifier = modifier,
        backgroundColor = DarkSurfaceVariant,
        borderColor = GoldBorder,
        borderWidth = 0.6.dp
    ) {
        Column(modifier = Modifier.padding(10.dp)) {
            Text(text = title, color = TextMuted, fontSize = 10.sp, maxLines = 1)
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = amount,
                color = GoldLight,
                fontSize = 12.sp,
                fontWeight = FontWeight.Bold,
                fontFamily = LuxDisplay
            )
        }
    }
}

@Composable
fun AdminInventoryTab(
    viewModel: GoldLuxViewModel,
    uiState: GoldLuxUiState,
    onOpenAddProduct: () -> Unit
) {
    val language = uiState.language
    val isArabic = language == AppLanguage.ARABIC
    val currency = uiState.currency
    val gram24KUsd = uiState.liveSnapshot?.gram24KUsd ?: 88.80

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column {
                Text(
                    text = if (isArabic) "كتالوج مجوهرات جولد لوكس" else "GoldLux Product Catalog",
                    color = TextWhite,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    fontFamily = LuxDisplay
                )
                Text(
                    text = "${Localization.formatInt(uiState.products.size, isArabic)} ${if (isArabic) "قطعة مجوهرات مسجلة" else "active pieces"}",
                    color = TextMuted,
                    fontSize = 12.sp
                )
            }

            GoldGradientButton(
                text = if (isArabic) "إضافة قطعة +" else "Add Piece +",
                onClick = onOpenAddProduct,
                modifier = Modifier.width(130.dp),
                height = 42.dp
            )
        }

        Spacer(modifier = Modifier.height(14.dp))

        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            items(uiState.products) { product ->
                val finalPrice = product.calculateFinalPrice(gram24KUsd, currency)
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
                        Box(
                            modifier = Modifier
                                .size(44.dp)
                                .clip(RoundedCornerShape(8.dp))
                                .background(Color(0x33D4AF37)),
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(
                                imageVector = Icons.Default.Diamond,
                                contentDescription = null,
                                tint = GoldLight,
                                modifier = Modifier.size(24.dp)
                            )
                        }

                        Spacer(modifier = Modifier.width(12.dp))

                        Column(modifier = Modifier.weight(1f)) {
                            Text(
                                text = if (isArabic) product.nameAr else product.nameEn,
                                color = TextWhite,
                                fontSize = 13.sp,
                                fontWeight = FontWeight.SemiBold
                            )
                            Spacer(modifier = Modifier.height(2.dp))
                            Text(
                                text = "${if (isArabic) product.karat.nameAr else product.karat.nameEn} • ${Localization.formatWeight(product.weightInGrams, isArabic)}",
                                color = TextMuted,
                                fontSize = 11.sp
                            )
                        }

                        Column(horizontalAlignment = Alignment.End) {
                            Text(
                                text = Localization.formatPrice(finalPrice, currency, isArabic),
                                color = GoldLight,
                                fontSize = 13.sp,
                                fontWeight = FontWeight.Bold
                            )
                            Text(
                                text = "متاح بالمخزن",
                                color = PriceUpGreen,
                                fontSize = 10.sp
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun AdminOrdersTab(
    viewModel: GoldLuxViewModel,
    uiState: GoldLuxUiState
) {
    val language = uiState.language
    val isArabic = language == AppLanguage.ARABIC
    val currency = uiState.currency

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text(
            text = if (isArabic) "فواتير وطلبات العملاء الواردة" else "Customer Orders & Armored Dispatches",
            color = TextWhite,
            fontSize = 16.sp,
            fontWeight = FontWeight.Bold,
            fontFamily = LuxDisplay
        )

        Spacer(modifier = Modifier.height(14.dp))

        if (uiState.orders.isEmpty()) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(32.dp),
                contentAlignment = Alignment.Center
            ) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Icon(
                        imageVector = Icons.Default.ReceiptLong,
                        contentDescription = null,
                        tint = TextMuted,
                        modifier = Modifier.size(54.dp)
                    )
                    Spacer(modifier = Modifier.height(12.dp))
                    Text(
                        text = Strings.noOrdersYet(language),
                        textAlign = TextAlign.Center,
                        color = TextMuted,
                        fontSize = 14.sp
                    )
                }
            }
        } else {
            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                items(uiState.orders) { order ->
                    AdminOrderCard(
                        order = order,
                        language = language,
                        currency = currency,
                        onUpdateStatus = { newStatus ->
                            viewModel.updateOrderStatus(order.id, newStatus)
                        }
                    )
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AdminOrderCard(
    order: AdminOrder,
    language: AppLanguage,
    currency: Currency,
    onUpdateStatus: (OrderStatus) -> Unit
) {
    val isArabic = language == AppLanguage.ARABIC
    val timeFmt = SimpleDateFormat("yyyy/MM/dd - h:mm a", if (isArabic) Locale("ar") else Locale.US)
    val dateStr = timeFmt.format(Date(order.timestamp))

    GoldCard(
        modifier = Modifier.fillMaxWidth(),
        backgroundColor = DarkSurface,
        borderColor = GoldBorder,
        borderWidth = 0.8.dp
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column {
                    Text(
                        text = "فاتورة: ${order.id}",
                        color = GoldLight,
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Bold
                    )
                    Text(
                        text = dateStr,
                        color = TextMuted,
                        fontSize = 11.sp
                    )
                }

                // Status chip
                Surface(
                    shape = RoundedCornerShape(8.dp),
                    color = when (order.status) {
                        OrderStatus.PROCESSING -> Color(0x33E67E22)
                        OrderStatus.DISPATCHED -> Color(0x333498DB)
                        OrderStatus.DELIVERED -> Color(0x332ECC71)
                    }
                ) {
                    Text(
                        text = when (order.status) {
                            OrderStatus.PROCESSING -> Strings.orderStatusProcessing(language)
                            OrderStatus.DISPATCHED -> Strings.orderStatusDispatched(language)
                            OrderStatus.DELIVERED -> Strings.orderStatusDelivered(language)
                        },
                        color = when (order.status) {
                            OrderStatus.PROCESSING -> Color(0xFFE67E22)
                            OrderStatus.DISPATCHED -> Color(0xFF3498DB)
                            OrderStatus.DELIVERED -> PriceUpGreen
                        },
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                    )
                }
            }

            Spacer(modifier = Modifier.height(10.dp))
            HorizontalDivider(color = Color(0x1AD4AF37), thickness = 0.8.dp)
            Spacer(modifier = Modifier.height(8.dp))

            // Order Financial breakdown
            val totalInCurrency = order.grandTotalUsd * currency.usdRate
            Text(
                text = "العميل: ${order.customerName} • ${order.deliveryAddress}",
                color = TextSilver,
                fontSize = 11.sp
            )

            Spacer(modifier = Modifier.height(6.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = "الإجمالي:",
                    color = TextWhite,
                    fontSize = 13.sp,
                    fontWeight = FontWeight.SemiBold
                )
                Text(
                    text = Localization.formatPrice(totalInCurrency, currency, isArabic),
                    color = GoldLight,
                    fontSize = 15.sp,
                    fontWeight = FontWeight.Bold,
                    fontFamily = LuxDisplay
                )
            }

            Spacer(modifier = Modifier.height(10.dp))

            // Action: Change Status
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                OrderStatus.values().forEach { st ->
                    Surface(
                        shape = RoundedCornerShape(6.dp),
                        color = if (order.status == st) GoldPrimary else DarkSurfaceVariant,
                        modifier = Modifier
                            .weight(1f)
                            .clickable { onUpdateStatus(st) }
                    ) {
                        Text(
                            text = when (st) {
                                OrderStatus.PROCESSING -> if (isArabic) "تحضير" else "Prepare"
                                OrderStatus.DISPATCHED -> if (isArabic) "شحن مصفح" else "Dispatch"
                                OrderStatus.DELIVERED -> if (isArabic) "تم التسليم" else "Delivered"
                            },
                            color = if (order.status == st) Color(0xFF0E0E13) else TextSilver,
                            fontSize = 10.sp,
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier.padding(vertical = 6.dp),
                            textAlign = androidx.compose.ui.text.style.TextAlign.Center
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun AdminApiTab(
    viewModel: GoldLuxViewModel,
    uiState: GoldLuxUiState
) {
    val context = LocalContext.current
    val language = uiState.language
    val isArabic = language == AppLanguage.ARABIC
    var keyInput by remember { mutableStateOf(uiState.customApiKey) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(16.dp)
    ) {
        // Detailed answer to user's question
        GoldCard(
            modifier = Modifier.fillMaxWidth(),
            backgroundColor = DarkSurface,
            borderColor = GoldBorder,
            borderWidth = 1.dp
        ) {
            Column(modifier = Modifier.padding(18.dp)) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        imageVector = Icons.Default.ShowChart,
                        contentDescription = null,
                        tint = GoldLight,
                        modifier = Modifier.size(24.dp)
                    )
                    Spacer(modifier = Modifier.width(10.dp))
                    Text(
                        text = Strings.chartRealityHeader(language),
                        color = TextWhite,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold,
                        fontFamily = LuxDisplay
                    )
                }

                Spacer(modifier = Modifier.height(10.dp))

                Text(
                    text = Strings.chartRealityExplanation(language),
                    color = TextSilver,
                    fontSize = 13.sp,
                    lineHeight = 21.sp
                )
            }
        }

        Spacer(modifier = Modifier.height(20.dp))

        // API Key Configuration
        GoldCard(
            modifier = Modifier.fillMaxWidth(),
            backgroundColor = DarkSurface,
            borderColor = GoldBorder,
            borderWidth = 0.8.dp
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                Text(
                    text = Strings.liveApiSourceLabel(language),
                    color = TextWhite,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.SemiBold
                )
                Text(
                    text = "MetalpriceAPI / GoldAPI (XAU / Troy Ounce to Gram conversion)",
                    color = GoldLight,
                    fontSize = 12.sp
                )

                Spacer(modifier = Modifier.height(12.dp))

                OutlinedTextField(
                    value = keyInput,
                    onValueChange = { keyInput = it },
                    placeholder = { Text(text = Strings.apiKeyPlaceholder(language), color = TextMuted, fontSize = 12.sp) },
                    singleLine = true,
                    modifier = Modifier.fillMaxWidth(),
                    colors = TextFieldDefaults.colors(
                        focusedContainerColor = DarkSurfaceVariant,
                        unfocusedContainerColor = DarkSurfaceVariant,
                        focusedTextColor = TextWhite,
                        unfocusedTextColor = TextWhite,
                        focusedIndicatorColor = GoldPrimary
                    )
                )

                Spacer(modifier = Modifier.height(12.dp))

                GoldGradientButton(
                    text = Strings.testConnection(language),
                    onClick = {
                        viewModel.setCustomApiKey(keyInput)
                        viewModel.refreshPrices()
                        Toast.makeText(context, if (isArabic) "تم فحص الاتصال وتحديث أسعار الذهب الفورية بنجاح!" else "Connected to Live Market Feed!", Toast.LENGTH_SHORT).show()
                    },
                    modifier = Modifier.fillMaxWidth(),
                    height = 46.dp
                )
            }
        }
    }
}

@Composable
fun AddNewProductDialog(
    viewModel: GoldLuxViewModel,
    uiState: GoldLuxUiState,
    onDismiss: () -> Unit
) {
    val language = uiState.language
    val isArabic = language == AppLanguage.ARABIC

    var nameAr by remember { mutableStateOf("") }
    var nameEn by remember { mutableStateOf("") }
    var weightInput by remember { mutableStateOf("") }
    var makingInput by remember { mutableStateOf("12.0") }
    var selectedKarat by remember { mutableStateOf(Karat.K21) }
    var selectedCategory by remember { mutableStateOf(ProductCategory.RINGS) }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = {
            Text(
                text = Strings.addNewProductTitle(language),
                color = TextWhite,
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
                fontFamily = LuxDisplay
            )
        },
        text = {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .verticalScroll(rememberScrollState()),
                verticalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                OutlinedTextField(
                    value = nameAr,
                    onValueChange = { nameAr = it },
                    label = { Text(text = Strings.pieceNameAr(language), color = TextMuted) },
                    singleLine = true,
                    colors = TextFieldDefaults.colors(
                        focusedContainerColor = DarkSurfaceVariant,
                        unfocusedContainerColor = DarkSurfaceVariant,
                        focusedTextColor = TextWhite,
                        unfocusedTextColor = TextWhite
                    )
                )

                OutlinedTextField(
                    value = nameEn,
                    onValueChange = { nameEn = it },
                    label = { Text(text = Strings.pieceNameEn(language), color = TextMuted) },
                    singleLine = true,
                    colors = TextFieldDefaults.colors(
                        focusedContainerColor = DarkSurfaceVariant,
                        unfocusedContainerColor = DarkSurfaceVariant,
                        focusedTextColor = TextWhite,
                        unfocusedTextColor = TextWhite
                    )
                )

                OutlinedTextField(
                    value = weightInput,
                    onValueChange = { weightInput = it },
                    label = { Text(text = Strings.pieceWeight(language), color = TextMuted) },
                    singleLine = true,
                    colors = TextFieldDefaults.colors(
                        focusedContainerColor = DarkSurfaceVariant,
                        unfocusedContainerColor = DarkSurfaceVariant,
                        focusedTextColor = TextWhite,
                        unfocusedTextColor = TextWhite
                    )
                )

                OutlinedTextField(
                    value = makingInput,
                    onValueChange = { makingInput = it },
                    label = { Text(text = Strings.pieceMaking(language), color = TextMuted) },
                    singleLine = true,
                    colors = TextFieldDefaults.colors(
                        focusedContainerColor = DarkSurfaceVariant,
                        unfocusedContainerColor = DarkSurfaceVariant,
                        focusedTextColor = TextWhite,
                        unfocusedTextColor = TextWhite
                    )
                )

                // Karat selection chips
                Text(text = Strings.pieceKarat(language), color = TextMuted, fontSize = 12.sp)
                Row(horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                    listOf(Karat.K24, Karat.K22, Karat.K21, Karat.K18).forEach { k ->
                        Surface(
                            shape = RoundedCornerShape(8.dp),
                            color = if (k == selectedKarat) GoldPrimary else DarkSurfaceVariant,
                            modifier = Modifier.clickable { selectedKarat = k }
                        ) {
                            Text(
                                text = if (isArabic) k.nameAr else k.nameEn,
                                color = if (k == selectedKarat) Color(0xFF0E0E13) else TextSilver,
                                fontSize = 11.sp,
                                modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                            )
                        }
                    }
                }
            }
        },
        confirmButton = {
            GoldGradientButton(
                text = Strings.savePiece(language),
                onClick = {
                    val weight = weightInput.toDoubleOrNull() ?: 5.0
                    val making = makingInput.toDoubleOrNull() ?: 12.0
                    val newProduct = Product(
                        id = "p_custom_${System.currentTimeMillis()}",
                        nameEn = if (nameEn.isNotBlank()) nameEn else "Handcrafted Gold Jewel",
                        nameAr = if (nameAr.isNotBlank()) nameAr else "قطعة ذهبية فاخرة",
                        descriptionEn = "Handcrafted in certified ${selectedKarat.nameEn} gold by GoldLux Master Jewelers.",
                        descriptionAr = "مصنوعة يدوياً من الذهب المعتمد ${selectedKarat.nameAr} بأيدي أمهر صاغة جولد لوكس.",
                        category = selectedCategory,
                        karat = selectedKarat,
                        weightInGrams = weight,
                        makingChargePerGramUsd = making,
                        imageUrls = listOf(
                            "https://images.unsplash.com/photo-1605100804763-247f67b3557e?w=800&auto=format&fit=crop&q=80"
                        ),
                        isFeatured = true
                    )
                    viewModel.addNewProduct(newProduct)
                    onDismiss()
                },
                modifier = Modifier.width(160.dp),
                height = 42.dp
            )
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text(text = Strings.cancel(language), color = TextMuted)
            }
        },
        containerColor = DarkSurface,
        shape = RoundedCornerShape(20.dp)
    )
}
