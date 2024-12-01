package org.example.pointofsalesystem.data.interfaces

import org.example.pointofsalesystem.domain.utils.Result

interface AuthRepository {
    suspend fun signInWithGoogle(): Result<String>
    suspend fun registerWithCredentials(name: String, email: String, password: String): Result<String>
    suspend fun signInWithCredentials(email: String, password: String): Result<String>
}