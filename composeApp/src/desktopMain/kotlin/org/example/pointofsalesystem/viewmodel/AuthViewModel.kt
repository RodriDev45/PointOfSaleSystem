package org.example.pointofsalesystem.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import io.github.jan.supabase.auth.status.SessionSource
import io.github.jan.supabase.auth.status.SessionStatus
import io.github.jan.supabase.auth.user.UserInfo
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import org.example.pointofsalesystem.data.repository.AuthRepository

class AuthViewModel(private val authRepository: AuthRepository): ViewModel() {

    // Estado de carga para controlar el SplashScreen
    private val _isLoading = MutableStateFlow(true)
    val isLoading: StateFlow<Boolean> = _isLoading

    init {
        observeSessionStatus()
    }

    private fun observeSessionStatus() {
        viewModelScope.launch {
            authRepository.sessionStatusFlow.collect { status ->
                when (status) {
                    SessionStatus.Initializing -> {
                        _isLoading.value = true
                        println("Initializing session")
                    }
                    is SessionStatus.Authenticated -> {
                        _isLoading.value = false
                        println("User is authenticated.")
                        when (status.source) {
                            SessionSource.External -> println("Authenticated from an external source")
                            is SessionSource.Refresh -> println("Authenticated via session refresh")
                            is SessionSource.SignIn -> println("Authenticated via sign-in")
                            is SessionSource.SignUp -> println("Authenticated via sign-up")
                            SessionSource.Storage -> println("Authenticated from session storage")
                            SessionSource.Unknown -> println("Authenticated from an unknown source")
                            is SessionSource.UserChanged -> println("User has changed")
                            is SessionSource.UserIdentitiesChanged -> println("User identities have changed")
                            SessionSource.AnonymousSignIn -> println("Authenticated anonymously")
                        }
                    }
                    is SessionStatus.NotAuthenticated -> {
                        _isLoading.value = false
                        if (status.isSignOut) {
                            println("User has signed out")
                        } else {
                            println("User is not authenticated")
                        }
                    }
                    is SessionStatus.RefreshFailure -> {
                        _isLoading.value = false
                        println("Session refresh failed: ${status.cause}")
                    }
                }
            }
        }
    }

    val isUserAuthenticated = authRepository.isUserAuthenticated.stateIn(
        viewModelScope,
        SharingStarted.Eagerly,
        false
    )

    fun signOut() {
        viewModelScope.launch {
            authRepository.signOut()
        }
    }

    fun getUser(): UserInfo? {
        return authRepository.getCurrentUser()
    }
}
