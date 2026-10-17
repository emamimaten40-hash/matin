package com.example.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.LineHeightStyle
import androidx.compose.ui.unit.em
import androidx.compose.ui.unit.sp
import com.example.R

/*
 * GoldLux type system
 * -------------------
 * LuxDisplay  -> "El Messiri": elegant, high-contrast Arabic + Latin face used for
 *                brand name, screen titles, section headers and prices.
 * LuxBody     -> "Alexandria": modern geometric Arabic + Latin face used for all
 *                UI text (labels, body copy, buttons, chips).
 *
 * El Messiri has fixed-width (tabular) digits — Latin 0-9 and Arabic-Indic ٠-٩ —
 * and its vertical metrics are re-balanced so it centres like the body font.
 * Alexandria has tabular Latin digits. Live prices never "jump" while animating.
 * Both families are licensed under the SIL Open Font License (see FONTS_OFL_*.txt).
 */
val LuxDisplay = FontFamily(
    Font(R.font.el_messiri_medium, FontWeight.Normal),
    Font(R.font.el_messiri_medium, FontWeight.Medium),
    Font(R.font.el_messiri_semibold, FontWeight.SemiBold),
    Font(R.font.el_messiri_bold, FontWeight.Bold),
    Font(R.font.el_messiri_bold, FontWeight.ExtraBold),
    Font(R.font.el_messiri_bold, FontWeight.Black)
)

val LuxBody = FontFamily(
    Font(R.font.alexandria_light, FontWeight.Light),
    Font(R.font.alexandria_regular, FontWeight.Normal),
    Font(R.font.alexandria_medium, FontWeight.Medium),
    Font(R.font.alexandria_semibold, FontWeight.SemiBold),
    Font(R.font.alexandria_bold, FontWeight.Bold),
    Font(R.font.alexandria_bold, FontWeight.ExtraBold),
    Font(R.font.alexandria_bold, FontWeight.Black)
)

/**
 * Every line is vertically centred inside its line box (extra leading trimmed
 * above the first and below the last line),
 * so Arabic and Latin text, icons and chips sit on the same visual centre line
 * inside Rows (no more "up and down" text).
 */
private val CenteredLines = LineHeightStyle(
    alignment = LineHeightStyle.Alignment.Center,
    trim = LineHeightStyle.Trim.Both
)

/** Base style for any text that only sets fontSize/fontWeight inline. */
private val BodyBase = TextStyle(
    fontFamily = LuxBody,
    // relative line height: scales with whatever fontSize is set inline
    lineHeight = 1.5.em,
    letterSpacing = 0.sp,
    lineHeightStyle = CenteredLines
)

private val DisplayBase = TextStyle(
    fontFamily = LuxDisplay,
    lineHeight = 1.3.em,
    letterSpacing = 0.sp,
    lineHeightStyle = CenteredLines
)

val Typography = Typography(
    displayLarge = DisplayBase.copy(fontWeight = FontWeight.Bold, fontSize = 34.sp, color = TextWhite),
    displayMedium = DisplayBase.copy(fontWeight = FontWeight.Bold, fontSize = 28.sp, color = TextWhite),
    displaySmall = DisplayBase.copy(fontWeight = FontWeight.Bold, fontSize = 24.sp, color = TextWhite),
    headlineLarge = DisplayBase.copy(fontWeight = FontWeight.Bold, fontSize = 24.sp, color = TextWhite),
    headlineMedium = DisplayBase.copy(fontWeight = FontWeight.SemiBold, fontSize = 20.sp, color = TextWhite),
    headlineSmall = DisplayBase.copy(fontWeight = FontWeight.SemiBold, fontSize = 18.sp, color = TextWhite),
    titleLarge = BodyBase.copy(fontWeight = FontWeight.SemiBold, fontSize = 18.sp, color = TextWhite),
    titleMedium = BodyBase.copy(fontWeight = FontWeight.Medium, fontSize = 16.sp, color = TextWhite),
    titleSmall = BodyBase.copy(fontWeight = FontWeight.Medium, fontSize = 14.sp, color = TextWhite),
    bodyLarge = BodyBase.copy(fontWeight = FontWeight.Normal, fontSize = 15.sp, color = TextSilver),
    bodyMedium = BodyBase.copy(fontWeight = FontWeight.Normal, fontSize = 14.sp, color = TextSilver),
    bodySmall = BodyBase.copy(fontWeight = FontWeight.Normal, fontSize = 12.sp, color = TextSilver),
    labelLarge = BodyBase.copy(fontWeight = FontWeight.SemiBold, fontSize = 14.sp, color = TextWhite),
    labelMedium = BodyBase.copy(fontWeight = FontWeight.Medium, fontSize = 12.sp, color = TextMuted),
    labelSmall = BodyBase.copy(fontWeight = FontWeight.Medium, fontSize = 11.sp, color = TextMuted)
)
