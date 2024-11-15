package org.example.pointofsalesystem.data.repository

import io.github.jan.supabase.SupabaseClient
import io.github.jan.supabase.auth.Auth
import io.github.jan.supabase.auth.auth
import io.github.jan.supabase.auth.providers.Google
import io.github.jan.supabase.auth.providers.builtin.Email
import io.github.jan.supabase.auth.status.SessionStatus
import io.github.jan.supabase.auth.user.UserInfo
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.serialization.json.buildJsonObject
import kotlinx.serialization.json.put

class AuthRepository(private val supabaseClient: SupabaseClient) {
    private val auth: Auth = supabaseClient.auth

    val isUserAuthenticated: Flow<Boolean> = auth.sessionStatus.map { status ->
        status is SessionStatus.Authenticated
    }

    val sessionStatusFlow: Flow<SessionStatus> = auth.sessionStatus


    // Función para autenticarse con email y contraseña
    suspend fun signInWithEmail(emailUser: String, passwordUser: String){
        return try {
            auth.signInWith(Email) {
                email = emailUser
                password = passwordUser
            }
        } catch (e: Exception) {
            println("Error: ${e.message}")
        }
    }

    // Función para registrar usuario con email y contraseña
    suspend fun signUpWithEmail(nameUser: String, emailUser: String, passwordUser: String): UserInfo? {
        return try {
            auth.signUpWith(Email){
                email = emailUser
                password = passwordUser
                data = buildJsonObject {
                    put("name", nameUser)
                }
            }
        } catch (e: Exception) {
            null
        }
    }

    // Función para autenticarse con Google (OAuth)
    suspend fun signInWithGoogle() {
        return try {
            auth.signInWith(Google)
        } catch (e: Exception) {
            println("Error: ${e.message}")
        }
    }

    // Función para obtener el usuario actual
    fun getCurrentUser(): UserInfo? {
        return auth.currentSessionOrNull()?.user
    }

    suspend fun signOut() {
        auth.signOut()
    }
}