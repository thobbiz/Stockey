package com.example.project_umbrella.data

import android.content.Context

interface AppContainer {
    val productsRepository: ProductsRepository

    val salesRepository: SalesRepository
}

class AppDataContainer(private val context: Context) : AppContainer {

    override val productsRepository: ProductsRepository by lazy {
        OfflineProductsRepository(ProductDatabase.getDatabase(context).productDao())
    }

    override val salesRepository: SalesRepository by lazy {
        OfflineSalesRepository(SaleDatabase.getDatabase(context).saleDao())
    }
}