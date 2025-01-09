package com.example.project_umbrella.data

import kotlinx.coroutines.flow.Flow

class OfflineProductsRepository(private val productDao: ProductDao): ProductsRepository {
    override fun getAllProducts(): Flow<List<Product>> = productDao.getAllProducts()

    override suspend fun getTotalProductsCount(): Flow<Int> = productDao.getTotalProductsCount()

    override fun getProduct(id: Int): Flow<Product?> = productDao.getProduct(id)

    override suspend fun insertProduct(product: Product) = productDao.insert(product)

    override suspend fun deleteProduct(product: Product) = productDao.delete(product)

    override suspend fun updateProduct(product: Product) = productDao.update(product)

    override suspend fun getTotalStockCount(): Flow<Int> = productDao.getTotalStockCount()
}