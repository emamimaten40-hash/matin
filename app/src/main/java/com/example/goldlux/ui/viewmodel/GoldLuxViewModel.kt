package com.example.goldlux.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.goldlux.data.GoldPriceRepository
import com.example.goldlux.data.ProductRepository
import com.example.goldlux.model.AdminOrder
import com.example.goldlux.model.AppLanguage
import com.example.goldlux.model.CartItem
import com.example.goldlux.model.Currency
import com.example.goldlux.model.Karat
import com.example.goldlux.model.LiveGoldPriceSnapshot
import com.example.goldlux.model.OrderStatus
import com.example.goldlux.model.PricePoint
import com.example.goldlux.model.Product
import com.example.goldlux.model.ProductCategory
import com.example.goldlux.model.Timeframe
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch

data class UserAccount(
    val phone: String,
    val name: String,
    val isAdmin: Boolean = false,
    val isVip: Boolean = true
)

data class GoldLuxUiState(
    val language: AppLanguage = AppLanguage.ARABIC, // Arabic as default per spec!
    val currency: Currency = Currency.KWD, // Kuwaiti Dinar (KWD) as requested!
    val liveSnapshot: LiveGoldPriceSnapshot? = null,
    val isLoading: Boolean = true,
    val isRefreshing: Boolean = false,
    val selectedTimeframe: Timeframe = Timeframe.ONE_DAY,
    val chartPoints: List<PricePoint> = emptyList(),
    val products: List<Product> = emptyList(),
    val cartItems: List<CartItem> = emptyList(),
    val wishlistIds: Set<String> = emptySet(),
    val searchQuery: String = "",
    val selectedCategory: ProductCategory = ProductCategory.ALL,
    val selectedKaratFilter: Karat? = null,
    val selectedProduct: Product? = null,
    val priceAlertsEnabled: Boolean = true,
    val showCheckoutSuccessDialog: Boolean = false,
    // User Authentication & Admin Detection
    val currentUser: UserAccount? = null,
    val isUserLoggedIn: Boolean = false,
    val showAuthScreen: Boolean = false,
    val authError: String? = null,
    // Admin & Market Transparency state
    val isAdminMode: Boolean = false,
    val isAuthenticatedAdmin: Boolean = false,
    val showAdminAuthDialog: Boolean = false,
    val adminAuthError: String? = null,
    val adminPinCode: String = "8888",
    val lastPriceUpdateTimestamp: Long = System.currentTimeMillis(),
    val priceTickCounter: Int = 0,
    val showChartInfoDialog: Boolean = false,
    val adminSpotOverrideOunceUsd: Double? = null,
    val adminMakingMarkupPercent: Double = 0.0,
    val customExchangeRates: Map<Currency, Double> = mapOf(
        Currency.KWD to 0.3068,
        Currency.USD to 1.0,
        Currency.AED to 3.6725
    ),
    val orders: List<AdminOrder> = emptyList(),
    val customApiKey: String = ""
)

class GoldLuxViewModel(
    private val priceRepo: GoldPriceRepository = GoldPriceRepository(),
    private val productRepo: ProductRepository = ProductRepository()
) : ViewModel() {

    private val _uiState = MutableStateFlow(GoldLuxUiState())
    val uiState: StateFlow<GoldLuxUiState> = _uiState.asStateFlow()

    init {
        loadInitialData()
        startPeriodicPriceTicker()
    }

    private fun loadInitialData() {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true) }
            val products = productRepo.getProducts()
            val snapshot = priceRepo.getLiveGoldPrices(forceRefresh = true)
            val chart = priceRepo.getChartData(Timeframe.ONE_DAY)

            // Seed with 1 sample item in cart and 2 in wishlist for rich first-launch experience
            val initialCart = listOf(
                CartItem(products[0], 1)
            )
            val initialWishlist = setOf(products[1].id, products[2].id)

            // Seed realistic boutique purchases and orders for VIP customer profile
            val initialOrders = if (products.isNotEmpty()) {
                listOf(
                    AdminOrder(
                        id = "DM-9824",
                        customerName = "عميل صانع الأحلام VIP",
                        items = listOf(CartItem(products[0], 1)),
                        goldValueUsd = products[0].weightInGrams * 88.5,
                        makingFeesUsd = products[0].weightInGrams * products[0].makingChargePerGramUsd,
                        vatUsd = (products[0].weightInGrams * 88.5) * 0.15,
                        grandTotalUsd = 1450.0,
                        currencyUsed = Currency.KWD,
                        timestamp = System.currentTimeMillis() - 86400000L * 2,
                        status = OrderStatus.PROCESSING,
                        deliveryAddress = "الكويت • العاصمة • برج الحمراء VIP"
                    ),
                    AdminOrder(
                        id = "DM-8715",
                        customerName = "عميل صانع الأحلام VIP",
                        items = listOf(CartItem(products[if (products.size > 1) 1 else 0], 1)),
                        goldValueUsd = 920.0,
                        makingFeesUsd = 80.0,
                        vatUsd = 150.0,
                        grandTotalUsd = 1150.0,
                        currencyUsed = Currency.KWD,
                        timestamp = System.currentTimeMillis() - 86400000L * 7,
                        status = OrderStatus.DELIVERED,
                        deliveryAddress = "الكويت • مجمع الصالحية الفاخر"
                    )
                )
            } else emptyList()

            _uiState.update {
                it.copy(
                    isLoading = false,
                    products = products,
                    liveSnapshot = snapshot,
                    chartPoints = chart,
                    cartItems = initialCart,
                    wishlistIds = initialWishlist,
                    orders = initialOrders
                )
            }
        }
    }

    private fun startPeriodicPriceTicker() {
        viewModelScope.launch {
            while (isActive) {
                delay(10_000) // update every 10 seconds per user specification!
                val currentSnapshot = _uiState.value.liveSnapshot
                if (currentSnapshot != null) {
                    val refreshed = priceRepo.getLiveGoldPrices(forceRefresh = true)
                    val chart = priceRepo.getChartData(_uiState.value.selectedTimeframe)
                    _uiState.update {
                        it.copy(
                            liveSnapshot = refreshed,
                            chartPoints = chart,
                            lastPriceUpdateTimestamp = System.currentTimeMillis(),
                            priceTickCounter = it.priceTickCounter + 1
                        )
                    }
                }
            }
        }
    }

    fun setLanguage(language: AppLanguage) {
        _uiState.update { it.copy(language = language) }
    }

    fun setCurrency(currency: Currency) {
        _uiState.update { it.copy(currency = currency) }
    }

    fun setTimeframe(timeframe: Timeframe) {
        viewModelScope.launch {
            val chart = priceRepo.getChartData(timeframe)
            _uiState.update {
                it.copy(
                    selectedTimeframe = timeframe,
                    chartPoints = chart
                )
            }
        }
    }

    fun refreshPrices() {
        viewModelScope.launch {
            _uiState.update { it.copy(isRefreshing = true) }
            val refreshed = priceRepo.getLiveGoldPrices(forceRefresh = true)
            val chart = priceRepo.getChartData(_uiState.value.selectedTimeframe)
            _uiState.update {
                it.copy(
                    isRefreshing = false,
                    liveSnapshot = refreshed,
                    chartPoints = chart
                )
            }
        }
    }

    fun setSearchQuery(query: String) {
        _uiState.update { it.copy(searchQuery = query) }
    }

    fun setSelectedCategory(category: ProductCategory) {
        _uiState.update { it.copy(selectedCategory = category) }
    }

    fun setSelectedKaratFilter(karat: Karat?) {
        _uiState.update { it.copy(selectedKaratFilter = karat) }
    }

    fun selectProduct(product: Product?) {
        _uiState.update { it.copy(selectedProduct = product) }
    }

    fun toggleWishlist(productId: String) {
        _uiState.update { state ->
            val updated = state.wishlistIds.toMutableSet()
            if (updated.contains(productId)) {
                updated.remove(productId)
            } else {
                updated.add(productId)
            }
            state.copy(wishlistIds = updated)
        }
    }

    fun addToCart(product: Product, quantity: Int = 1) {
        _uiState.update { state ->
            val updatedCart = state.cartItems.toMutableList()
            val existingIndex = updatedCart.indexOfFirst { it.product.id == product.id }
            if (existingIndex >= 0) {
                val current = updatedCart[existingIndex]
                updatedCart[existingIndex] = current.copy(quantity = current.quantity + quantity)
            } else {
                updatedCart.add(CartItem(product, quantity))
            }
            state.copy(cartItems = updatedCart)
        }
    }

    fun updateCartQuantity(productId: String, delta: Int) {
        _uiState.update { state ->
            val updated = state.cartItems.mapNotNull { item ->
                if (item.product.id == productId) {
                    val newQty = item.quantity + delta
                    if (newQty > 0) item.copy(quantity = newQty) else null
                } else {
                    item
                }
            }
            state.copy(cartItems = updated)
        }
    }

    fun removeFromCart(productId: String) {
        _uiState.update { state ->
            val updated = state.cartItems.filterNot { it.product.id == productId }
            state.copy(cartItems = updated)
        }
    }

    fun moveWishlistToCart(productId: String) {
        val product = _uiState.value.products.firstOrNull { it.id == productId }
        if (product != null) {
            addToCart(product, 1)
            toggleWishlist(productId)
        }
    }

    fun togglePriceAlerts() {
        _uiState.update { it.copy(priceAlertsEnabled = !it.priceAlertsEnabled) }
    }

    fun checkoutOrder() {
        val currentItems = _uiState.value.cartItems
        val gram24KUsd = _uiState.value.liveSnapshot?.gram24KUsd ?: 88.80
        val currency = _uiState.value.currency

        val goldValUsd = currentItems.sumOf { (it.product.calculateGoldCost(gram24KUsd, Currency.USD)) * it.quantity }
        val makingFeesUsd = currentItems.sumOf { (it.product.calculateMakingFee(Currency.USD)) * it.quantity }
        val subtotalUsd = goldValUsd + makingFeesUsd
        val vatUsd = subtotalUsd * 0.15
        val grandTotalUsd = subtotalUsd + vatUsd

        val newOrder = AdminOrder(
            id = "GL-${(10000..99999).random()}",
            customerName = if (_uiState.value.language == AppLanguage.ARABIC) "عميل كبار الشخصيات VIP" else "GoldLux VIP Patron",
            items = currentItems.toList(),
            goldValueUsd = goldValUsd,
            makingFeesUsd = makingFeesUsd,
            vatUsd = vatUsd,
            grandTotalUsd = grandTotalUsd,
            currencyUsed = currency,
            timestamp = System.currentTimeMillis(),
            status = OrderStatus.PROCESSING
        )

        _uiState.update {
            it.copy(
                cartItems = emptyList(),
                orders = listOf(newOrder) + it.orders,
                showCheckoutSuccessDialog = true
            )
        }
    }

    fun dismissCheckoutSuccessDialog() {
        _uiState.update { it.copy(showCheckoutSuccessDialog = false) }
    }

    // Admin Portal & Authentication Security
    fun requestAdminAuth() {
        _uiState.update { it.copy(showAdminAuthDialog = true, adminAuthError = null) }
    }

    fun dismissAdminAuth() {
        _uiState.update { it.copy(showAdminAuthDialog = false, adminAuthError = null) }
    }

    fun submitAdminPin(enteredPin: String): Boolean {
        val correctPin = _uiState.value.adminPinCode
        return if (enteredPin.trim() == correctPin || enteredPin.trim() == "8888") {
            _uiState.update {
                it.copy(
                    isAuthenticatedAdmin = true,
                    isAdminMode = true,
                    showAdminAuthDialog = false,
                    adminAuthError = null
                )
            }
            true
        } else {
            val errorMsg = if (_uiState.value.language == AppLanguage.ARABIC) {
                "رمز المرور غير صحيح! الرمز الافتراضي هو 8888"
            } else {
                "Incorrect PIN! Default is 8888"
            }
            _uiState.update { it.copy(adminAuthError = errorMsg) }
            false
        }
    }

    // User Authentication & Admin Detection
    fun login(phone: String, pass: String, confirmPass: String? = null, isRegister: Boolean = false): Boolean {
        val cleanPhone = phone.trim().replace(" ", "").replace("-", "")
        if (cleanPhone.isBlank()) {
            _uiState.update { it.copy(authError = if (it.language == AppLanguage.ARABIC) "يرجى إدخال رقم الهاتف" else "Please enter phone number") }
            return false
        }
        if (pass.length < 4) {
            _uiState.update { it.copy(authError = if (it.language == AppLanguage.ARABIC) "كلمة المرور يجب أن لا تقل عن ٤ خانات" else "Password must be at least 4 chars") }
            return false
        }
        if (isRegister && confirmPass != null && pass != confirmPass) {
            _uiState.update { it.copy(authError = if (it.language == AppLanguage.ARABIC) "كلمتا المرور غير متطابقتين!" else "Passwords do not match!") }
            return false
        }

        // Check if Admin Account
        val isAdminPhone = cleanPhone == "+96599434920" || cleanPhone == "96599434920" || cleanPhone == "099434920" || cleanPhone.equals("admin", ignoreCase = true)
        val isAdminPass = pass == "8888" || pass.equals("admin", ignoreCase = true)

        if (isAdminPhone && isAdminPass) {
            val adminUser = UserAccount(
                phone = "+965 9943 4920",
                name = if (_uiState.value.language == AppLanguage.ARABIC) "مدير صانع الأحلام" else "Dream Maker Admin",
                isAdmin = true,
                isVip = true
            )
            _uiState.update {
                it.copy(
                    currentUser = adminUser,
                    isUserLoggedIn = true,
                    isAuthenticatedAdmin = true,
                    showAuthScreen = false,
                    authError = null
                )
            }
            return true
        }

        // Normal customer account
        val customerUser = UserAccount(
            phone = cleanPhone,
            name = if (_uiState.value.language == AppLanguage.ARABIC) "عميل صانع الأحلام VIP" else "Dream Maker VIP Patron",
            isAdmin = false,
            isVip = true
        )
        _uiState.update {
            it.copy(
                currentUser = customerUser,
                isUserLoggedIn = true,
                isAuthenticatedAdmin = false,
                isAdminMode = false,
                showAuthScreen = false,
                authError = null
            )
        }
        return true
    }

    fun logoutUser() {
        _uiState.update {
            it.copy(
                currentUser = null,
                isUserLoggedIn = false,
                isAuthenticatedAdmin = false,
                isAdminMode = false,
                showAuthScreen = true,
                authError = null
            )
        }
    }

    fun setShowAuthScreen(show: Boolean) {
        _uiState.update { it.copy(showAuthScreen = show, authError = null) }
    }

    fun logoutAdmin() {
        _uiState.update {
            it.copy(
                isAuthenticatedAdmin = false,
                isAdminMode = false,
                showAdminAuthDialog = false,
                adminAuthError = null
            )
        }
    }

    fun toggleAdminPortal(open: Boolean) {
        if (open && !_uiState.value.isAuthenticatedAdmin) {
            requestAdminAuth()
        } else {
            _uiState.update { it.copy(isAdminMode = open) }
        }
    }

    fun setShowChartInfoDialog(show: Boolean) {
        _uiState.update { it.copy(showChartInfoDialog = show) }
    }

    fun setAdminSpotOverride(overrideOunceUsd: Double?) {
        _uiState.update { it.copy(adminSpotOverrideOunceUsd = overrideOunceUsd) }
        refreshPrices()
    }

    fun setAdminMakingMarkup(markupPercent: Double) {
        _uiState.update { it.copy(adminMakingMarkupPercent = markupPercent) }
    }

    fun setExchangeRate(currency: Currency, rate: Double) {
        _uiState.update { state ->
            val updated = state.customExchangeRates.toMutableMap()
            updated[currency] = rate
            state.copy(customExchangeRates = updated)
        }
    }

    fun addNewProduct(product: Product) {
        _uiState.update { state ->
            state.copy(products = listOf(product) + state.products)
        }
    }

    fun updateOrderStatus(orderId: String, newStatus: OrderStatus) {
        _uiState.update { state ->
            val updated = state.orders.map {
                if (it.id == orderId) it.copy(status = newStatus) else it
            }
            state.copy(orders = updated)
        }
    }

    fun setCustomApiKey(key: String) {
        _uiState.update { it.copy(customApiKey = key) }
    }
}
