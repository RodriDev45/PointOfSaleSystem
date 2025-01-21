package org.example.pointofsalesystem.domain.model

data class InventoryFilterModel(
    val searchText: String?,
    val quantity: Int?,
    val salePrice: Double?,
    val category: Long?,
    val inStock: Boolean,
    val lowStock: Boolean,
    val outOfStock: Boolean,
    val brand: String?,
)
