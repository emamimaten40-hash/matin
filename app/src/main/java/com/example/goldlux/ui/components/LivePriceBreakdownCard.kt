package com.example.goldlux.ui.components

import com.example.ui.theme.LuxDisplay
import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.TrendingUp
import androidx.compose.material3.Divider
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.goldlux.localization.Localization
import com.example.goldlux.localization.Strings
import com.example.goldlux.model.AppLanguage
import com.example.goldlux.model.Currency
import com.example.goldlux.model.Product
import com.example.ui.theme.DarkBackground
import com.example.ui.theme.DarkSurface
import com.example.ui.theme.DarkSurfaceElevated
import com.example.ui.theme.GoldBorder
import com.example.ui.theme.GoldLight
import com.example.ui.theme.GoldPrimary
import com.example.ui.theme.PriceUpGreen
import com.example.ui.theme.TextMuted
import com.example.ui.theme.TextSilver
import com.example.ui.theme.TextWhite

@Composable
fun LivePriceBreakdownCard(
    product: Product,
    gram24KUsd: Double,
    currency: Currency,
    language: AppLanguage,
    modifier: Modifier = Modifier
) {
    val isArabic = language == AppLanguage.ARABIC
    val rawGoldCost = product.calculateGoldCost(gram24KUsd, currency)
    val makingFee = product.calculateMakingFee(currency)
    val vatAmount = product.calculateVat(gram24KUsd, currency)
    val finalPrice = product.calculateFinalPrice(gram24KUsd, currency)

    val karatRatePerGram = (gram24KUsd * product.karat.purityFactor) * currency.usdRate

    GoldCard(
        modifier = modifier.fillMaxWidth(),
        backgroundColor = DarkSurface,
        borderColor = GoldBorder,
        borderWidth = 1.dp
    ) {
        Column(modifier = Modifier.padding(18.dp)) {
            // Header with Live Market Indicator
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Box(
                        modifier = Modifier
                            .size(10.dp)
                            .clip(CircleShape)
                            .background(PriceUpGreen)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = Strings.livePriceBreakdown(language),
                        color = GoldLight,
                        fontSize = 15.sp,
                        fontWeight = FontWeight.Bold
                    )
                }

                // Karat Badge
                Box(
                    modifier = Modifier
                        .clip(RoundedCornerShape(8.dp))
                        .background(Color(0x33D4AF37))
                        .border(0.6.dp, GoldPrimary, RoundedCornerShape(8.dp))
                        .padding(horizontal = 8.dp, vertical = 3.dp)
                ) {
                    Text(
                        text = if (isArabic) product.karat.nameAr else product.karat.nameEn,
                        color = GoldLight,
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
            }

            Spacer(modifier = Modifier.height(6.dp))
            Text(
                text = Strings.livePriceFormulaExplanation(language),
                color = TextMuted,
                fontSize = 11.sp,
                lineHeight = 15.sp
            )

            Spacer(modifier = Modifier.height(14.dp))
            HorizontalDivider(color = Color(0x22D4AF37), thickness = 0.8.dp)
            Spacer(modifier = Modifier.height(12.dp))

            // Step 1: Raw Gold Value
            BreakdownRow(
                title = Strings.rawGoldValue(language),
                subtitle = "${Localization.formatWeight(product.weightInGrams, isArabic)} × ${Localization.formatPrice(karatRatePerGram, currency, isArabic)}",
                amount = Localization.formatPrice(rawGoldCost, currency, isArabic),
                isArabic = isArabic
            )

            Spacer(modifier = Modifier.height(10.dp))

            // Step 2: Making Fee
            val makingRateConverted = product.makingChargePerGramUsd * currency.usdRate
            BreakdownRow(
                title = Strings.makingCharge(language),
                subtitle = "${Localization.formatWeight(product.weightInGrams, isArabic)} × ${Localization.formatPrice(makingRateConverted, currency, isArabic)}",
                amount = Localization.formatPrice(makingFee, currency, isArabic),
                isArabic = isArabic
            )

            Spacer(modifier = Modifier.height(10.dp))

            // Step 3: VAT (15%)
            BreakdownRow(
                title = Strings.vat15(language),
                subtitle = if (isArabic) "١٥٪ ضريبة رسمية" else "15% Standard Tax",
                amount = Localization.formatPrice(vatAmount, currency, isArabic),
                isArabic = isArabic
            )

            Spacer(modifier = Modifier.height(14.dp))
            HorizontalDivider(color = Color(0x33D4AF37), thickness = 1.dp)
            Spacer(modifier = Modifier.height(12.dp))

            // Final Price Total
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column {
                    Text(
                        text = Strings.finalPrice(language),
                        color = TextWhite,
                        fontSize = 14.sp,
                        fontWeight = FontWeight.SemiBold
                    )
                    Text(
                        text = if (isArabic) "السعر النهائي شامل الضريبة والشحن" else "Inclusive of Tax & Free Shipping",
                        color = TextMuted,
                        fontSize = 11.sp
                    )
                }

                AnimatedContent(
                    targetState = finalPrice,
                    transitionSpec = { fadeIn() togetherWith fadeOut() },
                    label = "final_price_anim"
                ) { target ->
                    Text(
                        text = Localization.formatPrice(target, currency, isArabic),
                        color = GoldLight,
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold,
                        fontFamily = LuxDisplay
                    )
                }
            }
        }
    }
}

@Composable
private fun BreakdownRow(
    title: String,
    subtitle: String,
    amount: String,
    isArabic: Boolean
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column(modifier = Modifier.weight(1f)) {
            Text(
                text = title,
                color = TextSilver,
                fontSize = 13.sp,
                fontWeight = FontWeight.Medium
            )
            Text(
                text = subtitle,
                color = TextMuted,
                fontSize = 11.sp
            )
        }
        Text(
            text = amount,
            color = TextWhite,
            fontSize = 13.sp,
            fontWeight = FontWeight.SemiBold
        )
    }
}
