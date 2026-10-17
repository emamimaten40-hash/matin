package com.example.goldlux.model

enum class Timeframe(val labelEn: String, val labelAr: String) {
    ONE_DAY("1D", "١ يوم"),
    ONE_WEEK("1W", "١ أسبوع"),
    ONE_MONTH("1M", "١ شهر"),
    THREE_MONTHS("3M", "٣ أشهر"),
    ONE_YEAR("1Y", "١ سنة")
}

data class PricePoint(
    val timestamp: Long,
    val dateLabelEn: String,
    val dateLabelAr: String,
    val priceUsdPerGram24K: Double
)

enum class Karat(
    val karatNumber: Int,
    val purityFactor: Double, // purity fraction
    val nameEn: String,
    val nameAr: String
) {
    K24(24, 0.999, "24 Karat", "عيار ٢٤"),
    K22(22, 0.916, "22 Karat", "عيار ٢٢"),
    K21(21, 0.875, "21 Karat", "عيار ٢١"),
    K18(18, 0.750, "18 Karat", "عيار ١٨")
}

data class GoldRateItem(
    val id: String,
    val titleEn: String,
    val titleAr: String,
    val subtitleEn: String,
    val subtitleAr: String,
    val basePriceUsd: Double,
    val changePercent: Double,
    val isBullion: Boolean = false,
    val karat: Karat? = null,
    val sellPriceKwd: Double? = null,
    val buyPriceKwd: Double? = null
)

data class LiveGoldPriceSnapshot(
    val ounceUsd: Double,
    val gram24KUsd: Double,
    val change24hPercent: Double,
    val high24hUsd: Double,
    val low24hUsd: Double,
    val lastUpdatedTimestamp: Long,
    val rates: List<GoldRateItem>,
    val chartData: Map<Timeframe, List<PricePoint>>,
    val isOffline: Boolean = false,
    val sourceName: String = "بورصة الكويت",
    val base24KKwd: Double = 42.775,
    val sell24KKwd: Double = 42.330,
    val silverKiloKwd: Double = 695.69
)
