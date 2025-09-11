package com.example.project_umbrella.model

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "customers")
data class Customer(
    @PrimaryKey(autoGenerate = true)
    val customerId: Int,
    val customerName: String
)
