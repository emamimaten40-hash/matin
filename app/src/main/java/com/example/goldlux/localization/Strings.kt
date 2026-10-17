package com.example.goldlux.localization

import com.example.goldlux.model.AppLanguage

object Strings {

    const val WHATSAPP_PHONE_RAW = "+96599434920"
    const val WHATSAPP_PHONE_CLEAN = "96599434920"
    const val WHATSAPP_PHONE_DISPLAY = "+965 9943 4920"

    fun appName(lang: AppLanguage) = if (lang == AppLanguage.ARABIC) "صانع الأحلام" else "Jewelry Dream Maker"
    fun luxuryJewellery(lang: AppLanguage) = if (lang == AppLanguage.ARABIC) "مجوهرات صانع الأحلام" else "Jewelry Dream Maker"

    // Tabs
    fun tabPrices(lang: AppLanguage) = if (lang == AppLanguage.ARABIC) "الأسعار" else "Prices"
    fun tabShop(lang: AppLanguage) = if (lang == AppLanguage.ARABIC) "المتجر" else "Shop"
    fun tabWhatsApp(lang: AppLanguage) = if (lang == AppLanguage.ARABIC) "واتساب" else "WhatsApp"
    fun tabWishlist(lang: AppLanguage) = if (lang == AppLanguage.ARABIC) "المفضلة" else "Wishlist"
    fun tabProfile(lang: AppLanguage) = if (lang == AppLanguage.ARABIC) "الحساب" else "Profile"

    // Home / Prices Screen
    fun greetingMorning(lang: AppLanguage) = if (lang == AppLanguage.ARABIC) "صباح الخير، عميلنا المميز" else "Good Morning, VIP Patron"
    fun greetingEvening(lang: AppLanguage) = if (lang == AppLanguage.ARABIC) "مساء الخير، عميلنا المميز" else "Good Evening, VIP Patron"
    fun liveGoldTracker(lang: AppLanguage) = if (lang == AppLanguage.ARABIC) "مؤشر أسعار الذهب المباشر" else "Live Gold Price Tracker"
    fun gold24kPerGram(lang: AppLanguage) = if (lang == AppLanguage.ARABIC) "سعر غرام الذهب عيار ٢٤" else "24K Gold Rate / Gram"
    fun liveMarketStatus(lang: AppLanguage) = if (lang == AppLanguage.ARABIC) "بث مباشر • بورصة الذهب الكويتية" else "Live Market • Kuwait Gold Exchange"
    fun offlineStatus(lang: AppLanguage) = if (lang == AppLanguage.ARABIC) "وضع عدم الاتصال • محفوظ محلياً" else "Offline Cache Mode"
    fun change24h(lang: AppLanguage) = if (lang == AppLanguage.ARABIC) "التغير خلال ٢٤ ساعة" else "24h Change"
    fun high24h(lang: AppLanguage) = if (lang == AppLanguage.ARABIC) "أعلى سعر:" else "24h High:"
    fun low24h(lang: AppLanguage) = if (lang == AppLanguage.ARABIC) "أدنى سعر:" else "24h Low:"
    fun lastUpdated(lang: AppLanguage) = if (lang == AppLanguage.ARABIC) "آخر تحديث:" else "Last Updated:"
    fun refresh(lang: AppLanguage) = if (lang == AppLanguage.ARABIC) "تحديث" else "Refresh"
    fun liveRatesHeader(lang: AppLanguage) = if (lang == AppLanguage.ARABIC) "الأسعار الفورية والسبائك" else "Live Karats & Bullion Rates"
    fun viewAll(lang: AppLanguage) = if (lang == AppLanguage.ARABIC) "عرض الكل" else "View All"
    fun featuredPieces(lang: AppLanguage) = if (lang == AppLanguage.ARABIC) "عروض مميزة مختارة" else "Featured Luxury Pieces"
    fun shopByCategory(lang: AppLanguage) = if (lang == AppLanguage.ARABIC) "تسوّق حسب الفئة" else "Shop by Category"
    fun interactiveChart(lang: AppLanguage) = if (lang == AppLanguage.ARABIC) "مخطط حركة السعر التفاعلي" else "Interactive Gold Trend Chart"
    fun chartDragHint(lang: AppLanguage) = if (lang == AppLanguage.ARABIC) "المس واسحب على المخطط لمعاينة السعر والتاريخ" else "Drag across chart to inspect price history"

    // Bullion & Coins
    fun tolaTitle(lang: AppLanguage) = if (lang == AppLanguage.ARABIC) "تولة ذهب صانع الأحلام (١١٫٦٦ غرام)" else "Dream Maker Gold Tola (11.66g)"
    fun tolaSubtitle(lang: AppLanguage) = if (lang == AppLanguage.ARABIC) "عيار ٢٤ نقاء ٩٩٩٫٩ كويتي فاخر" else "24K 999.9 Kuwait Fine Gold"
    fun silverBarTitle(lang: AppLanguage) = if (lang == AppLanguage.ARABIC) "سبيكة كيلو فضة (١۰۰۰ غرام)" else "1kg Fine Silver Bar (1000g)"
    fun silverBarSubtitle(lang: AppLanguage) = if (lang == AppLanguage.ARABIC) "فضة نقية ٩٩٩ دمغة كويتية فاخرة" else "999 Fine Silver Kuwait Hallmarked"
    fun goldPoundTitle(lang: AppLanguage) = if (lang == AppLanguage.ARABIC) "ليرة / جنيه ذهب كويتي (٨ غرام)" else "Kuwait Gold Sovereign (8g)"
    fun goldPoundSubtitle(lang: AppLanguage) = if (lang == AppLanguage.ARABIC) "عيار ٢١ دمغة الكويت المعتمدة" else "21K Kuwait Official Hallmark"
    fun bar10gTitle(lang: AppLanguage) = if (lang == AppLanguage.ARABIC) "سبيكة ١٠ غرام صانع الأحلام" else "10g Dream Maker Gold Bar"
    fun bar20gTitle(lang: AppLanguage) = if (lang == AppLanguage.ARABIC) "سبيكة ٢٠ غرام صانع الأحلام" else "20g Dream Maker Gold Bar"
    fun bar50gTitle(lang: AppLanguage) = if (lang == AppLanguage.ARABIC) "سبيكة ٥٠ غرام صانع الأحلام" else "50g Dream Maker Gold Bar"
    fun barPuritySubtitle(lang: AppLanguage) = if (lang == AppLanguage.ARABIC) "عيار ٢٤ نقاء ٩٩٩٫٩ سبيكة استثمارية فاخرة" else "24K 999.9 Fine Investment Ingot"

    // Shop Screen
    fun searchPlaceholder(lang: AppLanguage) = if (lang == AppLanguage.ARABIC) "ابحث عن خواتم، قلائد، أساور ذهبية..." else "Search rings, necklaces, bracelets..."
    fun allCategories(lang: AppLanguage) = if (lang == AppLanguage.ARABIC) "الكل" else "All"
    fun filterKarat(lang: AppLanguage) = if (lang == AppLanguage.ARABIC) "تصفية حسب العيار:" else "Filter by Karat:"
    fun itemsFound(lang: AppLanguage) = if (lang == AppLanguage.ARABIC) "قطعة مجوهرات متوفرة" else "jewelry pieces found"
    fun noProductsFound(lang: AppLanguage) = if (lang == AppLanguage.ARABIC) "لم يتم العثور على قطع تطابق بحثك" else "No pieces match your search"
    fun resetFilters(lang: AppLanguage) = if (lang == AppLanguage.ARABIC) "إعادة تعيين الفلاتر" else "Reset Filters"

    // Product Detail
    fun livePriceBreakdown(lang: AppLanguage) = if (lang == AppLanguage.ARABIC) "تفاصيل حساب السعر المباشر" else "Live Gold Price Breakdown"
    fun livePriceFormulaExplanation(lang: AppLanguage) = if (lang == AppLanguage.ARABIC)
        "(وزن الذهب × سعر العيار الفوري) + أجرة الصياغة + ضريبة القيمة المضافة (١٥٪)"
    else
        "(Gold Weight × Live Karat Rate) + Making Fee + VAT (15%)"
    fun rawGoldValue(lang: AppLanguage) = if (lang == AppLanguage.ARABIC) "قيمة الذهب الخالص" else "Raw Gold Value"
    fun makingCharge(lang: AppLanguage) = if (lang == AppLanguage.ARABIC) "أجرة الصياغة والتصميم" else "Craftsmanship / Making Fee"
    fun vat15(lang: AppLanguage) = if (lang == AppLanguage.ARABIC) "ضريبة القيمة المضافة (١٥٪)" else "VAT (15%)"
    fun finalPrice(lang: AppLanguage) = if (lang == AppLanguage.ARABIC) "السعر النهائي الفوري" else "Final Live Total"
    fun addToCart(lang: AppLanguage) = if (lang == AppLanguage.ARABIC) "إضافة إلى حقيبة التسوق" else "Add to Shopping Bag"
    fun contactWhatsApp(lang: AppLanguage) = if (lang == AppLanguage.ARABIC) "تواصل في واتساب" else "Contact on WhatsApp"
    fun orderViaWhatsApp(lang: AppLanguage) = if (lang == AppLanguage.ARABIC) "تواصل في واتساب" else "Order on WhatsApp"
    fun directCall(lang: AppLanguage) = if (lang == AppLanguage.ARABIC) "اتصال مباشر" else "Direct Call"
    fun phoneLabel(lang: AppLanguage) = if (lang == AppLanguage.ARABIC) "هاتف المتجر: 99434920 (+965)" else "Boutique: +965 99434920"
    fun addedToCartSuccess(lang: AppLanguage) = if (lang == AppLanguage.ARABIC) "تمت إضافة القطعة بنجاح إلى السلة!" else "Added to bag successfully!"
    fun quantity(lang: AppLanguage) = if (lang == AppLanguage.ARABIC) "الكمية" else "Quantity"
    fun specifications(lang: AppLanguage) = if (lang == AppLanguage.ARABIC) "المواصفات والشهادات" else "Specifications & Certification"
    fun hallmarkCertified(lang: AppLanguage) = if (lang == AppLanguage.ARABIC) "دمغة رسمية معتمدة" else "Official Hallmark Certified"
    fun certificateNo(lang: AppLanguage) = if (lang == AppLanguage.ARABIC) "رقم شهادة الفحص:" else "Certificate No:"
    fun lifetimeCleaning(lang: AppLanguage) = if (lang == AppLanguage.ARABIC) "تلميع وصيانة مجانية مدى الحياة" else "Lifetime Complimentary Polishing"
    fun freeInsuredShipping(lang: AppLanguage) = if (lang == AppLanguage.ARABIC) "شحن مؤمن وسريع حتى باب منزلك" else "Free Insured Express Delivery"
    fun weightLabel(lang: AppLanguage) = if (lang == AppLanguage.ARABIC) "الوزن التقريبي:" else "Weight:"
    fun karatLabel(lang: AppLanguage) = if (lang == AppLanguage.ARABIC) "العيار:" else "Karat:"

    // Cart Screen
    fun shoppingCart(lang: AppLanguage) = if (lang == AppLanguage.ARABIC) "حقيبة المشتريات" else "Shopping Bag"
    fun emptyCartTitle(lang: AppLanguage) = if (lang == AppLanguage.ARABIC) "حقيبة المشتريات فارغة" else "Your Bag is Empty"
    fun emptyCartSubtitle(lang: AppLanguage) = if (lang == AppLanguage.ARABIC) "تصفح مجموعتنا الذهبية الفاخرة واختر ما يناسب ذوقك الرفيع" else "Explore our luxury gold collection and select your cherished pieces"
    fun startShopping(lang: AppLanguage) = if (lang == AppLanguage.ARABIC) "استكشف التشكيلة" else "Explore Collection"
    fun orderSummary(lang: AppLanguage) = if (lang == AppLanguage.ARABIC) "ملخص الطلب" else "Order Summary"
    fun subtotal(lang: AppLanguage) = if (lang == AppLanguage.ARABIC) "قيمة المجوهرات والذهب" else "Gold & Jewellery Total"
    fun totalMakingCharges(lang: AppLanguage) = if (lang == AppLanguage.ARABIC) "إجمالي أجور الصياغة" else "Total Making Charges"
    fun totalVat(lang: AppLanguage) = if (lang == AppLanguage.ARABIC) "ضريبة القيمة المضافة (١٥٪)" else "VAT (15%)"
    fun deliveryFee(lang: AppLanguage) = if (lang == AppLanguage.ARABIC) "الشحن المؤمن الممتاز" else "Insured Express Delivery"
    fun free(lang: AppLanguage) = if (lang == AppLanguage.ARABIC) "مجاناً" else "FREE"
    fun grandTotal(lang: AppLanguage) = if (lang == AppLanguage.ARABIC) "المجموع الكلي" else "Grand Total"
    fun proceedToCheckout(lang: AppLanguage) = if (lang == AppLanguage.ARABIC) "متابعة إتمام الشراء" else "Proceed to Checkout"
    fun giftPackaging(lang: AppLanguage) = if (lang == AppLanguage.ARABIC) "علبة هدايا فاخرة مع ختم الشمع الملكي (مجاناً)" else "Complimentary Royal Wax-Sealed Gift Box"
    fun remove(lang: AppLanguage) = if (lang == AppLanguage.ARABIC) "إزالة" else "Remove"

    // Checkout
    fun checkoutTitle(lang: AppLanguage) = if (lang == AppLanguage.ARABIC) "تأكيد الطلب والدفع" else "Order Confirmation"
    fun deliveryAddress(lang: AppLanguage) = if (lang == AppLanguage.ARABIC) "عنوان التوصيل" else "Delivery Address"
    fun defaultAddress(lang: AppLanguage) = if (lang == AppLanguage.ARABIC) "الرياض، حي العليا، برج المملكة، المملكة العربية السعودية" else "Al-Olaya District, Kingdom Tower, Riyadh, Saudi Arabia"
    fun paymentMethod(lang: AppLanguage) = if (lang == AppLanguage.ARABIC) "طريقة الدفع" else "Payment Method"
    fun applePayMada(lang: AppLanguage) = if (lang == AppLanguage.ARABIC) "أبل باي / مدى / فيزا وماستركارد" else "Apple Pay / Mada / Visa & MasterCard"
    fun placeOrder(lang: AppLanguage) = if (lang == AppLanguage.ARABIC) "تأكيد وإتمام الشراء" else "Confirm & Pay Order"
    fun orderSuccessTitle(lang: AppLanguage) = if (lang == AppLanguage.ARABIC) "تهانينا! تم تأكيد طلبك بنجاح" else "Congratulations! Order Placed"
    fun orderSuccessDesc(lang: AppLanguage) = if (lang == AppLanguage.ARABIC)
        "تم استلام طلبك رقم #GL-78923 بنجاح. سيتم تحضير طردك الفاخر وشحنه بسيارة مصفحة مؤمنة."
    else
        "Your order #GL-78923 has been secured. Your pieces will be packaged in a royal box and dispatched via insured express."
    fun backToHome(lang: AppLanguage) = if (lang == AppLanguage.ARABIC) "العودة للرئيسية" else "Back to Home"

    // Wishlist Screen
    fun wishlistTitle(lang: AppLanguage) = if (lang == AppLanguage.ARABIC) "قائمة المفضلة" else "My Wishlist"
    fun emptyWishlistTitle(lang: AppLanguage) = if (lang == AppLanguage.ARABIC) "قائمة المفضلة فارغة" else "No Wishlist Items Yet"
    fun emptyWishlistSubtitle(lang: AppLanguage) = if (lang == AppLanguage.ARABIC) "احفظ القطع التي تنال إعجابك للرجوع إليها ومتابعة أسعارها" else "Save pieces you adore to track their live prices and order later"
    fun moveToBag(lang: AppLanguage) = if (lang == AppLanguage.ARABIC) "نقل إلى السلة" else "Move to Bag"

    // Profile Screen
    fun profileTitle(lang: AppLanguage) = if (lang == AppLanguage.ARABIC) "الملف الشخصي والإعدادات" else "Profile & Settings"
    fun vipTierTitle(lang: AppLanguage) = if (lang == AppLanguage.ARABIC) "عضوية صانع الأحلام الملكية" else "Dream Maker Royal Tier"
    fun vipMemberId(lang: AppLanguage) = if (lang == AppLanguage.ARABIC) "رقم العضوية: DM-٩٩٢٤٠" else "Member ID: DM-99240"
    fun vipBenefit(lang: AppLanguage) = if (lang == AppLanguage.ARABIC) "خصم ١٥٪ على أجور الصياغة وشحن مجاني مدى الحياة" else "15% off making fees & lifetime free insured delivery"
    fun preferencesHeader(lang: AppLanguage) = if (lang == AppLanguage.ARABIC) "تفضيلات التطبيق" else "App Preferences"
    fun languageSetting(lang: AppLanguage) = if (lang == AppLanguage.ARABIC) "لغة التطبيق" else "App Language"
    fun currencySetting(lang: AppLanguage) = if (lang == AppLanguage.ARABIC) "عملة الأسعار" else "Display Currency"
    fun priceAlerts(lang: AppLanguage) = if (lang == AppLanguage.ARABIC) "تنبيهات تحرك سعر الذهب" else "Gold Price Market Alerts"
    fun priceAlertsDesc(lang: AppLanguage) = if (lang == AppLanguage.ARABIC) "إشعار فوري عند تغير سعر الذهب بنسبة تتجاوز ١٪" else "Instant notification if 24K gold shifts by >1%"
    fun boutiquesHeader(lang: AppLanguage) = if (lang == AppLanguage.ARABIC) "فروعنا وبوتيكاتنا الحصرية" else "Exclusive Boutiques"
    fun boutiquesList(lang: AppLanguage) = if (lang == AppLanguage.ARABIC) "الكويت: العاصمة • برج الحمراء • مجمع الصالحية • دبي: دبي مول" else "Kuwait: Kuwait City • Al Hamra Tower • Al Salhiya Complex • Dubai: The Dubai Mall"
    fun conciergeSupport(lang: AppLanguage) = if (lang == AppLanguage.ARABIC) "المستشار الشخصي وخدمة العملاء (+965 9943 4920)" else "VIP Concierge & WhatsApp (+965 9943 4920)"
    fun callConcierge(lang: AppLanguage) = if (lang == AppLanguage.ARABIC) "اتصل بمجوهرات صانع الأحلام" else "Call Dream Maker Luxury"
    fun purityGuarantee(lang: AppLanguage) = if (lang == AppLanguage.ARABIC) "ضمان نقاء الذهب ١٠٠٪ ودمغة رسمية معتمدة من وزارة التجارة والصناعة الكويتية" else "100% Certified Gold Purity & Official Kuwait Commerce Hallmark"
    fun appVersion(lang: AppLanguage) = if (lang == AppLanguage.ARABIC) "إصدار صانع الأحلام ٣٫٠" else "Dream Maker Edition 3.0"

    // Admin Portal & Market Transparency
    fun adminPortalTitle(lang: AppLanguage) = if (lang == AppLanguage.ARABIC) "بوابة إدارة صانع الأحلام" else "Dream Maker Admin Portal"
    fun adminPortalDesc(lang: AppLanguage) = if (lang == AppLanguage.ARABIC) "إدارة الأسعار المباشرة، هوامش الربح، الكتالوج والطلبات" else "Live pricing control, margins, inventory & orders"
    fun openAdminPortal(lang: AppLanguage) = if (lang == AppLanguage.ARABIC) "فتح لوحة الإدارة والتحكم" else "Open Management Portal"
    fun backToClientApp(lang: AppLanguage) = if (lang == AppLanguage.ARABIC) "العودة لتطبيق المتجر" else "Return to Boutique App"
    fun tabAdminPricing(lang: AppLanguage) = if (lang == AppLanguage.ARABIC) "التسعير والعملات" else "Pricing & Rates"
    fun tabAdminProducts(lang: AppLanguage) = if (lang == AppLanguage.ARABIC) "المنتجات والمخزون" else "Inventory"
    fun tabAdminOrders(lang: AppLanguage) = if (lang == AppLanguage.ARABIC) "الطلبات والفواتير" else "Orders & Invoices"
    fun tabAdminApi(lang: AppLanguage) = if (lang == AppLanguage.ARABIC) "فید السوق والربط" else "Market Feed & API"
    fun spotOverrideLabel(lang: AppLanguage) = if (lang == AppLanguage.ARABIC) "تعديل سعر الأونصة يدوياً (USD):" else "Manual Spot Gold Ounce (USD):"
    fun makingMarkupLabel(lang: AppLanguage) = if (lang == AppLanguage.ARABIC) "هامش أجور الصياغة الإضافي:" else "Making Fee Global Margin:"
    fun resetToLiveMarket(lang: AppLanguage) = if (lang == AppLanguage.ARABIC) "إعادة ضبط لسعر البورصة المباشر" else "Reset to Live Market Feed"
    fun applyChanges(lang: AppLanguage) = if (lang == AppLanguage.ARABIC) "حفظ وتطبيق التغييرات" else "Save & Apply Changes"
    fun changesAppliedToast(lang: AppLanguage) = if (lang == AppLanguage.ARABIC) "تم تحديث الأسعار والمخزون بنجاح!" else "Prices & Inventory updated!"
    fun addNewProductTitle(lang: AppLanguage) = if (lang == AppLanguage.ARABIC) "إضافة قطعة ذهبية جديدة للكتالوج" else "Add New Jewellery Piece"
    fun pieceNameAr(lang: AppLanguage) = if (lang == AppLanguage.ARABIC) "اسم القطعة (بالعربية)" else "Item Name (Arabic)"
    fun pieceNameEn(lang: AppLanguage) = if (lang == AppLanguage.ARABIC) "اسم القطعة (بالإنجليزية)" else "Item Name (English)"
    fun pieceWeight(lang: AppLanguage) = if (lang == AppLanguage.ARABIC) "الوزن بالغرام" else "Weight in Grams"
    fun pieceMaking(lang: AppLanguage) = if (lang == AppLanguage.ARABIC) "أجرة الصياغة للغرام (USD)" else "Making Fee / Gram (USD)"
    fun pieceKarat(lang: AppLanguage) = if (lang == AppLanguage.ARABIC) "العيار" else "Karat"
    fun savePiece(lang: AppLanguage) = if (lang == AppLanguage.ARABIC) "حفظ وإضافة للمتجر" else "Save & Add to Shop"
    fun cancel(lang: AppLanguage) = if (lang == AppLanguage.ARABIC) "إلغاء" else "Cancel"
    fun orderStatusProcessing(lang: AppLanguage) = if (lang == AppLanguage.ARABIC) "قيد التحضير والتجهيز" else "Processing"
    fun orderStatusDispatched(lang: AppLanguage) = if (lang == AppLanguage.ARABIC) "في الشحن المصفح المؤمن" else "Dispatched Armored"
    fun orderStatusDelivered(lang: AppLanguage) = if (lang == AppLanguage.ARABIC) "تم التسليم بنجاح" else "Delivered"
    fun noOrdersYet(lang: AppLanguage) = if (lang == AppLanguage.ARABIC) "لا توجد طلبات جديدة حالياً" else "No orders recorded yet"
    fun chartRealityHeader(lang: AppLanguage) = if (lang == AppLanguage.ARABIC) "هل المخطط والأسعار حقيقية؟" else "Is the Chart & Pricing Real?"
    fun chartRealityExplanation(lang: AppLanguage) = if (lang == AppLanguage.ARABIC)
        "نعم! الأسعار مربوطة مباشرة بمؤشر الذهب العالمي (LBMA Spot) وبورصة تداول الذهب في الكويت. يتم تحديث سعر غرام عيار ٢٤ بدقة متناهية كل ١٠ ثوانٍ واحتساب باقي العيارات وفق نسب النقاء المعتمدة (٢٢ = ٩١٫٦٪ ، ٢١ = ٨٧٫٥٪ ، ١٨ = ٧٥٪) مع تسعير متزامن بالدينار الكويتي (د.ك)، الدولار ($) والدرهم الإماراتي (د.إ)."
    else
        "Yes! Prices are directly synchronized with Kuwait Gold Exchange and global LBMA spot gold. 24K, 22K, 21K, and 18K rates reflect live Kuwait market trading updated every 10 seconds in Kuwaiti Dinar (KWD), US Dollar (USD), and UAE Dirham (AED)."
    fun liveApiSourceLabel(lang: AppLanguage) = if (lang == AppLanguage.ARABIC) "مصدر البيانات المباشر:" else "Live Market Data Provider:"
    fun apiKeyPlaceholder(lang: AppLanguage) = if (lang == AppLanguage.ARABIC) "أدخل مفتاح API (اختياري للربط المخصص)..." else "Enter API Key (optional custom feed)..."
    fun testConnection(lang: AppLanguage) = if (lang == AppLanguage.ARABIC) "اختبار الاتصال بالسوق" else "Test Market Connection"

    // 10s Live Updates & Admin Authentication Isolation
    fun live10sSync(lang: AppLanguage) = if (lang == AppLanguage.ARABIC) "تحديث مباشر كل ١٠ ثوانٍ" else "Live 10s Price Update"
    fun live10sChart(lang: AppLanguage) = if (lang == AppLanguage.ARABIC) "مخطط لحظي يتحدث كل ١٠ ثوانٍ" else "Real-time 10s Chart Sync"
    fun adminLoginTitle(lang: AppLanguage) = if (lang == AppLanguage.ARABIC) "تسجيل دخول إدارة صانع الأحلام" else "Dream Maker Admin Access"
    fun adminLoginDesc(lang: AppLanguage) = if (lang == AppLanguage.ARABIC) "أدخل رمز المرور السري الخاص بإدارة المتجر للوصول للوحة التحكم:" else "Enter the master PIN to access the boutique management portal:"
    fun adminPinPlaceholder(lang: AppLanguage) = if (lang == AppLanguage.ARABIC) "رمز المرور (الافتراضي: 8888)" else "Admin PIN (Default: 8888)"
    fun adminUnlock(lang: AppLanguage) = if (lang == AppLanguage.ARABIC) "دخول الإدارة" else "Unlock Portal"
    fun adminLogout(lang: AppLanguage) = if (lang == AppLanguage.ARABIC) "قفل لوحة الإدارة" else "Lock & Exit Admin"
    fun adminModeActive(lang: AppLanguage) = if (lang == AppLanguage.ARABIC) "👑 حساب الإدارة مفعّل" else "👑 Master Admin Active"
    fun adminPinIncorrect(lang: AppLanguage) = if (lang == AppLanguage.ARABIC) "رمز المرور غير صحيح! الرمز الافتراضي: 8888" else "Incorrect PIN! Default is 8888"
    fun staffAccess(lang: AppLanguage) = if (lang == AppLanguage.ARABIC) "دخول الإدارة والمشرفين" else "Staff / Admin Access"
}
