package com.example.project_umbrella.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface ProductDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertProduct(product: Product)

    @Delete
    @Query("DELETE FROM products WHERE id = :productId")
    suspend fun deleteProduct(productId: Int)

    @Query("SELECT * from products WHERE id = :productId LIMIT 1")
    fun getProduct(productId: Int): Flow<Product>

    @Query("SELECT * from products")
    fun getAllProducts(): Flow<List<Product>>

    @Update
    @Query("UPDATE products SET quantity = quantity + :amount WHERE id = :productId")
    suspend fun addQuantity(productId: Int, amount: Int)

    @Update
    @Query("UPDATE products SET sellingPrice = :sellingPrice WHERE id = :productId")
    suspend fun updateSellingPrice(sellingPrice: Double, productId: Int)

    @Update
    @Query("UPDATE products SET costPrice = :costPrice WHERE id = :productId")
    suspend fun updateCostPrice(costPrice: Double, productId: Int)

    @Query("SELECT COUNT(*) FROM products")
    fun getTotalProductsCount(): Flow<Int>

    @Query("SELECT SUM(quantity) FROM products")
    fun getTotalStockCount(): Flow<Int>
}