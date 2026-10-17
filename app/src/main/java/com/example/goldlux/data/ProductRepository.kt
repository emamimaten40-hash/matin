package com.example.goldlux.data

import com.example.goldlux.model.Karat
import com.example.goldlux.model.Product
import com.example.goldlux.model.ProductCategory

class ProductRepository {

    fun getProducts(): List<Product> {
        return listOf(
            Product(
                id = "p1_royal_ring",
                code = "DM-1001",
                nameEn = "Royal Solitaire Sovereign Ring",
                nameAr = "خاتم السوليتير الملكي الذهبي",
                descriptionEn = "Handcrafted in certified 21K gold featuring intricate royal filigree engravings and a brilliant centerpiece cut. Hallmarked for everlasting heritage.",
                descriptionAr = "مصنوع يدوياً من الذهب الخالص عيار ٢١ بتطريزات ونقوش ملكية دقيقة وقطعة مركزية براقة. مدموغ وموثق ليكون إرثاً خالداً.",
                category = ProductCategory.RINGS,
                karat = Karat.K21,
                weightInGrams = 5.80,
                makingChargePerGramUsd = 12.0,
                imageUrls = listOf(
                    "https://images.unsplash.com/photo-1605100804763-247f67b3557e?w=800&auto=format&fit=crop&q=80",
                    "https://images.unsplash.com/photo-1603561591411-07134e71a2a9?w=800&auto=format&fit=crop&q=80"
                ),
                isFeatured = true,
                certificateNo = "GIA-SA-88192"
            ),
            Product(
                id = "p2_monarch_necklace",
                code = "DM-1002",
                nameEn = "Monarch Arabesque Gold Choker",
                nameAr = "عقد المونارك الأرابيسك الفاخر",
                descriptionEn = "Exquisite 22K pure yellow gold choker inspired by Andalusian arabesque palace architecture with cascading golden droplets.",
                descriptionAr = "طوق فاخر من الذهب الأصفر النقي عيار ٢٢ مستوحى من زخارف ونقوش القصور الأندلسية مع قطرات ذهبية متدلية بانسيابية.",
                category = ProductCategory.NECKLACES,
                karat = Karat.K22,
                weightInGrams = 18.40,
                makingChargePerGramUsd = 15.0,
                imageUrls = listOf(
                    "https://images.unsplash.com/photo-1599643478518-a784e5dc4c8f?w=800&auto=format&fit=crop&q=80",
                    "https://images.unsplash.com/photo-1515562141207-7a88fb7ce338?w=800&auto=format&fit=crop&q=80"
                ),
                isFeatured = true,
                certificateNo = "GIA-UAE-77401"
            ),
            Product(
                id = "p3_heritage_bangles",
                code = "DM-1003",
                nameEn = "Heritage Dubai Floral Bangle",
                nameAr = "سوار دبي التراثي المنقوش",
                descriptionEn = "Solid 21K traditional gold bangle with double safety clasp and laser-precision geometric patterns reminiscent of Arabian heritage.",
                descriptionAr = "سوار صلب من الذهب التقليدي عيار ٢١ بقفل أمان مزدوج وزخارف هندسية مستوحاة من عراقة التراث الخليجي الأصيل.",
                category = ProductCategory.BRACELETS,
                karat = Karat.K21,
                weightInGrams = 14.20,
                makingChargePerGramUsd = 11.0,
                imageUrls = listOf(
                    "https://images.unsplash.com/photo-1611591475152-478311396043?w=800&auto=format&fit=crop&q=80",
                    "https://images.unsplash.com/photo-1573408301185-9146fe634ad0?w=800&auto=format&fit=crop&q=80"
                ),
                isFeatured = true,
                certificateNo = "GIA-SA-66120"
            ),
            Product(
                id = "p4_celestial_earrings",
                code = "DM-1004",
                nameEn = "Celestial Crescent Gold Earrings",
                nameAr = "أقراط الهلال السماوي الذهبية",
                descriptionEn = "Dainty and graceful 18K polished gold drop earrings featuring a glowing celestial crescent motif with delicate dangling chains.",
                descriptionAr = "أقراط ناعمة وجذابة من الذهب اللامع عيار ١٨ بتصميم الهلال المتألق وسلاسل دقيقة تضفي بريقاً ساحراً مع كل حركة.",
                category = ProductCategory.EARRINGS,
                karat = Karat.K18,
                weightInGrams = 4.60,
                makingChargePerGramUsd = 14.5,
                imageUrls = listOf(
                    "https://images.unsplash.com/photo-1630019852942-f89202989a59?w=800&auto=format&fit=crop&q=80",
                    "https://images.unsplash.com/photo-1535632066927-ab7c9ab60908?w=800&auto=format&fit=crop&q=80"
                ),
                isFeatured = true,
                certificateNo = "GIA-SA-55912"
            ),
            Product(
                id = "p5_sultan_bridal_set",
                code = "DM-1005",
                nameEn = "Sultan Bridal 4-Piece Grand Set",
                nameAr = "طقم العروس السلطاني الملكي (٤ قطع)",
                descriptionEn = "A magnificent bridal ensemble including royal choker necklace, matching chandelier earrings, cuff bracelet, and majestic ring in 21K gold.",
                descriptionAr = "طقم زفاف ملكي فاخر يتضمن عقداً ملكياً، أقراط ثريا متناسقة، سوار كف عريض، وخاتماً مهيباً من الذهب عيار ٢١ المرصع.",
                category = ProductCategory.SETS,
                karat = Karat.K21,
                weightInGrams = 46.50,
                makingChargePerGramUsd = 16.0,
                imageUrls = listOf(
                    "https://images.unsplash.com/photo-1506630448388-4e683c67ddb0?w=800&auto=format&fit=crop&q=80",
                    "https://images.unsplash.com/photo-1599643478518-a784e5dc4c8f?w=800&auto=format&fit=crop&q=80"
                ),
                isFeatured = true,
                certificateNo = "GIA-SA-99014"
            ),
            Product(
                id = "p6_valcambi_bar_20g",
                code = "DM-1006",
                nameEn = "Swiss 20g 24K Fine Gold Bar",
                nameAr = "سبيكة سويسرية ٢٠ غرام عيار ٢٤",
                descriptionEn = "Pure 999.9 fine gold investment ingot sealed in a tamper-evident serial-numbered security assay card.",
                descriptionAr = "سبيكة استثمارية نقية من الذهب الخالص عيار ٢٤ بنقاء ٩٩٩٫٩ مغلفة ببطاقة أمان معتمدة ومختومة بالرقم التسلسلي الدولي.",
                category = ProductCategory.BARS,
                karat = Karat.K24,
                weightInGrams = 20.00,
                makingChargePerGramUsd = 2.5,
                imageUrls = listOf(
                    "https://images.unsplash.com/photo-1610375461246-83df859d849d?w=800&auto=format&fit=crop&q=80"
                ),
                isFeatured = true,
                certificateNo = "CH-ASSAY-2009"
            ),
            Product(
                id = "p7_infinity_eternity_ring",
                code = "DM-1007",
                nameEn = "Infinity Wave Gold Band",
                nameAr = "محبس تموج اللانهاية الذهبي",
                descriptionEn = "Modern undulating silhouette in satin and mirror-finish 18K gold. Designed for comfortable daily luxury wear.",
                descriptionAr = "تصميم عصري بتموجات اللانهاية بانعكاسات ساتان ومرآة من الذهب عيار ١٨ صُمم للارتداء اليومي الفاخر والمريح.",
                category = ProductCategory.RINGS,
                karat = Karat.K18,
                weightInGrams = 3.90,
                makingChargePerGramUsd = 13.0,
                imageUrls = listOf(
                    "https://images.unsplash.com/photo-1603561591411-07134e71a2a9?w=800&auto=format&fit=crop&q=80"
                ),
                isFeatured = false,
                certificateNo = "GIA-SA-33418"
            ),
            Product(
                id = "p8_palm_oasis_pendant",
                code = "DM-1008",
                nameEn = "Palm Oasis 21K Gold Pendant",
                nameAr = "قلادة واحة النخيل الذهبية عيار ٢١",
                descriptionEn = "Iconic Arabian date palm pendant crafted in warm 21K yellow gold with a sparkling diamond-cut textured trunk and delicate fronds.",
                descriptionAr = "قلادة نخلة عربية أصيلة مصنوعة من الذهب الأصفر الدافئ عيار ٢١ ببريق ماسي على الجذع وسعف منحوت بدقة فائقة.",
                category = ProductCategory.NECKLACES,
                karat = Karat.K21,
                weightInGrams = 7.30,
                makingChargePerGramUsd = 12.5,
                imageUrls = listOf(
                    "https://images.unsplash.com/photo-1599643477877-530eb83abc8e?w=800&auto=format&fit=crop&q=80"
                ),
                isFeatured = false,
                certificateNo = "GIA-SA-44182"
            ),
            Product(
                id = "p9_tassel_chandelier_earrings",
                code = "DM-1009",
                nameEn = "Royal Tassel Cascade Earrings",
                nameAr = "أقراط الشراشيب الملكية الفاخرة",
                descriptionEn = "Statement evening chandeliers in 21K gold featuring micro-mesh golden tassels that flow with graceful luminescence.",
                descriptionAr = "أقراط سهرة فارهة من الذهب عيار ٢١ بشراشيب ذهبية ناعمة تنسدل ببريق ملفت وجاذبية ساحرة للمناسبات الكبرى.",
                category = ProductCategory.EARRINGS,
                karat = Karat.K21,
                weightInGrams = 9.80,
                makingChargePerGramUsd = 15.0,
                imageUrls = listOf(
                    "https://images.unsplash.com/photo-1630019852942-f89202989a59?w=800&auto=format&fit=crop&q=80"
                ),
                isFeatured = false,
                certificateNo = "GIA-SA-22871"
            ),
            Product(
                id = "p10_tennis_link_bracelet",
                code = "DM-1010",
                nameEn = "Golden Riviera Tennis Bracelet",
                nameAr = "إسوارة الريفيرا الذهبية اللامعة",
                descriptionEn = "Classic tennis link bracelet articulated in rich 18K yellow gold with seamless flex-joints and security latch.",
                descriptionAr = "إسوارة ريفيرا كلاسيكية بحلقات متصلة من الذهب الأصفر الغني عيار ١٨ بمفاصل مرنة وقفل حماية غير مرئي.",
                category = ProductCategory.BRACELETS,
                karat = Karat.K18,
                weightInGrams = 11.50,
                makingChargePerGramUsd = 13.5,
                imageUrls = listOf(
                    "https://images.unsplash.com/photo-1611591475152-478311396043?w=800&auto=format&fit=crop&q=80"
                ),
                isFeatured = false,
                certificateNo = "GIA-SA-11784"
            ),
            Product(
                id = "p11_pamp_bar_50g",
                code = "DM-1011",
                nameEn = "PAMP Suisse 50g Fortuna 24K Bar",
                nameAr = "سبيكة بامب فورتونا ٥٠ غرام عيار ٢٤",
                descriptionEn = "Internationally celebrated Lady Fortuna 50g bullion bar in 999.9 purity, individually serialized with official Swiss hallmark.",
                descriptionAr = "سبيكة ليدي فورتونا الشهيرة عالمياً بوزن ٥٠ غرام نقاء ٩٩٩٫٩ مرقمة تسلسلياً ومختومة رسمياً من مصافي سويسرا المعتمدة.",
                category = ProductCategory.BARS,
                karat = Karat.K24,
                weightInGrams = 50.00,
                makingChargePerGramUsd = 1.8,
                imageUrls = listOf(
                    "https://images.unsplash.com/photo-1610375461246-83df859d849d?w=800&auto=format&fit=crop&q=80"
                ),
                isFeatured = false,
                certificateNo = "CH-FORTUNA-509"
            ),
            Product(
                id = "p12_sovereign_ring_men",
                code = "DM-1012",
                nameEn = "Emir Signet Gold Ring",
                nameAr = "خاتم الأمير المنقوش للرجال",
                descriptionEn = "Heavyweight masculine signet ring in solid 18K yellow gold with hand-chiseled brushed satin bezel.",
                descriptionAr = "خاتم أميري فخم ثقيل الوزن من الذهب الخالص عيار ١٨ بحافة ساتانية مشطوفة يدوياً وأناقة رجالية راقية.",
                category = ProductCategory.RINGS,
                karat = Karat.K18,
                weightInGrams = 12.30,
                makingChargePerGramUsd = 10.5,
                imageUrls = listOf(
                    "https://images.unsplash.com/photo-1605100804763-247f67b3557e?w=800&auto=format&fit=crop&q=80"
                ),
                isFeatured = false,
                certificateNo = "GIA-SA-77219"
            )
        )
    }
}
