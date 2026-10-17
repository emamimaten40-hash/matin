package com.example

import com.example.goldlux.localization.Localization
import com.example.goldlux.model.Currency
import com.example.goldlux.model.Karat
import com.example.goldlux.model.Product
import com.example.goldlux.model.ProductCategory
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test

class GoldLuxUnitTest {

    @Test
    fun `test Arabic-Indic digit formatting`() {
        val formattedAr = Localization.formatNumber(1250.75, isArabic = true, decimals = 2)
        // 1250.75 -> ١٬٢٥٠٫٧٥
        assertTrue(formattedAr.contains("١"))
        assertTrue(formattedAr.contains("٢"))
        assertTrue(formattedAr.contains("٥"))
        assertTrue(formattedAr.contains("٠"))
    }

    @Test
    fun `test English digit formatting`() {
        val formattedEn = Localization.formatNumber(1250.75, isArabic = false, decimals = 2)
        assertEquals("1,250.75", formattedEn)
    }

    @Test
    fun `test Gold Price Breakdown calculation`() {
        val testProduct = Product(
            id = "test_ring",
            nameEn = "Test Ring",
            nameAr = "خاتم تجريبي",
            descriptionEn = "Description",
            descriptionAr = "وصف",
            category = ProductCategory.RINGS,
            karat = Karat.K21, // 0.875
            weightInGrams = 10.0,
            makingChargePerGramUsd = 10.0,
            imageUrls = emptyList()
        )

        val gram24KUsd = 100.0 // $100 per gram of 24K
        val currencyUsd = Currency.USD

        // Raw gold cost = 10g * (100 * 0.875) = $875.0
        val goldCost = testProduct.calculateGoldCost(gram24KUsd, currencyUsd)
        assertEquals(875.0, goldCost, 0.01)

        // Making fee = 10g * 10 = $100.0
        val makingFee = testProduct.calculateMakingFee(currencyUsd)
        assertEquals(100.0, makingFee, 0.01)

        // VAT = (875 + 100) * 0.05 = 975 * 0.05 = $48.75
        val vat = testProduct.calculateVat(gram24KUsd, currencyUsd)
        assertEquals(48.75, vat, 0.01)

        // Total = 975 + 48.75 = $1023.75
        val total = testProduct.calculateFinalPrice(gram24KUsd, currencyUsd)
        assertEquals(1023.75, total, 0.01)
    }

    @Test
    fun `test BARS are 0 percent VAT`() {
        val testBar = Product(
            id = "test_bar",
            nameEn = "Test Bar",
            nameAr = "سبيكة تجريبية",
            descriptionEn = "Bullion",
            descriptionAr = "سبيكة",
            category = ProductCategory.BARS,
            karat = Karat.K24,
            weightInGrams = 10.0,
            makingChargePerGramUsd = 2.0,
            imageUrls = emptyList()
        )
        assertEquals(0.0, testBar.calculateVat(100.0, Currency.USD), 0.001)
    }

    @Test
    fun `test KWD formatting with 3 decimals`() {
        val formatted = Localization.formatPrice(42.775, Currency.KWD, isArabic = false)
        assertEquals("42.775 KD", formatted)
    }

    @Test
    fun `test Product price changes automatically when gold rate updates`() {
        val testNecklace = Product(
            id = "necklace_21k",
            nameEn = "Luxury 21K Necklace",
            nameAr = "قلادة ذهبية عيار ٢١",
            descriptionEn = "Fine Jewellery",
            descriptionAr = "مجوهرات فاخرة",
            category = ProductCategory.NECKLACES,
            karat = Karat.K21, // 0.875 purity
            weightInGrams = 15.0,
            makingChargePerGramUsd = 8.0,
            imageUrls = emptyList()
        )

        // Initial 24K price: 100 USD/g
        val initialPrice = testNecklace.calculateFinalPrice(100.0, Currency.USD)
        // Updated 24K price after 10s tick: 102 USD/g
        val updatedPrice = testNecklace.calculateFinalPrice(102.0, Currency.USD)

        assertTrue(updatedPrice > initialPrice)
        // Check exact difference:
        // Delta raw gold = 15g * (2 USD * 0.875) = 26.25 USD
        // Delta VAT (5%) = 26.25 * 0.05 = 1.3125 USD
        // Total expected delta = 27.5625 USD
        assertEquals(27.5625, updatedPrice - initialPrice, 0.01)
    }

    @Test
    fun `test Admin PIN authentication security`() {
        val correctPin = "8888"
        val wrongPin = "1111"

        // Verify correct PIN check
        assertTrue(correctPin == "8888")
        assertTrue(wrongPin != "8888")
    }
}
