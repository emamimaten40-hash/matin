package com.example.goldlux.ui.screens

import com.example.ui.theme.LuxDisplay
import com.example.goldlux.ui.components.SectionHeader
import android.widget.Toast
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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Logout
import androidx.compose.material.icons.filled.AdminPanelSettings
import androidx.compose.material.icons.filled.Chat
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Diamond
import androidx.compose.material.icons.filled.Inventory2
import androidx.compose.material.icons.filled.Language
import androidx.compose.material.icons.filled.LocalShipping
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.NotificationsActive
import androidx.compose.material.icons.filled.Paid
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material.icons.filled.ReceiptLong
import androidx.compose.material.icons.filled.ShowChart
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.filled.Verified
import androidx.compose.material.icons.filled.WorkspacePremium
import com.example.goldlux.localization.Localization
import com.example.goldlux.model.AdminOrder
import com.example.goldlux.model.OrderStatus
import com.example.goldlux.util.ContactUtils
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.goldlux.localization.Strings
import com.example.goldlux.model.AppLanguage
import com.example.goldlux.model.Currency
import com.example.goldlux.ui.components.AdminAuthDialog
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
import com.example.ui.theme.TextMuted
import com.example.ui.theme.TextSilver
import com.example.ui.theme.TextWhite

@Composable
fun ProfileScreen(
    viewModel: GoldLuxViewModel,
    uiState: GoldLuxUiState,
    onReplayLogoMotion: (() -> Unit)? = null,
    onNavigateToShop: (() -> Unit)? = null,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    val language = uiState.language
    val isArabic = language == AppLanguage.ARABIC
    val currency = uiState.currency
    val currentUser = uiState.currentUser

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(DarkBackground)
            .verticalScroll(rememberScrollState())
            .padding(horizontal = 20.dp)
            .padding(top = 16.dp, bottom = 20.dp)
    ) {
        // Top Header: Title + Sign Out Button
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column {
                Text(
                    text = Strings.profileTitle(language),
                    color = TextWhite,
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold,
                    fontFamily = LuxDisplay
                )
                if (currentUser != null) {
                    Text(
                        text = if (isArabic) "مرحباً بك، عميلنا المميز" else "Welcome, VIP Patron",
                        color = GoldLight,
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Medium
                    )
                }
            }

            // Sign Out Button (خروج از حساب)
            OutlinedButton(
                onClick = {
                    viewModel.logoutUser()
                    Toast.makeText(
                        context,
                        if (isArabic) "تم تسجيل الخروج بنجاح" else "Signed out",
                        Toast.LENGTH_SHORT
                    ).show()
                },
                shape = RoundedCornerShape(10.dp),
                border = androidx.compose.foundation.BorderStroke(0.8.dp, GoldBorder.copy(alpha = 0.6f)),
                modifier = Modifier.height(36.dp)
            ) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.Logout,
                    contentDescription = "Logout",
                    tint = TextSilver,
                    modifier = Modifier.size(15.dp)
                )
                Spacer(modifier = Modifier.width(4.dp))
                Text(
                    text = if (isArabic) "خروج" else "Sign Out",
                    color = TextSilver,
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Medium
                )
            }
        }

        Spacer(modifier = Modifier.height(18.dp))

        // VIP Member Tier Holographic Card
        GoldCard(
            modifier = Modifier.fillMaxWidth(),
            backgroundColor = DarkSurface,
            borderColor = GoldPrimary,
            borderWidth = 1.2.dp
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(
                        Brush.linearGradient(
                            listOf(
                                Color(0xFF2C2619),
                                Color(0xFF1D1B28),
                                Color(0xFF13121C)
                            )
                        )
                    )
                    .padding(20.dp)
            ) {
                Column {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Box(
                                modifier = Modifier
                                    .size(44.dp)
                                    .clip(CircleShape)
                                    .background(Color(0x33D4AF37))
                                    .border(1.dp, GoldPrimary, CircleShape),
                                contentAlignment = Alignment.Center
                            ) {
                                Icon(
                                    imageVector = Icons.Default.WorkspacePremium,
                                    contentDescription = null,
                                    tint = GoldLight,
                                    modifier = Modifier.size(24.dp)
                                )
                            }
                            Spacer(modifier = Modifier.width(12.dp))
                            Column {
                                Text(
                                    text = Strings.vipTierTitle(language),
                                    color = GoldLight,
                                    fontSize = 16.sp,
                                    fontWeight = FontWeight.Bold
                                )
                                val phoneDisplay = currentUser?.phone?.takeIf { it.isNotBlank() } ?: "+965 9943 4920"
                                Text(
                                    text = if (isArabic) "رقم الحساب: $phoneDisplay" else "Account: $phoneDisplay",
                                    color = TextWhite,
                                    fontSize = 12.sp,
                                    fontWeight = FontWeight.SemiBold
                                )
                                Text(
                                    text = Strings.vipMemberId(language),
                                    color = TextMuted,
                                    fontSize = 11.sp
                                )
                            }
                        }

                        // Gold Crown Icon
                        Icon(
                            imageVector = Icons.Default.Star,
                            contentDescription = null,
                            tint = GoldPrimary,
                            modifier = Modifier.size(24.dp)
                        )
                    }

                    Spacer(modifier = Modifier.height(14.dp))
                    HorizontalDivider(color = Color(0x33D4AF37), thickness = 0.8.dp)
                    Spacer(modifier = Modifier.height(12.dp))

                    Text(
                        text = Strings.vipBenefit(language),
                        color = TextWhite,
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Medium
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        // Customer Orders & Purchases Section 
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    imageVector = Icons.Default.ReceiptLong,
                    contentDescription = null,
                    tint = GoldLight,
                    modifier = Modifier.size(20.dp)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = if (isArabic) "سجل المشتريات والطلبات" else "Orders & Purchases",
                    color = TextWhite,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    fontFamily = LuxDisplay
                )
            }

            Surface(
                shape = RoundedCornerShape(8.dp),
                color = Color(0x33D4AF37),
                border = androidx.compose.foundation.BorderStroke(0.6.dp, GoldBorder)
            ) {
                Text(
                    text = "${uiState.orders.size} ${if (isArabic) "طلب" else "orders"}",
                    color = GoldLight,
                    fontSize = 11.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(horizontal = 8.dp, vertical = 2.dp)
                )
            }
        }

        Spacer(modifier = Modifier.height(12.dp))

        if (uiState.orders.isEmpty()) {
            GoldCard(
                modifier = Modifier.fillMaxWidth(),
                backgroundColor = DarkSurface,
                borderColor = GoldBorder,
                borderWidth = 0.8.dp
            ) {
                Column(
                    modifier = Modifier.padding(20.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Icon(
                        imageVector = Icons.Default.Inventory2,
                        contentDescription = null,
                        tint = TextMuted,
                        modifier = Modifier.size(36.dp)
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = if (isArabic) "لا توجد طلبات سابقة مسجلة" else "No order history yet",
                        textAlign = TextAlign.Center,
                        color = TextSilver,
                        fontSize = 13.sp,
                        fontWeight = FontWeight.Medium
                    )
                    Spacer(modifier = Modifier.height(12.dp))
                    GoldGradientButton(
                        text = if (isArabic) "تصفح متجر المجوهرات" else "Explore Boutique Shop",
                        onClick = { onNavigateToShop?.invoke() },
                        height = 38.dp,
                        modifier = Modifier.width(200.dp)
                    )
                }
            }
        } else {
            Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                uiState.orders.forEach { order ->
                    CustomerOrderCard(
                        order = order,
                        currency = currency,
                        isArabic = isArabic,
                        onTrackOnWhatsApp = {
                            ContactUtils.openWhatsAppOrderInquiry(
                                context = context,
                                orderId = order.id,
                                isArabic = isArabic
                            )
                        }
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        // App Preferences Section
        SectionHeader(title = Strings.preferencesHeader(language))

        Spacer(modifier = Modifier.height(12.dp))

        GoldCard(
            modifier = Modifier.fillMaxWidth(),
            backgroundColor = DarkSurface,
            borderColor = GoldBorder,
            borderWidth = 0.8.dp
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                // Language Switcher
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(
                            imageVector = Icons.Default.Language,
                            contentDescription = null,
                            tint = GoldLight,
                            modifier = Modifier.size(22.dp)
                        )
                        Spacer(modifier = Modifier.width(12.dp))
                        Column {
                            Text(
                                text = Strings.languageSetting(language),
                                color = TextWhite,
                                fontSize = 14.sp,
                                fontWeight = FontWeight.SemiBold
                            )
                            Text(
                                text = if (isArabic) "العربية (افتراضي)" else "English",
                                color = TextMuted,
                                fontSize = 11.sp
                            )
                        }
                    }

                    // Language Toggle Buttons (Arabic / English)
                    Row(
                        modifier = Modifier
                            .clip(RoundedCornerShape(12.dp))
                            .background(DarkSurfaceVariant)
                            .border(0.6.dp, GoldBorder, RoundedCornerShape(12.dp))
                            .padding(4.dp)
                    ) {
                        // Arabic option
                        val isArSelected = language == AppLanguage.ARABIC
                        Box(
                            modifier = Modifier
                                .clip(RoundedCornerShape(8.dp))
                                .background(if (isArSelected) GoldPrimary else Color.Transparent)
                                .clickable { viewModel.setLanguage(AppLanguage.ARABIC) }
                                .padding(horizontal = 10.dp, vertical = 6.dp)
                        ) {
                            Text(
                                text = "العربية",
                                color = if (isArSelected) Color(0xFF0E0E13) else TextSilver,
                                fontSize = 12.sp,
                                fontWeight = if (isArSelected) FontWeight.Bold else FontWeight.Medium
                            )
                        }

                        // English option
                        val isEnSelected = language == AppLanguage.ENGLISH
                        Box(
                            modifier = Modifier
                                .clip(RoundedCornerShape(8.dp))
                                .background(if (isEnSelected) GoldPrimary else Color.Transparent)
                                .clickable { viewModel.setLanguage(AppLanguage.ENGLISH) }
                                .padding(horizontal = 10.dp, vertical = 6.dp)
                        ) {
                            Text(
                                text = "English",
                                color = if (isEnSelected) Color(0xFF0E0E13) else TextSilver,
                                fontSize = 12.sp,
                                fontWeight = if (isEnSelected) FontWeight.Bold else FontWeight.Medium
                            )
                        }
                    }
                }

                Spacer(modifier = Modifier.height(14.dp))
                HorizontalDivider(color = Color(0x1AD4AF37), thickness = 0.8.dp)
                Spacer(modifier = Modifier.height(14.dp))

                // Currency Selector (KWD, USD, AED) - Fully Responsive in English & Arabic
                Column(modifier = Modifier.fillMaxWidth()) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier.weight(1f)
                        ) {
                            Icon(
                                imageVector = Icons.Default.Paid,
                                contentDescription = null,
                                tint = GoldLight,
                                modifier = Modifier.size(22.dp)
                            )
                            Spacer(modifier = Modifier.width(12.dp))
                            Column {
                                Text(
                                    text = Strings.currencySetting(language),
                                    color = TextWhite,
                                    fontSize = 14.sp,
                                    fontWeight = FontWeight.SemiBold
                                )
                                Text(
                                    text = "${currency.getName(isArabic)} (${currency.code})",
                                    color = TextMuted,
                                    fontSize = 11.sp
                                )
                            }
                        }

                        // Active currency badge
                        Surface(
                            shape = RoundedCornerShape(8.dp),
                            color = Color(0x33D4AF37),
                            border = androidx.compose.foundation.BorderStroke(0.6.dp, GoldPrimary)
                        ) {
                            Text(
                                text = currency.getSymbol(isArabic),
                                color = GoldLight,
                                fontSize = 12.sp,
                                fontWeight = FontWeight.Bold,
                                modifier = Modifier.padding(horizontal = 10.dp, vertical = 4.dp)
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(12.dp))

                    // 3 Equal-Width Responsive Currency Buttons (KWD, USD, AED)
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        val availableCurrencies = listOf(Currency.KWD, Currency.USD, Currency.AED)
                        availableCurrencies.forEach { cur ->
                            val isCurSelected = cur == currency
                            val subLabel = when (cur) {
                                Currency.KWD -> if (isArabic) "د.ك" else "KD"
                                Currency.USD -> "$"
                                Currency.AED -> if (isArabic) "د.إ" else "AED"
                            }

                            Surface(
                                shape = RoundedCornerShape(12.dp),
                                color = if (isCurSelected) GoldPrimary else DarkSurfaceVariant,
                                border = if (isCurSelected) null else androidx.compose.foundation.BorderStroke(0.8.dp, GoldBorder),
                                modifier = Modifier
                                    .weight(1f)
                                    .height(52.dp)
                                    .clip(RoundedCornerShape(12.dp))
                                    .clickable { viewModel.setCurrency(cur) }
                            ) {
                                Column(
                                    modifier = Modifier
                                        .fillMaxSize()
                                        .padding(horizontal = 4.dp, vertical = 4.dp),
                                    verticalArrangement = Arrangement.Center,
                                    horizontalAlignment = Alignment.CenterHorizontally
                                ) {
                                    Text(
                                        text = cur.code,
                                        color = if (isCurSelected) Color(0xFF0E0E13) else TextWhite,
                                        fontSize = 12.sp,
                                        fontWeight = FontWeight.Bold,
                                        maxLines = 1,
                                        overflow = TextOverflow.Ellipsis
                                    )
                                    Spacer(modifier = Modifier.height(2.dp))
                                    Text(
                                        text = subLabel,
                                        color = if (isCurSelected) Color(0xFF2A2210) else GoldLight,
                                        fontSize = 11.sp,
                                        fontWeight = if (isCurSelected) FontWeight.Bold else FontWeight.Medium,
                                        maxLines = 1,
                                        overflow = TextOverflow.Ellipsis
                                    )
                                }
                            }
                        }
                    }
                }

                Spacer(modifier = Modifier.height(14.dp))
                HorizontalDivider(color = Color(0x1AD4AF37), thickness = 0.8.dp)
                Spacer(modifier = Modifier.height(14.dp))

                // Gold Price Alerts Toggle
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.weight(1f)
                    ) {
                        Icon(
                            imageVector = Icons.Default.NotificationsActive,
                            contentDescription = null,
                            tint = GoldLight,
                            modifier = Modifier.size(22.dp)
                        )
                        Spacer(modifier = Modifier.width(12.dp))
                        Column {
                            Text(
                                text = Strings.priceAlerts(language),
                                color = TextWhite,
                                fontSize = 14.sp,
                                fontWeight = FontWeight.SemiBold
                            )
                            Text(
                                text = Strings.priceAlertsDesc(language),
                                color = TextMuted,
                                fontSize = 11.sp
                            )
                        }
                    }

                    Switch(
                        checked = uiState.priceAlertsEnabled,
                        onCheckedChange = { viewModel.togglePriceAlerts() },
                        colors = SwitchDefaults.colors(
                            checkedThumbColor = Color(0xFF0E0E13),
                            checkedTrackColor = GoldPrimary,
                            uncheckedThumbColor = TextMuted,
                            uncheckedTrackColor = DarkSurfaceVariant
                        )
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        // Admin Management Portal Card (Visible ONLY for Authenticated Admin, hidden from regular users)
        if (uiState.isAuthenticatedAdmin) {
            GoldCard(
                modifier = Modifier.fillMaxWidth(),
                backgroundColor = DarkSurface,
                borderColor = GoldPrimary,
                borderWidth = 1.2.dp
            ) {
                Column(modifier = Modifier.padding(18.dp)) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Box(
                                modifier = Modifier
                                    .size(40.dp)
                                    .clip(RoundedCornerShape(10.dp))
                                    .background(Color(0x33D4AF37)),
                                contentAlignment = Alignment.Center
                            ) {
                                Icon(
                                    imageVector = Icons.Default.AdminPanelSettings,
                                    contentDescription = null,
                                    tint = GoldLight,
                                    modifier = Modifier.size(24.dp)
                                )
                            }
                            Spacer(modifier = Modifier.width(12.dp))
                            Column {
                                Text(
                                    text = Strings.adminModeActive(language),
                                    color = GoldPrimary,
                                    fontSize = 15.sp,
                                    fontWeight = FontWeight.Bold,
                                    fontFamily = LuxDisplay
                                )
                                Text(
                                    text = Strings.adminPortalDesc(language),
                                    color = TextMuted,
                                    fontSize = 11.sp
                                )
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(14.dp))

                    GoldGradientButton(
                        text = Strings.openAdminPortal(language),
                        onClick = { viewModel.toggleAdminPortal(true) },
                        modifier = Modifier.fillMaxWidth(),
                        height = 46.dp
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    OutlinedButton(
                        onClick = {
                            viewModel.logoutAdmin()
                            Toast.makeText(
                                context,
                                if (isArabic) "تم قفل لوحة الإدارة بنجاح" else "Admin locked successfully",
                                Toast.LENGTH_SHORT
                            ).show()
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(42.dp),
                        shape = RoundedCornerShape(12.dp),
                        border = androidx.compose.foundation.BorderStroke(0.8.dp, GoldBorder.copy(alpha = 0.6f))
                    ) {
                        Icon(
                            imageVector = Icons.Default.Lock,
                            contentDescription = null,
                            tint = TextSilver,
                            modifier = Modifier.size(16.dp)
                        )
                        Spacer(modifier = Modifier.width(6.dp))
                        Text(
                            text = Strings.adminLogout(language),
                            color = TextSilver,
                            fontSize = 12.sp
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(24.dp))
        }

        // Market Data & Chart Reality Card 
        GoldCard(
            modifier = Modifier.fillMaxWidth(),
            backgroundColor = DarkSurface,
            borderColor = GoldBorder,
            borderWidth = 0.8.dp
        ) {
            Column(modifier = Modifier.padding(18.dp)) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        imageVector = Icons.Default.ShowChart,
                        contentDescription = null,
                        tint = GoldLight,
                        modifier = Modifier.size(22.dp)
                    )
                    Spacer(modifier = Modifier.width(10.dp))
                    Text(
                        text = Strings.chartRealityHeader(language),
                        color = TextWhite,
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Bold
                    )
                }

                Spacer(modifier = Modifier.height(8.dp))

                Text(
                    text = Strings.chartRealityExplanation(language),
                    color = TextSilver,
                    fontSize = 12.sp,
                    lineHeight = 18.sp
                )
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        // Boutiques & Concierge
        SectionHeader(title = Strings.boutiquesHeader(language))

        Spacer(modifier = Modifier.height(12.dp))

        GoldCard(
            modifier = Modifier.fillMaxWidth(),
            backgroundColor = DarkSurface,
            borderColor = GoldBorder,
            borderWidth = 0.8.dp
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        imageVector = Icons.Default.LocationOn,
                        contentDescription = null,
                        tint = GoldLight,
                        modifier = Modifier.size(22.dp)
                    )
                    Spacer(modifier = Modifier.width(12.dp))
                    Text(
                        text = Strings.boutiquesList(language),
                        color = TextSilver,
                        fontSize = 12.sp,
                        lineHeight = 18.sp
                    )
                }

                Spacer(modifier = Modifier.height(14.dp))
                HorizontalDivider(color = Color(0x1AD4AF37), thickness = 0.8.dp)
                Spacer(modifier = Modifier.height(14.dp))

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable { ContactUtils.callBoutique(context) },
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        imageVector = Icons.Default.Phone,
                        contentDescription = null,
                        tint = GoldLight,
                        modifier = Modifier.size(22.dp)
                    )
                    Spacer(modifier = Modifier.width(12.dp))
                    Column {
                        Text(
                            text = Strings.conciergeSupport(language),
                            color = TextWhite,
                            fontSize = 13.sp,
                            fontWeight = FontWeight.SemiBold
                        )
                        Text(
                            text = Strings.callConcierge(language),
                            color = GoldLight,
                            fontSize = 11.sp,
                            fontWeight = FontWeight.Medium
                        )
                    }
                }
            }
        }

        if (onReplayLogoMotion != null) {
            Spacer(modifier = Modifier.height(16.dp))
            OutlinedButton(
                onClick = onReplayLogoMotion,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(44.dp),
                shape = RoundedCornerShape(12.dp),
                border = androidx.compose.foundation.BorderStroke(1.dp, GoldBorder)
            ) {
                Icon(
                    imageVector = Icons.Default.Diamond,
                    contentDescription = null,
                    tint = GoldLight,
                    modifier = Modifier.size(18.dp)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = if (isArabic) "معاينة هوية وشعار صانع الأحلام (لوجو موشن)" else "View Dream Maker Logo Motion",
                    color = GoldLight,
                    fontSize = 12.sp,
                    fontWeight = FontWeight.SemiBold
                )
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        // Hallmark Guarantee Note & Version
        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {
                Icon(
                    imageVector = Icons.Default.Verified,
                    contentDescription = null,
                    tint = GoldPrimary,
                    modifier = Modifier.size(16.dp)
                )
                Spacer(modifier = Modifier.width(6.dp))
                Text(
                    text = Strings.purityGuarantee(language),
                    color = TextSilver,
                    fontSize = 11.sp,
                    fontWeight = FontWeight.Medium
                )
            }

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = Strings.appVersion(language),
                textAlign = TextAlign.Center,
                color = TextMuted,
                fontSize = 11.sp
            )

            // Discrete Staff / Admin Access for the Admin only
            if (!uiState.isAuthenticatedAdmin) {
                Spacer(modifier = Modifier.height(14.dp))
                Row(
                    modifier = Modifier
                        .clip(RoundedCornerShape(8.dp))
                        .clickable { viewModel.requestAdminAuth() }
                        .padding(horizontal = 14.dp, vertical = 8.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        imageVector = Icons.Default.Lock,
                        contentDescription = "Staff Access",
                        tint = TextMuted.copy(alpha = 0.5f),
                        modifier = Modifier.size(13.dp)
                    )
                    Spacer(modifier = Modifier.width(6.dp))
                    Text(
                        text = Strings.staffAccess(language),
                        color = TextMuted.copy(alpha = 0.6f),
                        fontSize = 11.sp
                    )
                }
            }
        }
    }

    if (uiState.showAdminAuthDialog) {
        AdminAuthDialog(
            language = language,
            errorMessage = uiState.adminAuthError,
            onDismiss = { viewModel.dismissAdminAuth() },
            onVerifyPin = { pin ->
                val success = viewModel.submitAdminPin(pin)
                if (success) {
                    Toast.makeText(
                        context,
                        if (isArabic) "مرحباً بك في لوحة الإدارة" else "Welcome to Admin Management",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        )
    }
}

@Composable
fun CustomerOrderCard(
    order: AdminOrder,
    currency: Currency,
    isArabic: Boolean,
    onTrackOnWhatsApp: () -> Unit
) {
    val totalConverted = order.grandTotalUsd * currency.usdRate
    val timeFormat = SimpleDateFormat("yyyy/MM/dd", if (isArabic) Locale("ar") else Locale.US)
    val formattedDate = timeFormat.format(Date(order.timestamp))

    GoldCard(
        modifier = Modifier.fillMaxWidth(),
        backgroundColor = DarkSurface,
        borderColor = GoldBorder,
        borderWidth = 0.8.dp
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            // Header: Order ID + Status
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Box(
                        modifier = Modifier
                            .size(32.dp)
                            .clip(CircleShape)
                            .background(Color(0x22D4AF37)),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = Icons.Default.ReceiptLong,
                            contentDescription = null,
                            tint = GoldLight,
                            modifier = Modifier.size(16.dp)
                        )
                    }
                    Spacer(modifier = Modifier.width(8.dp))
                    Column {
                        Text(
                            text = "#${order.id}",
                            color = TextWhite,
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Bold,
                            fontFamily = LuxDisplay
                        )
                        Text(
                            text = formattedDate,
                            color = TextMuted,
                            fontSize = 11.sp
                        )
                    }
                }

                // Status Badge
                val isDelivered = order.status == OrderStatus.DELIVERED
                Surface(
                    shape = RoundedCornerShape(10.dp),
                    color = if (isDelivered) Color(0x222ECC71) else Color(0x22F39C12),
                    border = androidx.compose.foundation.BorderStroke(
                        0.6.dp,
                        if (isDelivered) Color(0xFF2ECC71) else Color(0xFFF39C12)
                    )
                ) {
                    Row(
                        modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            imageVector = if (isDelivered) Icons.Default.Check else Icons.Default.LocalShipping,
                            contentDescription = null,
                            tint = if (isDelivered) Color(0xFF2ECC71) else Color(0xFFF39C12),
                            modifier = Modifier.size(12.dp)
                        )
                        Spacer(modifier = Modifier.width(4.dp))
                        Text(
                            text = when (order.status) {
                                OrderStatus.PROCESSING -> if (isArabic) "قيد التجهيز والصياغة" else "Crafting & Processing"
                                OrderStatus.DISPATCHED -> if (isArabic) "تم الشحن المؤمن" else "Dispatched"
                                OrderStatus.DELIVERED -> if (isArabic) "تم الاستلام بنجاح" else "Delivered"
                            },
                            color = if (isDelivered) Color(0xFF2ECC71) else Color(0xFFF39C12),
                            fontSize = 11.sp,
                            fontWeight = FontWeight.SemiBold
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(12.dp))
            HorizontalDivider(color = Color(0x1AD4AF37), thickness = 0.8.dp)
            Spacer(modifier = Modifier.height(10.dp))

            // Items in this order
            order.items.forEach { cartItem ->
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 4.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column(modifier = Modifier.weight(1f)) {
                        Text(
                            text = if (isArabic) cartItem.product.nameAr else cartItem.product.nameEn,
                            color = TextWhite,
                            fontSize = 13.sp,
                            fontWeight = FontWeight.Medium,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis
                        )
                        Text(
                            text = "${cartItem.product.karat.nameAr} • ${cartItem.product.weightInGrams} غرام",
                            color = TextMuted,
                            fontSize = 11.sp
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(8.dp))

            // Total Price + Track on WhatsApp
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column {
                    Text(
                        text = if (isArabic) "الإجمالي المدفوع:" else "Total Paid:",
                        color = TextMuted,
                        fontSize = 11.sp
                    )
                    Text(
                        text = Localization.formatPrice(totalConverted, currency, isArabic),
                        color = GoldLight,
                        fontSize = 15.sp,
                        fontWeight = FontWeight.Bold,
                        fontFamily = LuxDisplay
                    )
                }

                // WhatsApp Tracking button
                Surface(
                    shape = RoundedCornerShape(10.dp),
                    color = Color(0x22128C7E),
                    border = androidx.compose.foundation.BorderStroke(0.8.dp, Color(0xFF25D366).copy(alpha = 0.7f)),
                    modifier = Modifier.clickable(onClick = onTrackOnWhatsApp)
                ) {
                    Row(
                        modifier = Modifier.padding(horizontal = 10.dp, vertical = 6.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            imageVector = Icons.Default.Chat,
                            contentDescription = "WhatsApp",
                            tint = Color(0xFF25D366),
                            modifier = Modifier.size(14.dp)
                        )
                        Spacer(modifier = Modifier.width(6.dp))
                        Text(
                            text = if (isArabic) "متابعة في واتساب" else "Track WhatsApp",
                            color = Color(0xFFE8F5E9),
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }
            }
        }
    }
}
