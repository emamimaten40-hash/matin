package com.example.goldlux.model

enum class AppLanguage(val code: String, val displayName: String, val nativeName: String, val isRtl: Boolean) {
    ARABIC("ar", "Arabic", "العربية", true),
    ENGLISH("en", "English", "English", false)
}

enum class Currency(
    val code: String,
    val nameEn: String,
    val nameAr: String,
    val symbolAr: String,
    val symbolEn: String,
    val usdRate: Double // 1 USD in this currency
) {
    KWD("KWD", "Kuwaiti Dinar", "دينار كويتي", "د.ك", "KD", 0.3068),
    USD("USD", "US Dollar", "دولار أمريكي", "$", "$", 1.0),
    AED("AED", "UAE Dirham", "درهم إماراتي", "د.إ", "AED", 3.6725);

    fun getSymbol(isArabic: Boolean): String = if (isArabic) symbolAr else symbolEn
    fun getName(isArabic: Boolean): String = if (isArabic) nameAr else nameEn
}
