package com.example.project_umbrella.model

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey
import androidx.room.Relation
import com.example.project_umbrella.data.Product
import java.time.LocalDateTime

@Entity(tableName = "sales")
data class Sale (
    @PrimaryKey(autoGenerate = true)
    val saleId: Int = 0,
    val products: List<Product>?,
    val totalAmount: Double,
    val customer: Customer?,
    val status: SaleStatus,
    val paymentMethod: PaymentMethod,
    val notes: String,
    val date: LocalDateTime = LocalDateTime.now(),
)

@Entity(
    tableName  = "sale_products",
    foreignKeys = [
        ForeignKey(
            entity = Sale::class,
            parentColumns = ["saleId"],
            childColumns = ["saleOwnerId"],
            onDelete = ForeignKey.CASCADE
        ),
        ForeignKey(
            entity = Product::class,
            parentColumns = ["productId"],
            childColumns = ["productOwnerId"],
            onDelete = ForeignKey.CASCADE
        )
    ],
    indices = [Index("saleOwnerId"), Index("productOwnerId")]
)
data class SaleProduct(
    @PrimaryKey(autoGenerate = true) val saleProductId: Int = 0,
    val saleOwnerId: Int,
    val productOwnerId: Int,
    val quantitySold: Int,
    val unitPrice: Double
)

data class SaleWithProducts(
    @Embedded val sale: Sale,
    @Relation(
        parentColumn = "saleId",
        entityColumn = "saleOwnerId",
        entity = SaleProduct::class
    )
    val products: List<SaleProduct>
)

enum class SaleStatus {
    PENDING, COMPLETED, CANCELLED
}

enum class PaymentMethod {
    CASH, BANK_TRANSFER, DEBIT_CARD, DEBT, NOT_SELECTED
}