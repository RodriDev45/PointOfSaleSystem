package org.example.pointofsalesystem.data.repository

import io.github.jan.supabase.SupabaseClient
import io.github.jan.supabase.auth.Auth
import io.github.jan.supabase.auth.auth
import io.github.jan.supabase.auth.providers.Google
import io.github.jan.supabase.auth.providers.builtin.Email
import io.github.jan.supabase.auth.status.SessionStatus
import io.github.jan.supabase.auth.user.UserInfo
import io.github.jan.supabase.postgrest.from
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.serialization.json.buildJsonObject
import kotlinx.serialization.json.put
import org.example.pointofsalesystem.data.model.Profile
import org.example.pointofsalesystem.domain.utils.Result

class AuthRepository(private val supabaseClient: SupabaseClient) {
    private val auth: Auth = supabaseClient.auth

    val isUserAuthenticated: Flow<Boolean> = auth.sessionStatus.map { status ->
        status is SessionStatus.Authenticated
    }

    val sessionStatusFlow: Flow<SessionStatus> = auth.sessionStatus


    // Función para autenticarse con email y contraseña
    suspend fun signInWithEmail(emailUser: String, passwordUser: String): Result<String>{
        return try {
            auth.signInWith(Email) {
                email = emailUser
                password = passwordUser
            }
            Result.Success("Success user logged in")
        } catch (e: Exception) {
            Result.Error(e.message ?: "An unknown error occurred")
        }
    }

    // Función para registrar usuario con email y contraseña
    suspend fun signUpWithEmail(nameUser: String, emailUser: String, passwordUser: String): Result<UserInfo?> {
        return try {
            val userInfo = auth.signUpWith(Email){
                email = emailUser
                password = passwordUser
                data = buildJsonObject {
                    put("name", nameUser)
                }
            }
            Result.Success(userInfo)
        } catch (e: Exception) {
            Result.Error(e.message ?: "An unknown error occurred")
        }
    }

    // Función para autenticarse con Google (OAuth)
    suspend fun signInWithGoogle(): Result<String> {
        return try {
            auth.signInWith(Google)
            Result.Success("Success user logged in")
        } catch (e: Exception) {
            Result.Error(e.message ?: "An unknown error occurred")
        }
    }

    // Función para obtener el usuario actual
    fun getCurrentUser(): Result<UserInfo> {
        return try {
            val user = auth.currentSessionOrNull()?.user
            if(user !== null) Result.Success(user) else Result.Error("User not found")
        }catch (e: Exception){
            Result.Error(e.message ?: "An unknown error occurred")
        }
    }

    suspend fun getProfile(idUser: String): Result<Profile>{
        return try {
            val profile = supabaseClient.from("profiles").select(){
                filter {
                    eq("id", idUser)
                }
            }.decodeSingle<Profile>()
            Result.Success(profile)
        }catch (e: Exception){
            Result.Error(e.message ?: "An unknown error occurred")
        }
    }

    suspend fun signOut() {
        auth.signOut()
    }
}