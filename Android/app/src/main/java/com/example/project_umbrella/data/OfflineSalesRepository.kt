package com.example.project_umbrella.data

import com.example.project_umbrella.model.Sale
import kotlinx.coroutines.flow.Flow

class OfflineSalesRepository(private val saleDao: SaleDao): SalesRepository {
    override fun getAllSales(): Flow<List<Sale>> = saleDao.getAllSales()

    override suspend fun getTotalSalesCount(): Flow<Int> = saleDao.getTotalSalesCount()

    override suspend fun deleteSale(sale: Sale) = saleDao.delete(sale)

    override fun getSale(id: Int): Flow<Sale> = saleDao.getSale(id)

    override suspend fun insertSale(sale: Sale) = saleDao.insert(sale)
    override suspend fun getTotalProductSold(): Flow<Int> = saleDao.getTotalProductSold()
}