package com.example.project_umbrella.data

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "products")
data class Product(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val owner: String,
    val name: String,
    val costPrice: Double,
    val sellingPrice: Double,
    val quantity: Int,
    val unit: String,
    val description: String
)