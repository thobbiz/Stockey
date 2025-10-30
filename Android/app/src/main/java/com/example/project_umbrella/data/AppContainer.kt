package com.example.project_umbrella.data

import android.content.Context

interface AppContainer {
    val productsRepository: ProductsRepository
    val ordersRepository: OrdersRepository

    val orderProductsRepository: OrderProductsRepository
}

class AppDataContainer(private val context: Context) : AppContainer {

    override val productsRepository: ProductsRepository by lazy {
        OfflineProductsRepository(AppDatabase.getDatabase(context).productDao())
    }

    override val ordersRepository: OrdersRepository by lazy {
        OfflineOrdersRepository(AppDatabase.getDatabase(context).orderDao())
    }

    override val orderProductsRepository: OrderProductsRepository by lazy {
        OfflineOrderProductsRepository(AppDatabase.getDatabase(context).orderProductDao())
    }
}