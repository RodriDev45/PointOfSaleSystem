package org.example.pointofsalesystem.data.model

import java.util.Date

data class Product(
    val code: Long,
    val image: String?,
    val name: String,
    val category: Category,
    val brand: String,
    val quantity: Int,
    val minStock: Int,
    val maxStock: Int,
    val purchasePrice: Double,
    val salePrice: Double,
    val createdAt: Date,
)
