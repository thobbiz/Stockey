package com.example.project_umbrella.data

import com.example.project_umbrella.model.Order
import com.example.project_umbrella.model.OrderProduct
import com.example.project_umbrella.model.OrderWithProducts
import kotlinx.coroutines.flow.Flow

interface OrdersRepository {
    // Insert order in the data source
    suspend fun insertOrder(order: Order): Int

    // Insert orderProducts in the data source
    suspend fun insertOrderProducts(orderProducts: List<OrderProduct>)

    // Retrieve a order with orderProducts from the data source
    fun getOrderWithProducts(orderId: Int): OrderWithProducts?

    // Retrieve all orders from the data source
    fun getAllOrders(): Flow<List<OrderWithProducts>>

    // Update order total amount
    suspend fun updateOrderTotal(orderId: Int, total: Double)

    // Calculate order total amount
    suspend fun calculateOrderTotal(orderId: Int): Double?

    // Delete order from the data source
    suspend fun deleteOrder(orderId: Int)


    // Update order status in the data source
    suspend fun updateOrderStatus(orderStatus: String, orderId: Int)

    // Update order payment method in the data source
    suspend fun updatePaymentMethod(paymentMethod: String, orderId: Int)
}