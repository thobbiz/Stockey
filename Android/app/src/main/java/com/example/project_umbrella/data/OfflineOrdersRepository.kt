package com.example.project_umbrella.data

import com.example.project_umbrella.model.Order
import com.example.project_umbrella.model.OrderProduct
import com.example.project_umbrella.model.OrderWithProducts
import kotlinx.coroutines.flow.Flow

class OfflineOrdersRepository(private val orderDao: OrderDao): OrdersRepository {
    override suspend fun insertOrder(order: Order) = orderDao.insertOrder(order)

    override suspend fun insertOrderProducts(orderProducts: List<OrderProduct>) = orderDao.insertOrderProducts(orderProducts)

    override  fun getOrderWithProducts(orderId: Int): Flow<OrderWithProducts?> = orderDao.getOrderWithProducts(orderId)

    override fun getAllOrders(): Flow<List<OrderWithProducts>> = orderDao.getAllOrders()

    override fun calculateOrderTotal(orderId: Int): Flow<Double> = orderDao.calculateOrderTotal(orderId)

    override suspend fun updateOrderTotal(orderId: Int, total: Double) = orderDao.updateOrderTotal(orderId, total)

    override suspend fun deleteOrder(orderId: Int) = orderDao.deleteOrder(orderId)

    override suspend fun updateOrderStatus(orderId: Int, orderStatus: String) = orderDao.updateOrderStatus(orderId, orderStatus)

    override suspend fun updatePaymentMethod(orderId: Int, paymentMethod: String) = orderDao.updatePaymentMethod(orderId, paymentMethod)
}