package com.example.goldlux.model

enum class OrderStatus {
    PROCESSING,
    DISPATCHED,
    DELIVERED
}

data class AdminOrder(
    val id: String,
    val customerName: String,
    val items: List<CartItem>,
    val goldValueUsd: Double,
    val makingFeesUsd: Double,
    val vatUsd: Double,
    val grandTotalUsd: Double,
    val currencyUsed: Currency,
    val timestamp: Long,
    val status: OrderStatus = OrderStatus.PROCESSING,
    val deliveryAddress: String = "Al-Olaya, Kingdom Tower, VIP Suite #402"
)
