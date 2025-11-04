package com.example.project_umbrella.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.project_umbrella.model.OrderProduct
import kotlinx.coroutines.flow.Flow

@Dao
interface OrderProductDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertOrderProduct(orderProduct: OrderProduct)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertOrderProducts(orderProducts: List<OrderProduct>)

    @Query("SELECT * from order_products WHERE orderId = :orderId AND productId = :productId LIMIT 1")
    fun getAOrderProduct(orderId: Int, productId: Int): Flow<OrderProduct>

    @Query("SELECT * FROM order_products WHERE orderId = :orderId")
    fun getOrderProducts(orderId: Int): Flow<List<OrderProduct>>

    @Query("SELECT * from order_products ORDER BY orderId, productId")
    fun getAllOrderProducts(): Flow<List<OrderProduct>>

}