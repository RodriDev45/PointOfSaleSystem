package org.example.pointofsalesystem.domain.utils

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import org.example.pointofsalesystem.data.interfaces.UserRepository
import org.example.pointofsalesystem.data.model.User
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

object SessionState : KoinComponent {
    val userRepository: UserRepository by inject()
    var isLoggedIn by mutableStateOf(false)
        private set
    var loadingSession by mutableStateOf(false)
        private set
    var user by mutableStateOf<User?>(null)
        private set

    fun login(token: String, userLogin: User) {
        TokenManager.saveToken(token)
        user = userLogin
        isLoggedIn = true
    }

    fun logout() {
        TokenManager.clearToken()
        isLoggedIn = false
    }

    suspend fun checkSession() {
        loadingSession = true
        val token = TokenManager.getToken()
        val jwt = TokenManager.validateOwnJwt(token)
        if(jwt != null && jwt.subject != null) {
            val result = userRepository.getUserById(jwt.subject)
            if(result is Result.Success) {
                user = result.data
                isLoggedIn = true
            }
        }
        loadingSession = false
    }
}
