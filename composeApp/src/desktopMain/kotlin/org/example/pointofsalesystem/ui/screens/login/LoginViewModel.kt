package org.example.pointofsalesystem.ui.screens.login

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import org.example.pointofsalesystem.data.interfaces.AuthRepository
import org.example.pointofsalesystem.domain.model.FormField
import org.example.pointofsalesystem.domain.model.LoginModel
import org.example.pointofsalesystem.domain.usecase.forms.LoginForm
import org.example.pointofsalesystem.domain.utils.Result


class LoginViewModel(
    private val authRepository: AuthRepository,
    val loginForm: LoginForm
) : ViewModel() {
    var errorAuth by mutableStateOf<String?>(null)
        private set
    var rememberPassword by mutableStateOf(false)
        private set
    var loadingLogin by mutableStateOf(false)

    fun handleChangeEmail(value: String) {
        loginForm.updateEmail(value)
    }
    fun handleChangePassword(value: String) {
        loginForm.updatePassword(value)
    }

    fun handleChangeRememberPassword(value: Boolean) {
        rememberPassword = value
    }

    fun handleSubmit() {
        errorAuth = null
        loginForm.validate{ data ->
            viewModelScope.launch {
                loadingLogin = true
                val result = authRepository.signInWithCredentials(
                    email = data.email,
                    password = data.password,
                )
                if(result is Result.Error) {
                    errorAuth = result.error
                }
                loadingLogin = false
            }
        }
    }

    fun loginGoogle(){
        errorAuth = null
        viewModelScope.launch {
            loadingLogin = true
            val result = authRepository.signInWithGoogle()
            println(result)
            if (result is Result.Error) {
                errorAuth = result.error
            }
            loadingLogin = false
        }
    }


}