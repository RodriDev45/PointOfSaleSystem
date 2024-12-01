package org.example.pointofsalesystem.data.repository

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.example.pointofsalesystem.data.datasource.OracleDataSource
import org.example.pointofsalesystem.data.interfaces.UserRepository
import org.example.pointofsalesystem.data.model.User
import org.example.pointofsalesystem.domain.utils.Result
import java.sql.SQLException

class UserRepositoryImpl : UserRepository {
    override suspend fun insertUser(newUser: User): Result<String> {
        val query = "INSERT INTO users (id, name, email, password, picture, google_id) VALUES (?, ?, ?, ?, ?, ?)"
        return withContext(Dispatchers.IO) {
            try {
                OracleDataSource.getConnection().use { connection ->
                    val preparedStatement = connection.prepareStatement(query)
                    preparedStatement.setString(1, newUser.id)
                    preparedStatement.setString(2, newUser.name)
                    preparedStatement.setString(3, newUser.email)
                    preparedStatement.setString(4, newUser.password)
                    preparedStatement.setString(5, newUser.picture)
                    preparedStatement.setString(6, newUser.googleId)

                    val rowsInserted = preparedStatement.executeUpdate()
                    if(rowsInserted > 0){
                        Result.Success("User created successfully")
                    }else{
                        Result.Error("Failed to create user")
                    }
                }
            } catch (e: SQLException) {
                e.printStackTrace()
                Result.Error("An unexpected error occurred")
            }
        }
    }

    override suspend fun updateUser(user: User): Result<User> {
        val query = "UPDATE users SET name = ?, picture = ?, role = ? WHERE id = ?"
        return withContext(Dispatchers.IO) {
            try {
                OracleDataSource.getConnection().use { connection ->
                    val preparedStatement = connection.prepareStatement(query)
                    preparedStatement.use {
                        // Establece los valores de las columnas a actualizar
                        it.setString(1, user.name)
                        it.setString(2, user.picture)
                        it.setString(3, user.role)
                        it.setString(4, user.id)
                        val rowsAffected = it.executeUpdate()

                        if (rowsAffected > 0) {
                            Result.Success(user)
                        } else {
                            Result.Error("User not found or no change made")
                        }
                    }
                }
            } catch (e: SQLException) {
                e.printStackTrace()
                Result.Error(e.message ?: "SQL error occurred")
            }
        }

    }

    override suspend fun deleteUser(user: User): Result<User> {
        val query = "DELETE FROM users WHERE id = ?"
        return withContext(Dispatchers.IO){
            try {
                OracleDataSource.getConnection().use { connection ->
                    val preparedStatement = connection.prepareStatement(query)
                    preparedStatement.use {
                        it.setString(1, user.id)
                        val rowsAffected = it.executeUpdate()
                        if (rowsAffected > 0) {
                            Result.Success(user)
                        } else {
                            Result.Error("User not found")
                        }
                    }
                }
            }catch (e: SQLException){
                e.printStackTrace()
                Result.Error(e.message ?: "An unexpected error occurred")
            }
        }
    }

    override suspend fun getUserById(userId: String): Result<User> {
        val query = "SELECT * FROM users WHERE id = ?"
        return withContext(Dispatchers.IO){
            try {
                OracleDataSource.getConnection().use { connection ->
                    val preparedStatement = connection.prepareStatement(query)
                    preparedStatement.setString(1, userId)
                    val resultSet = preparedStatement.executeQuery()
                    if(resultSet.next()){
                        val user = User(
                            id = resultSet.getString("id"),
                            name = resultSet.getString("name"),
                            email = resultSet.getString("email"),
                            password = resultSet.getString("password"),
                            picture = resultSet.getString("picture"),
                            googleId = resultSet.getString("google_id"),
                            role = resultSet.getString("role")
                        )
                        Result.Success(user)
                    }else{
                        Result.Error("Failed to get user")
                    }
                }
            }catch (e: SQLException){
                e.printStackTrace()
                Result.Error(e.message.toString())
            }
        }
    }

    override suspend fun getUserByEmail(email: String): Result<User> {
        val query = "SELECT * FROM users WHERE email = ?"
        return withContext(Dispatchers.IO){
            try {
                OracleDataSource.getConnection().use { connection ->
                    val preparedStatement = connection.prepareStatement(query)
                    preparedStatement.setString(1, email)
                    val resultSet = preparedStatement.executeQuery()
                    if(resultSet.next()){
                        val user = User(
                            id = resultSet.getString("id"),
                            name = resultSet.getString("name"),
                            email = resultSet.getString("email"),
                            password = resultSet.getString("password"),
                            picture = resultSet.getString("picture"),
                            googleId = resultSet.getString("google_id"),
                            role = resultSet.getString("role")
                        )
                        Result.Success(user)
                    }else{
                        Result.Error("User not found")
                    }
                }
            }catch (e: SQLException){
                e.printStackTrace()
                Result.Error(e.message.toString())
            }
        }
    }
}