package org.example.pointofsalesystem.data.interfaces

import org.example.pointofsalesystem.data.model.Category
import org.example.pointofsalesystem.domain.utils.Result

interface CategoryRepository {
    suspend fun getAllCategories(): Result<List<Category>>
    suspend fun getCategoryById(id: Long): Result<Category>
}