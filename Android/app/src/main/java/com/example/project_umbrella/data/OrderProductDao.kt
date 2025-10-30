package com.example.project_umbrella.data

import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.project_umbrella.model.OrderProduct
import kotlinx.coroutines.flow.Flow

interface OrderProductDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertOrderProduct(orderProduct: OrderProduct)

    @Query("SELECT * from order_products WHERE orderId = :orderId AND productId = productId LIMIT 1")
    fun getOrderProduct(orderId: Int, productId: Int): Flow<OrderProduct>

    @Query("SELECT * from order_products ORDER BY orderId, productId")
    fun getAllOrderProducts(): Flow<List<OrderProduct>>

}