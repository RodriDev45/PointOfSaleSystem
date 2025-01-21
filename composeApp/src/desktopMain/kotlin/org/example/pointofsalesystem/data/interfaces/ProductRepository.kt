package org.example.pointofsalesystem.data.interfaces

import org.example.pointofsalesystem.data.model.Product
import org.example.pointofsalesystem.data.model.ProductPagination
import org.example.pointofsalesystem.domain.model.InventoryFilterModel
import org.example.pointofsalesystem.domain.utils.Result

interface ProductRepository {
    suspend fun insertProduct(product: Product): Result<String>
    suspend fun deleteProduct(code: String): Result<String>
    suspend fun getProductByCode(code: String): Result<Product>
    suspend fun getAllProducts(): Result<List<Product>>
    suspend fun getProductsRange(page: Int, pageSize: Int, filters: InventoryFilterModel): Result<ProductPagination>
    suspend fun updateProduct(product: Product): Result<String>
}