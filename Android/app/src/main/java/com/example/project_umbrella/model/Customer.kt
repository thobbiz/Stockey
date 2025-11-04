package com.example.project_umbrella.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.time.LocalDateTime

@Entity(tableName = "customers")
data class Customer(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val name: String,
    val phone: Long? = null,
    val email: String? = null,
    val createdAt: LocalDateTime = LocalDateTime.now()
)