package com.example.goldlux.util

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.widget.Toast
import com.example.goldlux.localization.Localization
import com.example.goldlux.model.Currency
import com.example.goldlux.model.Product

object ContactUtils {
    const val BOUTIQUE_PHONE = "+96599434920"
    const val BOUTIQUE_PHONE_RAW = "96599434920"

    fun openWhatsApp(
        context: Context,
        product: Product? = null,
        livePrice: Double? = null,
        currency: Currency? = null,
        isArabic: Boolean = true
    ) {
        try {
            val message = if (product != null && livePrice != null && currency != null) {
                if (isArabic) {
                    "السلام عليكم، أود الاستفسار عن شراء القطعة:\n*${product.nameAr}*\nالعيار: ${product.karat.nameAr} (الوزن: ${product.weightInGrams} غرام)\nالسعر المباشر: ${Localization.formatPrice(livePrice, currency, true)}\nمن مجوهرات صانع الأحلام الكويت."
                } else {
                    "Hello, I would like to inquire about purchasing:\n*${product.nameEn}*\nKarat: ${product.karat.nameEn} (Weight: ${product.weightInGrams}g)\nLive Price: ${Localization.formatPrice(livePrice, currency, false)}\nfrom Jewelry Dream Maker Kuwait."
                }
            } else {
                if (isArabic) {
                    "السلام عليكم، أود التواصل مع مجوهرات صانع الأحلام للاستفسار عن القطع الذهبية والأسعار المباشرة."
                } else {
                    "Hello, I would like to contact Jewelry Dream Maker regarding gold jewellery pieces and live rates."
                }
            }

            val encodedMsg = Uri.encode(message)
            val url = "https://api.whatsapp.com/send?phone=$BOUTIQUE_PHONE_RAW&text=$encodedMsg"
            val intent = Intent(Intent.ACTION_VIEW).apply {
                data = Uri.parse(url)
                flags = Intent.FLAG_ACTIVITY_NEW_TASK
            }
            context.startActivity(intent)
        } catch (e: Exception) {
            Toast.makeText(
                context,
                if (isArabic) "واتساب غير متوفر على الجهاز" else "WhatsApp not found",
                Toast.LENGTH_SHORT
            ).show()
        }
    }

    fun callBoutique(context: Context) {
        try {
            val intent = Intent(Intent.ACTION_DIAL).apply {
                data = Uri.parse("tel:$BOUTIQUE_PHONE")
                flags = Intent.FLAG_ACTIVITY_NEW_TASK
            }
            context.startActivity(intent)
        } catch (e: Exception) {
            Toast.makeText(context, "تعذر فتح لوحة الاتصال", Toast.LENGTH_SHORT).show()
        }
    }

    fun openWhatsAppOrderInquiry(context: Context, orderId: String, isArabic: Boolean = true) {
        try {
            val message = if (isArabic) {
                "السلام عليكم مجوهرات صانع الأحلام، أود الاستفسار عن حالة ومتابعة طلبي رقم: #$orderId"
            } else {
                "Hello Jewelry Dream Maker, I would like to track my order #$orderId"
            }
            val encodedMsg = Uri.encode(message)
            val url = "https://api.whatsapp.com/send?phone=$BOUTIQUE_PHONE_RAW&text=$encodedMsg"
            val intent = Intent(Intent.ACTION_VIEW).apply {
                data = Uri.parse(url)
                flags = Intent.FLAG_ACTIVITY_NEW_TASK
            }
            context.startActivity(intent)
        } catch (e: Exception) {
            Toast.makeText(context, if (isArabic) "تعذر فتح واتساب" else "Could not open WhatsApp", Toast.LENGTH_SHORT).show()
        }
    }
}
