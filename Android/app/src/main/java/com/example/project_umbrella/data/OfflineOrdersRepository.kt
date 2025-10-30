package com.example.project_umbrella.data

import com.example.project_umbrella.model.Order
import com.example.project_umbrella.model.OrderStatus
import com.example.project_umbrella.model.PaymentMethod
import kotlinx.coroutines.flow.Flow

class OfflineOrdersRepository(private val orderDao: OrderDao): OrdersRepository {
    override suspend fun insertOrder(order: Order) = orderDao.insertOrder(order)

    override suspend fun deleteOrder(orderId: Int) = orderDao.deleteOrder(orderId)

    override fun getOrder(orderId: Int): Flow<Order?> = orderDao.getOrder(orderId)

    override fun getAllOrders(): Flow<List<Order>> = orderDao.getAllOrders()

    override suspend fun updateOrderStatus(orderStatus: OrderStatus, orderId: Int) = orderDao.updateOrderStatus(orderStatus, orderId)

    override suspend fun updatePaymentMethod(paymentMethod: PaymentMethod, orderId: Int) = orderDao.updatePaymentMethod(paymentMethod, orderId)
}