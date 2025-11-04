package com.example.project_umbrella.model

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.Relation
import com.example.project_umbrella.data.Product

@Entity(
    tableName = "order_products",
    primaryKeys = ["orderId", "productId"],
    indices = [
        Index(value = ["orderId"]),
        Index(value = ["orderId", "productId"], unique = true)
    ],
    foreignKeys =
        [
            ForeignKey(
                entity = Order::class,
                parentColumns = ["id"],
                childColumns = ["orderId"],
                onDelete = ForeignKey.CASCADE
            ),
            ForeignKey(
                entity = Product::class,
                parentColumns = ["id"],
                childColumns = ["productId"],
                onDelete = ForeignKey.RESTRICT // Prevent deletion of products in orders
            )
        ],
)
data class OrderProduct (
    val orderId: Int,
    val productId: Int,
    val price: Double,
    val quantity: Int,
)


//Get an order with all of its order products
data class OrderWithProducts(
    @Embedded val order: Order,
    @Relation(
        parentColumn = "id",
        entityColumn = "orderId",
        entity = OrderProduct::class
    )
    val orderProducts: List<OrderProduct>
)