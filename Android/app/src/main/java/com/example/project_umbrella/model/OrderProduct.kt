package com.example.project_umbrella.model

import androidx.room.Entity

@Entity(tableName = "order_products")
data class OrderProduct (
    val orderId: Int,
    val productId: Int,
    val price: Double,
    val quantity: Int,
)