package com.example.project_umbrella.ui.screens.HomeScreens

import androidx.lifecycle.ViewModel
import com.example.project_umbrella.data.CustomersRepository
import com.example.project_umbrella.data.Product
import com.example.project_umbrella.model.Customer

class CustomerScreenViewModel(
    customersRepository: CustomersRepository,
): ViewModel {
}

data class CustomersScreenUiState(
    val customersList: List<Customer> = listOf()
)