package com.example.project_umbrella.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface ProductDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertProduct(product: Product)

    @Query("DELETE FROM products WHERE id = :productId")
    suspend fun deleteProduct(productId: Int)

    @Query("SELECT * from products WHERE id = :productId LIMIT 1")
    fun getProduct(productId: Int): Flow<Product>

    @Query("SELECT * from products ORDER BY name ASC")
    fun getAllProducts(): Flow<List<Product>>

    @Query("UPDATE products SET quantity = quantity + :amount WHERE id = :productId")
    suspend fun addQuantity(productId: Int, amount: Int)

    @Query("UPDATE products SET quantity = quantity - :amount WHERE id = :productId")
    suspend fun removeQuantity(productId: Int, amount: Int)

    @Query("UPDATE products SET sellingPrice = :sellingPrice WHERE id = :productId")
    suspend fun updateSellingPrice(productId: Int ,sellingPrice: Double)

    @Query("UPDATE products SET costPrice = :costPrice WHERE id = :productId")
    suspend fun updateCostPrice(productId: Int, costPrice: Double)

    @Query("SELECT COUNT(*) FROM products")
    fun getTotalProductsCount(): Flow<Int>

    @Query("SELECT SUM(quantity) FROM products")
    fun getTotalStockCount(): Flow<Int>
}