package com.example.project_umbrella.data

import com.example.project_umbrella.model.Customer
import kotlinx.coroutines.flow.Flow

class OfflineCustomersRepository(private val customerDao: CustomerDao): CustomersRepository {
    override suspend fun insertCustomer(customer: Customer) = customerDao.insertCustomer(customer)
    override suspend fun deleteCustomer(customerId: Int) = customerDao.deleteCustomer(customerId)
    override fun getCustomer(id: Int): Flow<Customer?> = customerDao.getCustomer(id)
    override fun getAllCustomers(): Flow<List<Customer>> = customerDao.getAllCustomers()
    override suspend fun updateCustomerPhone(customerId: Int, phoneNo: Long) = customerDao.updateCustomerPhone(customerId, phoneNo)
}