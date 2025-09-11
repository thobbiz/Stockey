package com.example.project_umbrella.ui.screens.inventory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.project_umbrella.data.Product
import com.example.project_umbrella.data.ProductsRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch


class InventoryScreenViewModel(productsRepository: ProductsRepository) : ViewModel() {


    companion object {
        private const val TIMEOUT_MILLIS = 5_000L
    }
    val inventoryUiState: StateFlow<InventoryUiState> = productsRepository.getAllProducts().map { InventoryUiState(it) }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(TIMEOUT_MILLIS),
            initialValue = InventoryUiState()
        )

    private val _totalProducts = MutableStateFlow(0)
    val totalProducts: StateFlow<Int> = _totalProducts

    init {
        viewModelScope.launch {
            productsRepository.getTotalProductsCount().collect { count ->
                _totalProducts.value = count
            }
        }
    }

    private val _totalStocks = MutableStateFlow(0)
    val totalStockCount: StateFlow<Int> = _totalStocks

    init {
        viewModelScope.launch {
            productsRepository.getTotalStockCount().collect{ count ->
                _totalStocks.value = count
            }
        }
    }

}

data class InventoryUiState(val productsList: List<Product> = listOf())