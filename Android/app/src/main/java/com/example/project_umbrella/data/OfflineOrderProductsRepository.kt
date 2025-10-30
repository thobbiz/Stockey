package com.example.project_umbrella.data

import com.example.project_umbrella.model.OrderProduct
import kotlinx.coroutines.flow.Flow

class OfflineOrderProductsRepository(private val orderProductDao: OrderProductDao): OrderProductsRepository {
    override suspend fun insertOrderProduct(orderProduct: OrderProduct) = orderProductDao.insertOrderProduct(orderProduct)
    override fun getAOrderProduct(orderId: Int, productId: Int): Flow<OrderProduct> = orderProductDao.getAOrderProduct(orderId, productId)
    override fun getOrderProducts(orderId: Int, productId: Int): Flow<List<OrderProduct>> = orderProductDao.getOrderProducts(orderId)
    override fun getAllOrderProducts(): Flow<List<OrderProduct>> = orderProductDao.getAllOrderProducts()
}