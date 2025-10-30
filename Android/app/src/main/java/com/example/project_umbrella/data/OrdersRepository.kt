package com.example.project_umbrella.data

import com.example.project_umbrella.model.Order
import com.example.project_umbrella.model.OrderStatus
import com.example.project_umbrella.model.PaymentMethod
import kotlinx.coroutines.flow.Flow

interface OrdersRepository {
    // Insert order in the data source
    suspend fun insertOrder(order: Order)

    // Delete order from the data source
    suspend fun deleteOrder(orderId: Int)

    // Retrieve a order form the data source
    fun getOrder(orderId: Int): Flow<Order?>

    // Retrieve all orders from the data source
    fun getAllOrders(): Flow<List<Order>>

    // Update order status in the data source
    suspend fun updateOrderStatus(orderStatus: OrderStatus, orderId: Int)

    // Update order payment method in the data source
    suspend fun updatePaymentMethod(paymentMethod: PaymentMethod, orderId: Int)
}