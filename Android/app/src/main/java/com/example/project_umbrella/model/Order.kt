package com.example.project_umbrella.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.time.LocalDateTime

@Entity(tableName = "orders")
data class Order (
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val customerId: Int? = null,
    val totalAmount: Double = 0.0,
    val orderStatus: String = OrderStatus.OrderStatusPending.orderStatusName,
    val paymentMethod: String = PaymentMethod.PaymentMethodNotSelected.paymentMethodName,
    val comment: String? = null,
    val createdAt: LocalDateTime = LocalDateTime.now()
)

enum class OrderStatus(val orderStatusName: String) {
    OrderStatusPending("Pending"),
    OrderStatusCompleted("Completed"),
    OrderStatusCancelled("Cancelled"),
}
enum class PaymentMethod(val paymentMethodName: String) {
    PaymentMethodCash("Cash"),
    PaymentMethodBankTransfer("Bank Transfer"),
    PaymentMethodDebitCard("Debit Card"),
    PaymentMethodDebt ("Debt"),
    PaymentMethodNotSelected ("Not Selected"),
}