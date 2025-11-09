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

    private val _totalProductsCount = MutableStateFlow(0)
    private val _totalStocksCount = MutableStateFlow(0)
    val totalProductCount: StateFlow<Int> = _totalProductsCount
    val totalStockCount: StateFlow<Int> = _totalStocksCount

    val inventoryUiState: StateFlow<InventoryUiState> =
        productsRepository.getAllProducts().map { InventoryUiState(it) }
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(TIMEOUT_MILLIS),
                initialValue = InventoryUiState()
            )

    init {
        viewModelScope.launch {
            productsRepository.getTotalStockCount().collect{ count ->
                _totalStocksCount.value = count
            }

            productsRepository.getTotalProductsCount().collect { count ->
                _totalProductsCount.value = count
            }
        }
    }

}

data class InventoryUiState(
    val productsList: List<Product> = listOf(),
    val totalProductCount: Int = 0,
    val totalStockCount: Int = 0
)