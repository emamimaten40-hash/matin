package com.example.goldlux.data

import android.util.Log
import com.example.goldlux.model.GoldRateItem
import com.example.goldlux.model.Karat
import com.example.goldlux.model.LiveGoldPriceSnapshot
import com.example.goldlux.model.PricePoint
import com.example.goldlux.model.Timeframe
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.json.JSONObject
import java.io.BufferedReader
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.URL
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale
import kotlin.random.Random

interface IGoldPriceRepository {
    suspend fun getLiveGoldPrices(forceRefresh: Boolean = false): LiveGoldPriceSnapshot
    suspend fun getChartData(timeframe: Timeframe): List<PricePoint>
}

class GoldPriceRepository : IGoldPriceRepository {

    // Live prices come from Dar Al Sabaek (daralsabaek.com) public price feed.
    // All values are Kuwaiti Dinar (KWD) per gram. "purchase" = price the customer
    // pays (shop sells), "sell" = price the customer receives (shop buys back).
    private val TROY_OUNCE_GRAMS = 31.1034768
    private val KWD_TO_USD_RATE = 1.0 / 0.3068
    private val DAR_AL_SABAEK_API_URL = "https://api.daralsabaek.com/api/goldAndFundBalance/getMetalSellAndBuyPrices"

    // Last known real prices (initial values are only shown until the first successful fetch)
    private var buy24: Double = 42.004
    private var buy22: Double = 38.503
    private var buy21: Double = 36.754
    private var buy18: Double = 31.503
    private var sell24: Double = 41.709
    private var sell22: Double = 38.003
    private var sell21: Double = 36.154
    private var sell18: Double = 30.803
    private var ounceUsd: Double = 4166.13
    private var silverKiloKwd: Double = 631.93
    private var hasLiveData = false

    // Session statistics built from real observed prices (no simulated ticks)
    private var openBuy24: Double? = null
    private var sessionHigh24: Double? = null
    private var sessionLow24: Double? = null

    private var lastSnapshot: LiveGoldPriceSnapshot? = null
    private val liveDayChartPoints = mutableListOf<PricePoint>()

    private fun fetchDarAlSabaek(): Boolean {
        var connection: HttpURLConnection? = null
        return try {
            val conn = URL(DAR_AL_SABAEK_API_URL).openConnection() as HttpURLConnection
            connection = conn
            conn.requestMethod = "GET"
            conn.connectTimeout = 6000
            conn.readTimeout = 6000
            conn.setRequestProperty("User-Agent", "SaneAlAhlam-Kuwait/1.0 (Android)")
            conn.setRequestProperty("Accept", "application/json")
            if (conn.responseCode != HttpURLConnection.HTTP_OK) return false
            val response = BufferedReader(InputStreamReader(conn.inputStream)).use { it.readText() }
            val json = JSONObject(response)
            if (!json.optBoolean("succeeded", false)) return false
            val r = json.optJSONObject("result") ?: return false
            val p24 = r.optDouble("purchaseGoldPrice", 0.0)
            if (p24.isNaN() || p24 <= 0.0) return false

            buy24 = p24
            buy22 = r.optDouble("purchase22GoldPrice", p24 * 22.0 / 24.0)
            buy21 = r.optDouble("purchase21GoldPrice", p24 * 21.0 / 24.0)
            buy18 = r.optDouble("purchase18GoldPrice", p24 * 18.0 / 24.0)
            sell24 = r.optDouble("sellGoldPrice", sell24)
            sell22 = r.optDouble("sell22GoldPrice", sell22)
            sell21 = r.optDouble("sell21GoldPrice", sell21)
            sell18 = r.optDouble("sell18GoldPrice", sell18)
            val oz = r.optDouble("goldOuncePrice", 0.0)
            ounceUsd = if (oz > 0.0) oz else buy24 * KWD_TO_USD_RATE * TROY_OUNCE_GRAMS
            val silver = r.optDouble("silverKiloPrice", 0.0)
            if (silver > 0.0) silverKiloKwd = silver
            true
        } catch (e: Exception) {
            Log.d("GoldPriceRepo", "Dar Al Sabaek feed unavailable: ${e.message}")
            false
        } finally {
            connection?.disconnect()
        }
    }

    override suspend fun getLiveGoldPrices(forceRefresh: Boolean): LiveGoldPriceSnapshot = withContext(Dispatchers.IO) {
        if (lastSnapshot != null && !forceRefresh) {
            return@withContext lastSnapshot!!
        }

        val isLiveNetwork = fetchDarAlSabaek()
        if (isLiveNetwork) hasLiveData = true

        if (isLiveNetwork) {
            if (openBuy24 == null) openBuy24 = buy24
            sessionHigh24 = maxOf(sessionHigh24 ?: buy24, buy24)
            sessionLow24 = minOf(sessionLow24 ?: buy24, buy24)
        }

        val gram24KUsd = buy24 * KWD_TO_USD_RATE
        val open = openBuy24 ?: buy24
        val changePercent = if (open > 0.0) (buy24 - open) / open * 100.0 else 0.0
        val high24h = (sessionHigh24 ?: buy24) * KWD_TO_USD_RATE
        val low24h = (sessionLow24 ?: buy24) * KWD_TO_USD_RATE

        fun karatRate(karat: Karat, buy: Double, sell: Double, purityAr: String, purityEn: String) =
            GoldRateItem(
                id = "rate_${karat.karatNumber}k",
                titleEn = "${karat.karatNumber}K Gold / Gram",
                titleAr = "${karat.nameAr} / غرام",
                subtitleEn = "Sell to us: ${"%.3f".format(Locale.US, sell)} KD • $purityEn",
                subtitleAr = "البيع: ${"%.3f".format(Locale.US, sell)} د.ك • $purityAr",
                basePriceUsd = buy * KWD_TO_USD_RATE,
                changePercent = changePercent,
                karat = karat,
                sellPriceKwd = sell,
                buyPriceKwd = buy
            )

        // Only the four karats sold in the boutique: 24 • 22 • 21 • 18
        val rates = listOf(
            karatRate(Karat.K24, buy24, sell24, "ذهب صافي", "Pure gold"),
            karatRate(Karat.K22, buy22, sell22, "نقاء ٩١٦", "916 purity"),
            karatRate(Karat.K21, buy21, sell21, "نقاء ٨٧٥", "875 purity"),
            karatRate(Karat.K18, buy18, sell18, "نقاء ٧٥٠", "750 purity")
        )

        val now = System.currentTimeMillis()
        val hourFormat = SimpleDateFormat("h:mm a", Locale.US)
        val hourFormatAr = SimpleDateFormat("h:mm a", Locale("ar"))

        // 1D chart = real prices observed in this session (one point per update)
        if (hasLiveData) {
            liveDayChartPoints.add(
                PricePoint(
                    timestamp = now,
                    dateLabelEn = hourFormat.format(Date(now)),
                    dateLabelAr = hourFormatAr.format(Date(now)),
                    priceUsdPerGram24K = gram24KUsd
                )
            )
            if (liveDayChartPoints.size > 240) liveDayChartPoints.removeAt(0)
        }

        val chartData = mapOf(
            Timeframe.ONE_DAY to liveDayChartPoints.toList(),
            Timeframe.ONE_WEEK to generateTimeframeData(Timeframe.ONE_WEEK, gram24KUsd),
            Timeframe.ONE_MONTH to generateTimeframeData(Timeframe.ONE_MONTH, gram24KUsd),
            Timeframe.THREE_MONTHS to generateTimeframeData(Timeframe.THREE_MONTHS, gram24KUsd),
            Timeframe.ONE_YEAR to generateTimeframeData(Timeframe.ONE_YEAR, gram24KUsd)
        )

        val snapshot = LiveGoldPriceSnapshot(
            ounceUsd = ounceUsd,
            gram24KUsd = gram24KUsd,
            change24hPercent = changePercent,
            high24hUsd = high24h,
            low24hUsd = low24h,
            lastUpdatedTimestamp = System.currentTimeMillis(),
            rates = rates,
            chartData = chartData,
            isOffline = !isLiveNetwork,
            sourceName = if (isLiveNetwork) "دار السبائك (مباشر)" else "دار السبائك",
            base24KKwd = buy24,
            sell24KKwd = sell24,
            silverKiloKwd = silverKiloKwd
        )

        lastSnapshot = snapshot
        snapshot
    }

    override suspend fun getChartData(timeframe: Timeframe): List<PricePoint> {
        val snapshot = getLiveGoldPrices(forceRefresh = false)
        return snapshot.chartData[timeframe] ?: generateTimeframeData(timeframe, snapshot.gram24KUsd)
    }

    private fun generateTimeframeData(timeframe: Timeframe, currentGramPrice: Double): List<PricePoint> {
        val points = mutableListOf<PricePoint>()
        val count = when (timeframe) {
            Timeframe.ONE_DAY -> 16
            Timeframe.ONE_WEEK -> 14
            Timeframe.ONE_MONTH -> 20
            Timeframe.THREE_MONTHS -> 24
            Timeframe.ONE_YEAR -> 24
        }

        val hourFormat = SimpleDateFormat("h:mm a", Locale.US)
        val hourFormatAr = SimpleDateFormat("h:mm a", Locale("ar"))
        val dayFormat = SimpleDateFormat("d MMM", Locale.US)
        val dayFormatAr = SimpleDateFormat("d MMMM", Locale("ar"))
        val monthFormat = SimpleDateFormat("MMM yyyy", Locale.US)
        val monthFormatAr = SimpleDateFormat("MMMM yyyy", Locale("ar"))

        val walkPrice = when (timeframe) {
            Timeframe.ONE_DAY -> currentGramPrice * 0.988
            Timeframe.ONE_WEEK -> currentGramPrice * 0.965
            Timeframe.ONE_MONTH -> currentGramPrice * 0.940
            Timeframe.THREE_MONTHS -> currentGramPrice * 0.910
            Timeframe.ONE_YEAR -> currentGramPrice * 0.835
        }

        val stepMillis = when (timeframe) {
            Timeframe.ONE_DAY -> (1.5 * 3600 * 1000).toLong()
            Timeframe.ONE_WEEK -> (12 * 3600 * 1000).toLong()
            Timeframe.ONE_MONTH -> (1.5 * 24 * 3600 * 1000).toLong()
            Timeframe.THREE_MONTHS -> (3.75 * 24 * 3600 * 1000).toLong()
            Timeframe.ONE_YEAR -> (15.2 * 24 * 3600 * 1000).toLong()
        }

        val startTime = System.currentTimeMillis() - (stepMillis * (count - 1))

        for (i in 0 until count) {
            val pointTime = startTime + (i * stepMillis)
            val date = Date(pointTime)

            val progress = i.toDouble() / (count - 1)
            val target = currentGramPrice
            val noise = Random.nextDouble(-0.35, 0.35)
            val price = (walkPrice + ((target - walkPrice) * (progress * 0.92))) + noise

            val labelEn = when (timeframe) {
                Timeframe.ONE_DAY -> hourFormat.format(date)
                Timeframe.ONE_WEEK, Timeframe.ONE_MONTH, Timeframe.THREE_MONTHS -> dayFormat.format(date)
                Timeframe.ONE_YEAR -> monthFormat.format(date)
            }
            val labelAr = when (timeframe) {
                Timeframe.ONE_DAY -> hourFormatAr.format(date)
                Timeframe.ONE_WEEK, Timeframe.ONE_MONTH, Timeframe.THREE_MONTHS -> dayFormatAr.format(date)
                Timeframe.ONE_YEAR -> monthFormatAr.format(date)
            }

            points.add(
                PricePoint(
                    timestamp = pointTime,
                    dateLabelEn = labelEn,
                    dateLabelAr = labelAr,
                    priceUsdPerGram24K = if (i == count - 1) currentGramPrice else price
                )
            )
        }

        return points
    }
}
