package com.example.goldlux.ui.screens

import com.example.ui.theme.LuxDisplay
import com.example.goldlux.ui.components.SectionHeader
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
import androidx.compose.material.icons.filled.Call
import androidx.compose.material.icons.filled.Chat
import androidx.compose.material.icons.filled.Diamond
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.PlayCircleOutline
import androidx.compose.material.icons.filled.Security
import androidx.compose.material.icons.filled.SupportAgent
import androidx.compose.material.icons.filled.Verified
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.goldlux.localization.Strings
import com.example.goldlux.model.AppLanguage
import com.example.goldlux.ui.components.GoldCard
import com.example.goldlux.ui.viewmodel.GoldLuxUiState
import com.example.goldlux.ui.viewmodel.GoldLuxViewModel
import com.example.goldlux.util.ContactUtils
import com.example.ui.theme.DarkBackground
import com.example.ui.theme.DarkSurface
import com.example.ui.theme.DarkSurfaceVariant
import com.example.ui.theme.GoldBorder
import com.example.ui.theme.GoldLight
import com.example.ui.theme.GoldPrimary
import com.example.ui.theme.PriceUpGreen
import com.example.ui.theme.TextMuted
import com.example.ui.theme.TextSilver
import com.example.ui.theme.TextWhite

@Composable
fun WhatsAppHubScreen(
    viewModel: GoldLuxViewModel,
    uiState: GoldLuxUiState,
    onReplayLogoMotion: () -> Unit,
    onNavigateToShop: () -> Unit,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    val language = uiState.language
    val isArabic = language == AppLanguage.ARABIC

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(DarkBackground)
            .verticalScroll(rememberScrollState())
            .padding(bottom = 16.dp)
    ) {
        // Top Header
        Column(modifier = Modifier.padding(horizontal = 20.dp, vertical = 16.dp)) {
            Text(
                text = if (isArabic) "مركز الطلب والتواصل الفوري" else "VIP Concierge & Orders",
                color = GoldLight,
                fontSize = 13.sp,
                fontWeight = FontWeight.Medium
            )
            Spacer(modifier = Modifier.height(2.dp))
            Text(
                text = if (isArabic) "مجوهرات صانع الأحلام" else "Jewelry Dream Maker",
                color = TextWhite,
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                fontFamily = LuxDisplay
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = if (isArabic) "شراء مباشر وتواصل VIP عبر واتساب على مدار الساعة" else "Direct WhatsApp order line & 24/7 client care",
                color = TextMuted,
                fontSize = 12.sp
            )
        }

        // WhatsApp Main Action Hero Card
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp)
                .clip(RoundedCornerShape(20.dp))
                .background(
                    Brush.verticalGradient(
                        listOf(
                            Color(0xFF1E2822),
                            Color(0xFF121A16),
                            DarkSurface
                        )
                    )
                )
                .border(1.2.dp, Color(0xFF25D366).copy(alpha = 0.6f), RoundedCornerShape(20.dp))
                .padding(20.dp)
        ) {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Box(
                    modifier = Modifier
                        .size(64.dp)
                        .clip(CircleShape)
                        .background(
                            Brush.radialGradient(
                                listOf(Color(0xFF25D366), Color(0xFF128C7E))
                            )
                        ),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = Icons.Default.Chat,
                        contentDescription = "WhatsApp",
                        tint = Color.White,
                        modifier = Modifier.size(36.dp)
                    )
                }

                Spacer(modifier = Modifier.height(14.dp))

                Text(
                    text = if (isArabic) "خط الشراء المباشر عبر واتساب" else "Direct WhatsApp Purchase Line",
                    textAlign = TextAlign.Center,
                    color = TextWhite,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    fontFamily = LuxDisplay
                )

                Spacer(modifier = Modifier.height(6.dp))

                Text(
                    text = Strings.WHATSAPP_PHONE_DISPLAY,
                    textAlign = TextAlign.Center,
                    color = PriceUpGreen,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    letterSpacing = 1.sp
                )

                Spacer(modifier = Modifier.height(4.dp))

                Text(
                    text = if (isArabic) "تواصل مباشرة مع مستشاري صانع الأحلام لشراء الذهب، حجز السبائك، والاستفسار عن الأسعار اللحظية"
                    else "Connect directly with Dream Maker consultants for bullion, jewelry orders & live market quotes",
                    textAlign = TextAlign.Center,
                    color = TextSilver,
                    fontSize = 13.sp,
                    lineHeight = 18.sp,
                    modifier = Modifier.padding(horizontal = 8.dp)
                )

                Spacer(modifier = Modifier.height(18.dp))

                // Dual Buttons: Open WhatsApp + Direct Call
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    // Open WhatsApp Button
                    Surface(
                        shape = RoundedCornerShape(12.dp),
                        color = Color(0xFF25D366),
                        modifier = Modifier
                            .weight(1f)
                            .height(48.dp)
                            .clip(RoundedCornerShape(12.dp))
                            .clickable { ContactUtils.openWhatsApp(context = context, isArabic = isArabic) }
                    ) {
                        Row(
                            modifier = Modifier.fillMaxSize(),
                            horizontalArrangement = Arrangement.Center,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Icon(
                                imageVector = Icons.Default.Chat,
                                contentDescription = null,
                                tint = Color.White,
                                modifier = Modifier.size(20.dp)
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            Text(
                                text = if (isArabic) "محادثة في واتساب" else "Chat on WhatsApp",
                                color = Color.White,
                                fontSize = 14.sp,
                                fontWeight = FontWeight.Bold
                            )
                        }
                    }

                    // Direct Call Button
                    Surface(
                        shape = RoundedCornerShape(12.dp),
                        color = DarkSurfaceVariant,
                        border = androidx.compose.foundation.BorderStroke(1.dp, GoldBorder),
                        modifier = Modifier
                            .weight(0.9f)
                            .height(48.dp)
                            .clip(RoundedCornerShape(12.dp))
                            .clickable { ContactUtils.callBoutique(context) }
                    ) {
                        Row(
                            modifier = Modifier.fillMaxSize(),
                            horizontalArrangement = Arrangement.Center,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Icon(
                                imageVector = Icons.Default.Call,
                                contentDescription = null,
                                tint = GoldLight,
                                modifier = Modifier.size(19.dp)
                            )
                            Spacer(modifier = Modifier.width(6.dp))
                            Text(
                                text = if (isArabic) "اتصال هاتفي" else "Phone Call",
                                color = GoldLight,
                                fontSize = 13.sp,
                                fontWeight = FontWeight.Bold
                            )
                        }
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(20.dp))

        // Quick WhatsApp Order Topics
        Column(modifier = Modifier.padding(horizontal = 16.dp)) {
            SectionHeader(title = if (isArabic) "خدمات واستفسارات سريعة" else "Quick Concierge Requests")

            Spacer(modifier = Modifier.height(12.dp))

            OrderTopicCard(
                icon = Icons.Default.Diamond,
                title = if (isArabic) "طلب شراء مجوهرات أو خواتم" else "Jewelry & Ring Inquiry",
                subtitle = if (isArabic) "استفسر عن توفر المقاسات وأحدث الموديلات" else "Inquire about sizing, designs and stock",
                onClick = {
                    ContactUtils.openWhatsApp(context, isArabic = isArabic)
                }
            )

            Spacer(modifier = Modifier.height(10.dp))

            OrderTopicCard(
                icon = Icons.Default.Verified,
                title = if (isArabic) "حجز وشراء سبائك ذهب عيار ٢٤" else "24K Gold Bullion Reservation",
                subtitle = if (isArabic) "سبائك ١٠ غ، ٢٠ غ، ٥٠ غ، وتولة استثمارية بأسعار البورصة" else "10g, 20g, 50g & tola at live Kuwait rates",
                onClick = {
                    ContactUtils.openWhatsApp(context, isArabic = isArabic)
                }
            )

            Spacer(modifier = Modifier.height(10.dp))

            OrderTopicCard(
                icon = Icons.Default.SupportAgent,
                title = if (isArabic) "استشارة أسعار الذهب الفورية" else "Live Gold Price Advisory",
                subtitle = if (isArabic) "تحديث دقيق لأسعار البيع والشراء كل ١٥ ثانية" else "Accurate Kuwait market buy & sell updates",
                onClick = {
                    ContactUtils.openWhatsApp(context, isArabic = isArabic)
                }
            )

            Spacer(modifier = Modifier.height(10.dp))

            // Replay Logo Motion
            OrderTopicCard(
                icon = Icons.Default.PlayCircleOutline,
                title = if (isArabic) "معاينة لوجو موشن صانع الأحلام" else "Replay Dream Maker Logo Motion",
                subtitle = if (isArabic) "شاهد المقدمة الفاخرة وشعار صانع الأحلام المتحرك" else "Watch the luxury emblem motion entrance",
                onClick = onReplayLogoMotion
            )
        }

        Spacer(modifier = Modifier.height(20.dp))

        // Boutique Information Card
        GoldCard(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
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
                        modifier = Modifier.size(20.dp)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = if (isArabic) "بوتيكات وفروع صانع الأحلام" else "Dream Maker Boutiques",
                        color = TextWhite,
                        fontSize = 15.sp,
                        fontWeight = FontWeight.Bold
                    )
                }

                Spacer(modifier = Modifier.height(10.dp))

                Text(
                    text = Strings.boutiquesList(language),
                    color = TextSilver,
                    fontSize = 13.sp,
                    lineHeight = 19.sp
                )

                Spacer(modifier = Modifier.height(10.dp))

                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        imageVector = Icons.Default.Security,
                        contentDescription = null,
                        tint = GoldLight,
                        modifier = Modifier.size(16.dp)
                    )
                    Spacer(modifier = Modifier.width(6.dp))
                    Text(
                        text = Strings.purityGuarantee(language),
                        color = TextMuted,
                        fontSize = 11.sp
                    )
                }
            }
        }
    }
}

@Composable
private fun OrderTopicCard(
    icon: ImageVector,
    title: String,
    subtitle: String,
    onClick: () -> Unit
) {
    GoldCard(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick),
        backgroundColor = DarkSurface,
        borderColor = GoldBorder.copy(alpha = 0.5f),
        borderWidth = 0.8.dp,
        onClick = onClick
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(14.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .size(42.dp)
                    .clip(CircleShape)
                    .background(DarkSurfaceVariant)
                    .border(0.8.dp, GoldBorder, CircleShape),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = icon,
                    contentDescription = null,
                    tint = GoldLight,
                    modifier = Modifier.size(22.dp)
                )
            }

            Spacer(modifier = Modifier.width(14.dp))

            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = title,
                    color = TextWhite,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.SemiBold
                )
                Spacer(modifier = Modifier.height(2.dp))
                Text(
                    text = subtitle,
                    color = TextMuted,
                    fontSize = 12.sp
                )
            }

            Icon(
                imageVector = Icons.Default.Chat,
                contentDescription = null,
                tint = PriceUpGreen,
                modifier = Modifier.size(18.dp)
            )
        }
    }
}
