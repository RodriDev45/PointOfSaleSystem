package org.example.pointofsalesystem.ui.screens.login

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import org.example.pointofsalesystem.data.repository.AuthRepository
import org.example.pointofsalesystem.domain.model.FieldValue
import org.example.pointofsalesystem.domain.model.LoginModel


class LoginViewModel(
    private val authRepository: AuthRepository
) : ViewModel() {

    val isUserAuthenticated = authRepository.isUserAuthenticated.stateIn(
        viewModelScope,
        SharingStarted.Eagerly,
        false
    )

    var rememberPassword by mutableStateOf(false)
        private set
    var email by  mutableStateOf(FieldValue(
        value = "",
        error = null,
    ))
        private set
    var password by mutableStateOf(FieldValue(
        value = "",
        error = null,
    ))
        private set

    fun handleChangeRemember(value: Boolean) {
        rememberPassword = value
    }

    fun handleChangeEmail(value: String) {
        val emailRegex = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$".toRegex()
        val error = if (value.isEmpty()) {
            "The email is required."
        }else if(!emailRegex.matches(value)) {
            "Please enter a valid email"
        } else null
        email = FieldValue(
            value = value,
            error = error,
        )
    }
    fun handleChangePassword(value: String) {
        val error = if (value.isEmpty()) {
            "Please enter your password"
        } else if (value.length < 6) {
            "Password must be at least 6 characters long"
        }
        else null
        password = FieldValue(
            value = value,
            error = error,
        )
    }

    fun handleSubmit(onSubmit: (data: LoginModel) -> Unit) {
        handleChangeEmail(email.value)
        handleChangePassword(password.value)

        if(email.error.isNullOrEmpty() && password.error.isNullOrEmpty()) {
            onSubmit(LoginModel(
                email.value,
                password.value,
            ))
        }
    }

    fun login(data: LoginModel) {
        println(data)
        viewModelScope.launch {
            authRepository.signInWithGoogle()
        }
    }

    fun loginGoogle(){
        viewModelScope.launch {
            authRepository.signInWithGoogle()
        }
    }

}