package org.example.pointofsalesystem.data.repository

import com.zaxxer.hikari.HikariDataSource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.example.pointofsalesystem.data.datasource.OracleDataSource
import org.example.pointofsalesystem.data.interfaces.CategoryRepository
import org.example.pointofsalesystem.data.model.Category
import org.example.pointofsalesystem.data.model.User
import org.example.pointofsalesystem.domain.utils.Result
import java.sql.SQLException

class CategoryRepositoryImpl(
    private val dataSource: HikariDataSource
) : CategoryRepository {
    override suspend fun getAllCategories(): Result<List<Category>> {
        val query = "SELECT * FROM categories"
        return withContext(Dispatchers.IO){
            try {
                dataSource.connection.use { connection ->
                    connection.prepareStatement(query).use { preparedStatement ->
                        preparedStatement.executeQuery().use { resultSet ->
                            val categories = mutableListOf<Category>()
                            while (resultSet.next()) {
                                println(resultSet.getString("name"))
                                categories.add(
                                    Category(
                                        id = resultSet.getLong("id"),
                                        name = resultSet.getString("name"),
                                        created_at = resultSet.getDate("created_at")
                                    )
                                )
                            }
                            Result.Success(categories)
                        }
                    }
                }
            }catch (e: SQLException){
                e.printStackTrace()
                Result.Error(e.message.toString())
            }
        }
    }

    override suspend fun getCategoryById(id: Long): Result<Category> {
        val query = "SELECT * FROM categories WHERE id = ?"
        return withContext(Dispatchers.IO){
            try {
                dataSource.connection.use { connection ->
                    val preparedStatement = connection.prepareStatement(query)
                    preparedStatement.setLong(1, id)
                    val resultSet = preparedStatement.executeQuery()
                    if(resultSet.next()){
                        val category = Category(
                            id = resultSet.getLong("id"),
                            name = resultSet.getString("name"),
                            created_at = resultSet.getDate("created_at")
                        )
                        Result.Success(category)
                    }else{
                        Result.Error("Failed to get category")
                    }
                }
            }catch (e: SQLException){
                e.printStackTrace()
                Result.Error(e.message.toString())
            }
        }
    }
}