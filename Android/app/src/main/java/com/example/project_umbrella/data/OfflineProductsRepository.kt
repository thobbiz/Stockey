package com.example.project_umbrella.data

import kotlinx.coroutines.flow.Flow

class OfflineProductsRepository(private val productDao: ProductDao): ProductsRepository {
    override suspend fun insertProduct(product: Product) = productDao.insertProduct(product)

    override suspend fun deleteProduct(productId: Int) = productDao.deleteProduct(productId)

    override fun getProduct(id: Int): Flow<Product?> = productDao.getProduct(id)

    override fun getAllProducts(): Flow<List<Product>> = productDao.getAllProducts()

    override suspend fun addQuantity(productId: Int, amount: Int) = productDao.addQuantity(productId, amount)

    override suspend fun updateProductSellingPrice(sellingPrice: Double, productId: Int) = productDao.updateSellingPrice(productId, sellingPrice)

    override suspend fun updateProductCostPrice(costPrice: Double, productId: Int) = productDao.updateCostPrice(productId, costPrice)

    override suspend fun getTotalProductsCount(): Flow<Int> = productDao.getTotalProductsCount()

    override suspend fun getTotalStockCount(): Flow<Int> = productDao.getTotalStockCount()
}