package com.example.project_umbrella.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.project_umbrella.model.Customer
import kotlinx.coroutines.flow.Flow

@Dao
interface CustomerDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertCustomer(customer: Customer)

    @Query("DELETE FROM customers WHERE id = :customerId")
    suspend fun deleteCustomer(customerId: Int)

    @Query("SELECT * from customers WHERE id = :customerId LIMIT 1")
    fun getCustomer(customerId: Int): Flow<Customer>

    @Query("SELECT * from customers ORDER BY id")
    fun getAllCustomers(): Flow<List<Customer>>


    @Query("UPDATE customers SET phone = :phoneNo WHERE id = :customerId")
    suspend fun updateCustomerPhone(customerId: Int ,phoneNo: Long)
}