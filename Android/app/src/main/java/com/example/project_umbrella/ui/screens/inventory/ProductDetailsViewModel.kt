package com.example.project_umbrella.ui.screens.inventory

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.project_umbrella.data.ProductsRepository
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn

class ProductDetailsViewModel(
    savedStateHandle: SavedStateHandle,
    private val productsRepository: ProductsRepository
): ViewModel() {
    private val productId: Int = checkNotNull(savedStateHandle[ProductDetailsDestination.productIdArg])
    val uiState: StateFlow<ProductDetailsUiState> = productsRepository.getProduct(productId)
        .filterNotNull()
        .map {
            ProductDetailsUiState(outOfStock = it.quantity <= 0, productInfo = it.toProductInfo())
        }.stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(TIMEOUT_MILLIS),
            initialValue = ProductDetailsUiState()
        )

    companion object {
        private const val TIMEOUT_MILLIS = 5_000L
    }

    suspend fun deleteProduct() {
        productsRepository.deleteProduct(uiState.value.productInfo.toProduct().id)
    }
}

data class ProductDetailsUiState(
    val outOfStock: Boolean = true,
    val productInfo: ProductInfo = ProductInfo()
)