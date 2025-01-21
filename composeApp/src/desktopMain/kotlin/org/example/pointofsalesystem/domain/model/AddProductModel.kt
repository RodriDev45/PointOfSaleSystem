package org.example.pointofsalesystem.domain.model

import java.io.File

data class AddProductModel (
    val image: File?,
    val code: Long,
    val name: String,
    val category: Long,
    val brand: String,
    val quantity: Int,
    val minStock: Int,
    val maxStock: Int,
    val purchasePrice: Double,
    val salePrice: Double,
)