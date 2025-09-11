package com.example.project_umbrella.ui.screens.HomeScreens

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import co.yml.charts.common.extensions.isNotNull
import com.example.project_umbrella.data.Product
import com.example.project_umbrella.data.SalesRepository
import com.example.project_umbrella.model.Customer
import com.example.project_umbrella.model.PaymentMethod
import com.example.project_umbrella.model.Sale
import com.example.project_umbrella.model.SaleStatus
import java.time.LocalDateTime

class CreateScreenViewModel(
    private val salesRepository: SalesRepository): ViewModel(){

        var saleUiState by mutableStateOf(SaleUiState())
            private set

    fun updateUiState(saleInfo: SaleInfo) {
        saleUiState = SaleUiState(
            salesInfo = saleInfo,
            isInfoValid = validateInput(saleInfo)
        )
    }

    private fun validateInput(uiState: SaleInfo = saleUiState.salesInfo): Boolean {
        return with(uiState) {
            products.isNotNull() && totalAmount.isNotNull() && status == SaleStatus.PENDING && paymentMethod != PaymentMethod.NOT_SELECTED
        }
    }

    suspend fun createSale() {
        if(validateInput()) {
            saleUiState.salesInfo.status = SaleStatus.COMPLETED
            salesRepository.insertSale(saleUiState.salesInfo.toSale())
        }
    }

}

data class SaleUiState(
    val salesInfo: SaleInfo = SaleInfo(),
    val isInfoValid: Boolean = false
)

data class SaleInfo(
    val saleId: Int = 0,
    val products: List<Product>? = null,
    val totalAmount: Double = 0.0,
    val customer: Customer? = null,
    var status: SaleStatus = SaleStatus.PENDING,
    val paymentMethod: PaymentMethod = PaymentMethod.NOT_SELECTED,
    val notes: String = "",
    val date: LocalDateTime = LocalDateTime.now()
)

fun SaleInfo.toSale(): Sale = Sale(
    saleId = saleId,
    products = products,
    totalAmount = totalAmount,
    customer = customer,
    status = status,
    paymentMethod = paymentMethod,
    notes = notes,
    date = date
)

fun Sale.toSaleInfo(): SaleInfo = SaleInfo(
    saleId = saleId,
    products = products,
    totalAmount = totalAmount,
    customer = customer,
    status = status,
    paymentMethod = paymentMethod,
    notes = notes,
    date = date
)

fun Sale.toSaleUiState(isInfoValid: Boolean = false): SaleUiState = SaleUiState(
    salesInfo = this.toSaleInfo(),
    isInfoValid = isInfoValid
)