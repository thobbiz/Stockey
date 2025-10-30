package com.example.project_umbrella.data

import kotlinx.coroutines.flow.Flow

interface ProductsRepository {
    // Insert product in the data source
    suspend fun insertProduct(product: Product)

    // Delete product from the data source
    suspend fun deleteProduct(productId: Int)

    // Retrieve a Product form the data source
    fun getProduct(id: Int): Flow<Product?>

    // Retrieve all products from the data source
    fun getAllProducts(): Flow<List<Product>>

    // Add to Quantity of product
    suspend fun addQuantity(productId: Int, amount: Int)

    // Update product sellingPrice in the data source
    suspend fun updateProductSellingPrice(sellingPrice: Double, productId: Int)

    // Update product costPrice in the data source
    suspend fun updateProductCostPrice(costPrice: Double, productId: Int)

    // Retrieve the number of products
    suspend fun getTotalProductsCount(): Flow<Int>

    // Retrieve the total number of units of products available
    suspend fun getTotalStockCount(): Flow<Int>

}