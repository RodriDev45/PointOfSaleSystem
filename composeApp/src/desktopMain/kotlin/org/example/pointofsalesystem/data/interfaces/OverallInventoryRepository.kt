package org.example.pointofsalesystem.data.interfaces

import org.example.pointofsalesystem.domain.utils.Result

interface OverallInventoryRepository {
    suspend fun getTotalCategories(): Result<Int>
    suspend fun getTopCategories(): Result<Int>
    suspend fun getTotalProducts(): Result<Int>
    suspend fun getTotalValue(): Result<Double>
    suspend fun getTotalLowStock(): Result<Int>
    suspend fun getTotalOutOfStock(): Result<Int>

}