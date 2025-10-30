package com.example.project_umbrella.data

import com.example.project_umbrella.model.OrderProduct
import kotlinx.coroutines.flow.Flow

interface OrderProductsRepository {
    // Insert orderProduct in the data source
    suspend fun insertOrderProduct(orderProduct: OrderProduct)

    // Retrieve a orderProduct from the data source
    fun getAOrderProduct(orderId: Int, productId: Int): Flow<OrderProduct>

    // Retrieve an order orderProducts from the data source
    fun getOrderProducts(orderId: Int, productId: Int): Flow<List<OrderProduct>>

    // Retrieve all orderProducts from the data source
    fun getAllOrderProducts(): Flow<List<OrderProduct>>
}