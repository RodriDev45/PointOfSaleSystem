package org.example.pointofsalesystem.data.repository

import com.zaxxer.hikari.HikariDataSource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.example.pointofsalesystem.data.interfaces.OverallInventoryRepository
import org.example.pointofsalesystem.data.model.Category
import org.example.pointofsalesystem.domain.utils.Result
import java.sql.SQLException

class OverallInventoryRepositoryImpl(
    private val dataSource: HikariDataSource
) : OverallInventoryRepository {
    override suspend fun getTotalCategories(): Result<Int> {
        val query = "SELECT COUNT(*) AS total_categories FROM categories"
        return withContext(Dispatchers.IO) {
            try {
                dataSource.connection.use { connection ->
                    connection.prepareStatement(query).use { preparedStatement ->
                        preparedStatement.executeQuery().use { resultSet ->
                            if(resultSet.next()) {
                                val totalCategories = resultSet.getInt("total_categories")
                                Result.Success(totalCategories)
                            }else{

                                Result.Success(0)
                            }
                        }
                    }
                }
            }catch (e: SQLException){
                e.printStackTrace()
                Result.Error(e.message.toString())
            }
        }
    }

    override suspend fun getTopCategories(): Result<Int> {
        val query = """
            SELECT
                COUNT(*) AS top_categories_count
            FROM (
                     SELECT
                         c.id AS category_id
                     FROM
                         categories c
                             LEFT JOIN
                         products p
                         ON
                             c.id = p.id_category
                     GROUP BY
                         c.id
                     HAVING
                         COUNT(p.code) >= 5
                 ) AS top_categories;
        """.trimIndent()
        return withContext(Dispatchers.IO) {
            try {
                dataSource.connection.use { connection ->
                    connection.prepareStatement(query).use { preparedStatement ->
                        preparedStatement.executeQuery().use { resultSet ->
                            if(resultSet.next()) {
                                val totalCategories = resultSet.getInt("top_categories_count")
                                Result.Success(totalCategories)
                            }else{
                                Result.Success(0)
                            }
                        }
                    }
                }
            }catch (e: SQLException){
                e.printStackTrace()
                Result.Error(e.message.toString())
            }
        }
    }

    override suspend fun getTotalProducts(): Result<Int> {
        val query = "SELECT COUNT(*) AS total_products FROM products"
        return withContext(Dispatchers.IO) {
            try {
                dataSource.connection.use { connection ->
                    connection.prepareStatement(query).use { preparedStatement ->
                        preparedStatement.executeQuery().use { resultSet ->
                            if(resultSet.next()) {
                                val totalProducts = resultSet.getInt("total_products")
                                println("El result es: $totalProducts")
                                Result.Success(totalProducts)
                            }else{

                                Result.Success(0)
                            }
                        }
                    }
                }
            }catch (e: SQLException){
                e.printStackTrace()
                Result.Error(e.message.toString())
            }
        }
    }

    override suspend fun getTotalValue(): Result<Double> {
        val query = "SELECT SUM(quantity * sale_price) AS total_value FROM products;"
        return withContext(Dispatchers.IO) {
            try {
                dataSource.connection.use { connection ->
                    connection.prepareStatement(query).use { preparedStatement ->
                        preparedStatement.executeQuery().use { resultSet ->
                            if(resultSet.next()) {
                                val total = resultSet.getDouble("total_value")
                                Result.Success(total)
                            }else{

                                Result.Success(0.0)
                            }
                        }
                    }
                }
            }catch (e: SQLException){
                e.printStackTrace()
                Result.Error(e.message.toString())
            }
        }
    }

    override suspend fun getTotalLowStock(): Result<Int> {
        val query = "SELECT COUNT(*) AS low_stock_count FROM products WHERE quantity < 10;"
        return withContext(Dispatchers.IO) {
            try {
                dataSource.connection.use { connection ->
                    connection.prepareStatement(query).use { preparedStatement ->
                        preparedStatement.executeQuery().use { resultSet ->
                            if(resultSet.next()) {
                                val total = resultSet.getInt("low_stock_count")
                                Result.Success(total)
                            }else{

                                Result.Success(0)
                            }
                        }
                    }
                }
            }catch (e: SQLException){
                e.printStackTrace()
                Result.Error(e.message.toString())
            }
        }
    }

    override suspend fun getTotalOutOfStock(): Result<Int> {
        val query = "SELECT COUNT(*) AS out_stock_count FROM products WHERE quantity <= 0;"
        return withContext(Dispatchers.IO) {
            try {
                dataSource.connection.use { connection ->
                    connection.prepareStatement(query).use { preparedStatement ->
                        preparedStatement.executeQuery().use { resultSet ->
                            if(resultSet.next()) {
                                val total = resultSet.getInt("out_stock_count")
                                Result.Success(total)
                            }else{

                                Result.Success(0)
                            }
                        }
                    }
                }
            }catch (e: SQLException){
                e.printStackTrace()
                Result.Error(e.message.toString())
            }
        }
    }
}