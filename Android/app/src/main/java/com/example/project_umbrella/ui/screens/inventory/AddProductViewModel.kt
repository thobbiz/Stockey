package com.example.project_umbrella.ui.screens.inventory

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.example.project_umbrella.data.Product
import com.example.project_umbrella.data.ProductsRepository
import java.text.NumberFormat

class AddProductViewModel(
    private val productsRepository: ProductsRepository): ViewModel() {

    // Holds current Ui State
    var productUiState by mutableStateOf(ProductUiState())
        private set

    // Updates Ui State with the value provided in the argument
    fun updateUiState(productInfo: ProductInfo) {
        productUiState =
            ProductUiState(productInfo = productInfo, isInfoValid = validateInput(productInfo))
    }

    // Checks input fields to check if they are NOT empty
    private fun validateInput(uiState: ProductInfo = productUiState.productInfo): Boolean {
        return with(uiState) {
            name.isNotBlank() && unitSellingPrice.isNotBlank() && quantity.isNotBlank() && unitCostPrice.isNotBlank() && measurementUnit.isNotBlank()
        }
    }

    // Save Product into database
    suspend fun saveProduct() {
        if (validateInput()) {
            productsRepository.insertProduct(productUiState.productInfo.toProduct())
        }
    }
}


// Represents Ui state for Product
data class ProductUiState(
    val productInfo: ProductInfo = ProductInfo(),
    val isInfoValid: Boolean = false
)

// Represents product info
data class ProductInfo(
    val productId: Int = 0,
    val name: String = "",
    val unitSellingPrice: String = "",
    val unitCostPrice : String = "",
    val quantity: String = "",
    val measurementUnit: String =""
)

// Converts product information to product object
fun ProductInfo.toProduct(): Product = Product (
    productId = productId,
    name = name,
    sellingPrice = unitSellingPrice.toDoubleOrNull() ?: 0.0,
    costPrice = unitCostPrice.toDoubleOrNull() ?: 0.0,
    quantity = quantity.toIntOrNull() ?: 0,
    measurementUnit = measurementUnit
)

fun Product.formatedCostPrice(): String {
    return NumberFormat.getCurrencyInstance().format(costPrice)
}

fun Product.formatedSellingPrice(): String {
    return NumberFormat.getCurrencyInstance().format(sellingPrice)
}

// Converts product object to product info
fun Product.toProductInfo(): ProductInfo = ProductInfo(
    productId = productId,
    name = name,
    unitSellingPrice = sellingPrice.toString(),
    unitCostPrice = costPrice.toString(),
    quantity = quantity.toString(),
    measurementUnit = measurementUnit
)

// Converts product object to product Ui State
fun Product.toProductUiState(isInfoValid: Boolean = false): ProductUiState = ProductUiState(
    productInfo = this.toProductInfo(),
    isInfoValid = isInfoValid
)