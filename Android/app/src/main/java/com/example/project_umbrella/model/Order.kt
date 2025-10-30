package com.example.project_umbrella.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.time.LocalDateTime

@Entity(tableName = "orders")
data class Order (
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val customerId: Int,
    val totalAmount: Double,
    val orderStatus: OrderStatus,
    val paymentMethod: PaymentMethod,
    val comment: String? = null,
    val createdAt: LocalDateTime = LocalDateTime.now()
)

enum class OrderStatus(val orderStatusName: String) {
    OrderStatusPending("pending"),
    OrderStatusCompleted("completed"),
    OrderStatusCancelled("cancelled"),
}
enum class PaymentMethod(val paymentMethodName: String) {
    PaymentMethodCash("Cash"),
    PaymentMethodBankTransfer("Bank Transfer"),
    PaymentMethodDebitCard("Debit Card"),
    PaymentMethodDebt ("Debt"),
    PaymentMethodNotSelected ("Not Selected"),
}