package com.example.project_umbrella.data

import android.content.Context

interface AppContainer {
    val productsRepository: ProductsRepository
    val ordersRepository: OrdersRepository
    val customersRepository: CustomersRepository

    val entryRepository: EntriesRepository
}

class AppDataContainer(private val context: Context) : AppContainer {

    override val productsRepository: ProductsRepository by lazy {
        OfflineProductsRepository(AppDatabase.getDatabase(context).productDao())
    }

    override val ordersRepository: OrdersRepository by lazy {
        OfflineOrdersRepository(AppDatabase.getDatabase(context).orderDao())
    }

    override val customersRepository: CustomersRepository by lazy {
        OfflineCustomersRepository(AppDatabase.getDatabase(context).customerDao())
    }

    override val entryRepository: EntriesRepository by lazy {
        OfflineEntriesRepository(AppDatabase.getDatabase(context).entryDao())
    }
}