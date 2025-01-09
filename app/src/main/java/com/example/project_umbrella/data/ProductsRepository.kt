package com.example.project_umbrella.data

import kotlinx.coroutines.flow.Flow

interface ProductsRepository {
    // Retrieve all products from the data source
    fun getAllProducts(): Flow<List<Product>>

    // Retrieve a Product form the data source
    fun getProduct(id: Int): Flow<Product?>


    // Insert product in the data source
    suspend fun insertProduct(product: Product)


    // Delete product from the data source
    suspend fun deleteProduct(product: Product)


    // Update product in the data source
    suspend fun updateProduct(product: Product)

    // Retrieve the number of products
    suspend fun getTotalProductsCount(): Flow<Int>

    // Retrieve the total number of units of products available
    suspend fun getTotalStockCount(): Flow<Int>

}