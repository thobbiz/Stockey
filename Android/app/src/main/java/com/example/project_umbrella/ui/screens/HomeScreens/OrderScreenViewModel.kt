package com.example.project_umbrella.ui.screens.HomeScreens

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.project_umbrella.data.OrdersRepository
import com.example.project_umbrella.model.Order
import com.example.project_umbrella.model.OrderProduct
import com.example.project_umbrella.model.OrderStatus
import com.example.project_umbrella.model.PaymentMethod
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

class OrderScreenViewModel(
    private val ordersRepository: OrdersRepository
): ViewModel() {

    companion object {
        private const val TIMEOUT_MILLIS = 5_000L
    }

    private val _totalAmountCount = MutableStateFlow(0.0)

    var uiState by mutableStateOf(OrderScreenUiState())
        private set

    var order: Order = Order (
    )

    fun orderIsValid(): Boolean {
        return !(uiState.orderProductsList.isEmpty() || (uiState.orderInfo.paymentMethod == PaymentMethod.PaymentMethodNotSelected))
    }

    suspend fun updateUiState(orderInfo: OrderInfo) {
        val orderState = ordersRepository.getOrderWithProducts(order.id).first()
        uiState = OrderScreenUiState(
            orderProductsList = orderState!!.orderProducts,
            orderInfo = orderInfo
        )
    }

    suspend fun saveOrder() {
        if (orderIsValid()) {
            order = Order(
                totalAmount = _totalAmountCount.value,
                orderStatus = OrderStatus.OrderStatusCompleted.orderStatusName,
                paymentMethod = uiState.orderInfo.paymentMethod.paymentMethodName,
                comment = uiState.orderInfo.comment
            )
            ordersRepository.insertOrder(order)
        }
    }

    init {
        viewModelScope.launch {
            ordersRepository.calculateOrderTotal(order.id).collect { total ->
                _totalAmountCount.value = total
            }
        }
    }
}

data class OrderScreenUiState(
    val orderProductsList: List<OrderProduct> = listOf(),
    val orderInfo: OrderInfo = OrderInfo(),
)

data class OrderInfo (
    val paymentMethod: PaymentMethod = PaymentMethod.PaymentMethodNotSelected,
    val comment: String = ""
)