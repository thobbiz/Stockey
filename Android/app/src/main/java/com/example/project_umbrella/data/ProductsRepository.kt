package com.example.project_umbrella.data

import kotlinx.coroutines.flow.Flow

interface ProductsRepository {
    // Update product costPrice// Insert product in the data source
        suspend fun insertProduct(product: Product)

        // Delete product from the data source
        suspend fun deleteProduct(productId: Int)

        // Retrieve a Product from the data source
        fun getProduct(id: Int): Flow<Product?>

        // Retrieve all products from the data source
        fun getAllProducts(): Flow<List<Product>>

        // Add to Quantity of product in the data source
        suspend fun addQuantity(productId: Int, amount: Int)

        // Add to Quantity of product in the data source
        suspend fun removeQuantity(productId: Int, amount: Int)
        suspend fun updateProductCostPrice(productId: Int, costPrice: Double)

    // Update product sellingPrice in the data source
    suspend fun updateProductSellingPrice(productId: Int, sellingPrice: Double)

        // Retrieve the number of products
        suspend fun getTotalProductsCount(): Flow<Int>

        // Retrieve the total number of units of products available
        suspend fun getTotalStockCount(): Flow<Int>
}