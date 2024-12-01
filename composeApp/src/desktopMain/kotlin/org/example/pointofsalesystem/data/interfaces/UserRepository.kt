package org.example.pointofsalesystem.data.interfaces

import org.example.pointofsalesystem.data.model.User
import org.example.pointofsalesystem.domain.utils.Result

interface UserRepository {
    suspend fun insertUser(newUser: User): Result<String>
    suspend fun updateUser(user: User): Result<User>
    suspend fun deleteUser(user: User): Result<User>
    suspend fun getUserById(userId: String): Result<User>
    suspend fun getUserByEmail(email: String): Result<User>
}