package com.example.project_umbrella.ui.screens.inventory

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.project_umbrella.data.ProductsRepository
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

class EditProductViewModel(
    savedStateHandle: SavedStateHandle,
    private val productsRepository: ProductsRepository
) : ViewModel(){

    var productUiState by mutableStateOf(ProductUiState())
        private set

    private val productId: Int = checkNotNull(savedStateHandle[EditProductDestination.productIdArg])

    private fun validateInput(uiState: ProductInfo = productUiState.productInfo): Boolean {
        return with(uiState) {
            name.isNotBlank() && unitSellingPrice.isNotBlank() && quantity.isNotBlank() && unitCostPrice.isNotBlank()
        }
    }

    init {
        viewModelScope.launch {
            productUiState = productsRepository.getProduct(productId)
                .filterNotNull()
                .first()
                .toProductUiState()
        }
    }

    fun updateUiState(productInfo: ProductInfo){
        productUiState =
            ProductUiState(
                productInfo = productInfo,
                isInfoValid = validateInput(productInfo)
            )
    }

    suspend fun updateProduct() {
//        if (validateInput(productUiState.productInfo)) {
//            productsRepository.updateProduct(productUiState.productInfo.toProduct())
//        }
    }

}