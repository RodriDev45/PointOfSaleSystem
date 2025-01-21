package org.example.pointofsalesystem.data.repository

import com.zaxxer.hikari.HikariDataSource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.example.pointofsalesystem.data.datasource.OracleDataSource
import org.example.pointofsalesystem.data.interfaces.ProductRepository
import org.example.pointofsalesystem.data.model.Category
import org.example.pointofsalesystem.data.model.Product
import org.example.pointofsalesystem.data.model.ProductPagination
import org.example.pointofsalesystem.domain.model.InventoryFilterModel
import org.example.pointofsalesystem.domain.utils.Result
import java.sql.SQLException

class ProductRepositoryImpl(
    private val dataSource: HikariDataSource
) : ProductRepository {
    override suspend fun insertProduct(product: Product): Result<String> {
        val query = "INSERT INTO products (code, name, id_category, brand, quantity, minimum_stock, maximum_stock, purchase_price, sale_price, image_url) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)"
        return withContext(Dispatchers.IO) {
            try {
                dataSource.connection.use { connection ->
                    val preparedStatement = connection.prepareStatement(query)
                    preparedStatement.setLong(1, product.code)
                    preparedStatement.setString(2, product.name)
                    preparedStatement.setLong(3, product.category.id)
                    preparedStatement.setString(4, product.brand)
                    preparedStatement.setInt(5, product.quantity)
                    preparedStatement.setInt(6, product.minStock)
                    preparedStatement.setInt(7, product.maxStock)
                    preparedStatement.setDouble(8, product.purchasePrice)
                    preparedStatement.setDouble(9, product.salePrice)
                    preparedStatement.setString(10, product.image)

                    val rowsInserted = preparedStatement.executeUpdate()
                    if(rowsInserted > 0){
                        Result.Success("Product created successfully")
                    }else{
                        Result.Error("Failed to create product")
                    }
                }
            } catch (e: SQLException) {
                e.printStackTrace()
                Result.Error("An unexpected error occurred")
            }
        }
    }

    override suspend fun deleteProduct(code: String): Result<String> {
        TODO("Not yet implemented")
    }

    override suspend fun getProductByCode(code: String): Result<Product> {
        TODO("Not yet implemented")
    }

    override suspend fun getAllProducts(): Result<List<Product>> {
        TODO("Not yet implemented")
    }

    override suspend fun getProductsRange(
        page: Int,
        pageSize: Int,
        filters: InventoryFilterModel
    ): Result<ProductPagination> {
        val startRow = (page - 1) * pageSize + 1
        val endRow = startRow + pageSize - 1

        val baseQuery = """
        SELECT *
        FROM (
            SELECT
                p.code AS product_code,
                p.name AS product_name,
                p.id_category,
                p.brand AS product_brand,
                p.quantity AS product_quantity,
                p.minimum_stock AS product_minimum_stock,
                p.maximum_stock AS product_maximum_stock,
                p.purchase_price AS product_purchase_price,
                p.sale_price AS product_sale_price,
                p.image_url AS product_image_url,
                p.created_at AS product_created_at,
                c.id AS category_id,
                c.name AS category_name,
                c.created_at AS category_created_at,
                ROW_NUMBER() OVER (ORDER BY p.created_at DESC) AS row_num,
                COUNT(*) OVER () AS total_products
            FROM
                products p
            JOIN
                categories c ON p.id_category = c.id
            WHERE 1=1
    """.trimIndent()

        val filtersQuery = StringBuilder()
        val parameters = mutableListOf<Any>()

        filters.searchText?.let {
            val regex = "[0-9]{13}".toRegex()
            if(regex.matches(it)){
                filtersQuery.append(" AND p.code = ?")
                parameters.add(it)
            }else{
                filtersQuery.append(" AND LOWER(p.name) LIKE ?")
                parameters.add("%${it.lowercase()}%")
            }
        }

        filters.quantity?.let {
            filtersQuery.append(" AND p.quantity >= ?")
            parameters.add(it)
        }

        filters.salePrice?.let {
            filtersQuery.append(" AND p.sale_price >= ?")
            parameters.add(it)
        }

        filters.category?.let {
            filtersQuery.append(" AND p.id_category = ?")
            parameters.add(it)
        }

        filters.brand?.let {
            filtersQuery.append(" AND LOWER(p.brand) LIKE ?")
            parameters.add(it.lowercase())
        }

        // Lógica para filtros de stock
        if (filters.inStock || filters.lowStock || filters.outOfStock) {
            filtersQuery.append(" AND (")
            val stockConditions = mutableListOf<String>()

            if (filters.inStock) {
                stockConditions.add("p.quantity > 10") // Productos con cantidad mayor a 10
            }
            if (filters.lowStock) {
                stockConditions.add("p.quantity BETWEEN 1 AND 10") // Productos con cantidad entre 1 y 10
            }
            if (filters.outOfStock) {
                stockConditions.add("p.quantity = 0") // Productos con cantidad igual a 0
            }

            filtersQuery.append(stockConditions.joinToString(" OR "))
            filtersQuery.append(")")
        }

        val finalQuery = """
        $baseQuery
        ${filtersQuery.toString()}
        ) subquery
        WHERE row_num BETWEEN ? AND ?
    """.trimIndent()

        parameters.add(startRow)
        parameters.add(endRow)

        return withContext(Dispatchers.IO) {
            try {
                dataSource.connection.use { connection ->
                    connection.prepareStatement(finalQuery).use { preparedStatement ->
                        parameters.forEachIndexed { index, param ->
                            when (param) {
                                is Int -> preparedStatement.setInt(index + 1, param)
                                is Long -> preparedStatement.setLong(index + 1, param)
                                is Double -> preparedStatement.setDouble(index + 1, param)
                                is String -> preparedStatement.setString(index + 1, param)
                            }
                        }

                        preparedStatement.executeQuery().use { resultSet ->
                            val products = mutableListOf<Product>()
                            var totalProducts = 0

                            while (resultSet.next()) {
                                if (totalProducts == 0) {
                                    totalProducts = resultSet.getInt("total_products")
                                }

                                products.add(
                                    Product(
                                        code = resultSet.getLong("product_code"),
                                        name = resultSet.getString("product_name"),
                                        category = Category(
                                            id = resultSet.getLong("category_id"),
                                            name = resultSet.getString("category_name"),
                                            created_at = resultSet.getDate("category_created_at")
                                        ),
                                        brand = resultSet.getString("product_brand"),
                                        quantity = resultSet.getInt("product_quantity"),
                                        minStock = resultSet.getInt("product_minimum_stock"),
                                        maxStock = resultSet.getInt("product_maximum_stock"),
                                        purchasePrice = resultSet.getDouble("product_purchase_price"),
                                        salePrice = resultSet.getDouble("product_sale_price"),
                                        createdAt = resultSet.getDate("product_created_at"),
                                        image = resultSet.getString("product_image_url")
                                    )
                                )
                            }

                            Result.Success(ProductPagination(products = products, total = totalProducts))
                        }
                    }
                }
            } catch (e: SQLException) {
                e.printStackTrace()
                Result.Error(e.message.toString())
            }
        }
    }



    override suspend fun updateProduct(product: Product): Result<String> {
        TODO("Not yet implemented")
    }

}