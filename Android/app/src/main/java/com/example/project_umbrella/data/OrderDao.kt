package com.example.project_umbrella.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import com.example.project_umbrella.model.Order
import com.example.project_umbrella.model.OrderProduct
import com.example.project_umbrella.model.OrderWithProducts
import kotlinx.coroutines.flow.Flow

@Dao
interface OrderDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertOrder(order: Order)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertOrderProducts(orderProducts: List<OrderProduct>)

    @Transaction
    @Query("SELECT * FROM orders WHERE id = :orderId")
    fun getOrderWithProducts(orderId: Int): Flow<OrderWithProducts?>

    @Transaction
    @Query("SELECT * from orders ORDER BY id")
    fun getAllOrders(): Flow<List<OrderWithProducts>>

    @Query("UPDATE orders SET totalAmount = :total WHERE id = :orderId")
    suspend fun updateOrderTotal(orderId: Int, total: Double)

    @Query("""
        SELECT SUM(quantity * price) 
        FROM order_products 
        WHERE orderId = :orderId
    """)
    fun calculateOrderTotal(orderId: Int): Flow<Double>

    @Query("UPDATE orders SET orderStatus = :orderStatus WHERE id = :orderId")
    suspend fun updateOrderStatus(orderId: Int, orderStatus: String)

    @Query("UPDATE orders SET paymentMethod = :paymentMethod WHERE id = :orderId")
    suspend fun updatePaymentMethod(orderId: Int, paymentMethod: String)

    @Query("DELETE FROM orders WHERE id = :orderId")
    suspend fun deleteOrder(orderId: Int)
}