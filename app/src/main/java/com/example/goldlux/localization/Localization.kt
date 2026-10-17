package com.example.goldlux.localization

import com.example.goldlux.model.AppLanguage
import com.example.goldlux.model.Currency
import java.text.DecimalFormat
import java.text.DecimalFormatSymbols
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale

object Localization {

    private val ARABIC_DIGITS = charArrayOf('٠', '١', '٢', '٣', '٤', '٥', '٦', '٧', '٨', '٩')

    /**
     * Converts a formatted string (like "1,250.50") into Arabic-Indic numerals
     * if the active language is Arabic.
     */
    fun formatNumber(number: Double, isArabic: Boolean, decimals: Int = 2): String {
        val pattern = if (decimals <= 0) "#,##0" else "#,##0." + "0".repeat(decimals)
        val symbols = DecimalFormatSymbols(Locale.US)
        val df = DecimalFormat(pattern, symbols)
        val standardStr = df.format(number)

        if (!isArabic) return standardStr

        val sb = StringBuilder()
        for (c in standardStr) {
            when {
                c in '0'..'9' -> sb.append(ARABIC_DIGITS[c - '0'])
                c == ',' -> sb.append('٬') // Arabic thousands separator
                c == '.' -> sb.append('٫') // Arabic decimal point
                else -> sb.append(c)
            }
        }
        return sb.toString()
    }

    /**
     * Formats integer number
     */
    fun formatInt(number: Int, isArabic: Boolean): String {
        return formatNumber(number.toDouble(), isArabic, decimals = 0)
    }

    /**
     * Formats currency with localized symbol and digits
     * e.g. "42.775 KD" in English, "٤٢٫٧٧٥ د.ك" in Arabic
     */
    fun formatPrice(
        amount: Double,
        currency: Currency,
        isArabic: Boolean,
        decimals: Int = if (currency == Currency.KWD && amount < 100.0) 3 else 2
    ): String {
        val numFormatted = formatNumber(amount, isArabic, decimals = decimals)
        val symbol = currency.getSymbol(isArabic)
        return "$numFormatted $symbol"
    }

    /**
     * Formats percentage change like "+1.24%" or "-0.85%"
     */
    fun formatPercent(percent: Double, isArabic: Boolean): String {
        val prefix = if (percent >= 0) "+" else ""
        val numFormatted = formatNumber(Math.abs(percent), isArabic, decimals = 2)
        val sign = if (percent >= 0) "+" else "-"
        return if (isArabic) {
            "٪$numFormatted$sign"
        } else {
            "$prefix$numFormatted%"
        }
    }

    /**
     * Formats weight like "8.50 g" or "٨٫٥٠ غرام"
     */
    fun formatWeight(weightGrams: Double, isArabic: Boolean): String {
        val numFormatted = formatNumber(weightGrams, isArabic, decimals = 2)
        val unit = if (isArabic) "غرام" else "g"
        return "$numFormatted $unit"
    }

    /**
     * Returns localized date (Gregorian, and in Arabic also shows Hijri approximation)
     */
    fun getLocalizedCurrentDate(isArabic: Boolean): String {
        val cal: Calendar = Calendar.getInstance()
        val gregorianFormat = SimpleDateFormat(
            if (isArabic) "EEEE، d MMMM yyyy" else "EEEE, MMMM d, yyyy",
            if (isArabic) Locale("ar") else Locale.US
        )
        val gregDate = gregorianFormat.format(cal.time)

        if (!isArabic) return gregDate

        // Approximate Hijri conversion for Middle East luxury feel (e.g. 1448 AH)
        val year: Int = cal.get(Calendar.YEAR)
        val month: Int = cal.get(Calendar.MONTH)
        val day: Int = cal.get(Calendar.DAY_OF_MONTH)
        val hijriYear = ((year - 622) * 1.030684).toInt()
        val hijriMonths = arrayOf(
            "المحرّم", "صفر", "ربيع الأول", "ربيع الآخر",
            "جمادى الأولى", "جمادى الآخرة", "رجب", "شعبان",
            "رمضان", "شوّال", "ذو القعدة", "ذو الحجة"
        )
        val hijriMonth = hijriMonths[(month + 1) % 12]
        val hijriDay = ((day + 5) % 29) + 1
        val hijriStr = "${formatInt(hijriDay, true)} $hijriMonth ${formatInt(hijriYear, true)} هـ"

        return "$gregDate • $hijriStr"
    }
}
