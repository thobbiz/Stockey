package com.example.project_umbrella.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.project_umbrella.model.Sale
import kotlinx.coroutines.flow.Flow

@Dao
interface SaleDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(sale: Sale)

    @Delete
    suspend fun delete(sale: Sale)

    @Query("SELECT * from sales WHERE id = :id")
    fun getSale(id: Int): Flow<Sale>

    @Query("SELECT * from sales ORDER BY id DESC")
    fun getAllSales(): Flow<List<Sale>>

    @Query("SELECT COUNT(*) FROM sales")
    fun getTotalSalesCount(): Flow<Int>

    @Query("SELECT SUM(quantity) FROM sales.products")
    fun getTotalProductSold(): Flow<Int>
}