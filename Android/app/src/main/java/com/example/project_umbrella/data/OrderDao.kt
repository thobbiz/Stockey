package com.example.project_umbrella.data

import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.project_umbrella.model.Order
import com.example.project_umbrella.model.OrderStatus
import com.example.project_umbrella.model.PaymentMethod
import kotlinx.coroutines.flow.Flow

interface OrderDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertOrder(order: Order)

    @Delete
    @Query("DELETE FROM orders WHERE id = :orderId")
    suspend fun deleteOrder(orderId: Int)

    @Query("SELECT * from orders WHERE id = :orderId LIMIT 1")
    fun getOrder(orderId: Int): Flow<Order>

    @Query("SELECT * from orders ORDER BY id")
    fun getAllOrders(): Flow<List<Order>>

    @Update
    @Query("UPDATE orders SET orderStatus = :orderStatus WHERE id = :orderId")
    suspend fun updateOrderStatus(orderStatus: OrderStatus, orderId: Int)

    @Update
    @Query("UPDATE orders SET paymentMethod = :paymentMethod WHERE id = :orderId")
    suspend fun updatePaymentMethod(paymentMethod: PaymentMethod, orderId: Int)

}