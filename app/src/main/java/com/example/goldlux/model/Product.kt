package com.example.goldlux.model

enum class ProductCategory(val id: String, val nameEn: String, val nameAr: String) {
    ALL("all", "All", "الكل"),
    RINGS("rings", "Rings", "الخواتم"),
    NECKLACES("necklaces", "Necklaces", "القلائد"),
    EARRINGS("earrings", "Earrings", "الأقراط"),
    BRACELETS("bracelets", "Bracelets", "الأساور"),
    SETS("sets", "Sets", "الأطقم"),
    BARS("bars", "Gold Bars", "سبائك ذهب")
}

data class Product(
    val id: String,
    val nameEn: String,
    val nameAr: String,
    val descriptionEn: String,
    val descriptionAr: String,
    val category: ProductCategory,
    val karat: Karat,
    val weightInGrams: Double,
    val makingChargePerGramUsd: Double,
    val imageUrls: List<String>,
    val isFeatured: Boolean = false,
    val certificateNo: String = "GIA-SA-98241",
    val hallmarked: Boolean = true,
    /** Product code shown in the app and sent in WhatsApp orders (e.g. DM-1001) */
    val code: String = ""
) {
    /** Always returns a code: the assigned one, or a stable one derived from the id. */
    val displayCode: String
        get() = code.ifBlank { "DM-" + (1000 + (id.hashCode() and 0x7fffffff) % 9000) }

    /**
     * Calculates the raw gold value based on current 24K price per gram
     */
    fun calculateGoldCost(gram24KUsd: Double, currency: Currency): Double {
        val karatPriceUsd = gram24KUsd * karat.purityFactor
        return (weightInGrams * karatPriceUsd) * currency.usdRate
    }

    /**
     * Craftsmanship / Making fee (أجرة الصناعة)
     */
    fun calculateMakingFee(currency: Currency): Double {
        return (weightInGrams * makingChargePerGramUsd) * currency.usdRate
    }

    /**
     * VAT (ضريبة القيمة المضافة) 0% on pure bullion bars, 5% on jewelry pieces
     */
    fun calculateVat(gram24KUsd: Double, currency: Currency): Double {
        if (category == ProductCategory.BARS) return 0.0
        val subtotal = calculateGoldCost(gram24KUsd, currency) + calculateMakingFee(currency)
        return subtotal * 0.05
    }

    /**
     * Final live recomputed price: (weight * karat price) + making fee + VAT
     */
    fun calculateFinalPrice(gram24KUsd: Double, currency: Currency): Double {
        val goldCost = calculateGoldCost(gram24KUsd, currency)
        val makingFee = calculateMakingFee(currency)
        val vat = calculateVat(gram24KUsd, currency)
        return goldCost + makingFee + vat
    }
}

data class CartItem(
    val product: Product,
    var quantity: Int
)
