package com.example.project_umbrella.data

import com.example.project_umbrella.model.Sale
import kotlinx.coroutines.flow.Flow

interface SalesRepository {
    fun getAllSales(): Flow<List<Sale>>

    fun getSale(id: Int): Flow<Sale>

    suspend fun insertSale(sale: Sale)

    suspend fun deleteSale(sale: Sale)

    suspend fun getTotalSalesCount(): Flow<Int>

    suspend fun getTotalProductSold(): Flow<Int>
}