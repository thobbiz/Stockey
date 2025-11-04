package com.example.project_umbrella.data

import com.example.project_umbrella.model.Customer
import kotlinx.coroutines.flow.Flow

interface CustomersRepository {
    // Insert customer in the data source
    suspend fun insertCustomer(customer: Customer)

    // Delete product from the data source
    suspend fun deleteCustomer(customerId: Int)

    // Retrieve a customer form the data source
    fun getCustomer(id: Int): Flow<Customer?>

    // Retrieve all customers from the data source
    fun getAllCustomers(): Flow<List<Customer>>

    // Update customer Phone Number
    suspend fun updateCustomerPhone(customerId: Int, phoneNo: Long)

}