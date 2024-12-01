package org.example.pointofsalesystem.ui.screens.register

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import org.example.pointofsalesystem.data.interfaces.AuthRepository
import org.example.pointofsalesystem.domain.model.LoginModel
import org.example.pointofsalesystem.domain.usecase.forms.LoginForm
import org.example.pointofsalesystem.domain.usecase.forms.RegisterForm
import org.example.pointofsalesystem.domain.utils.Result

class RegisterViewModel (
    private val authRepository: AuthRepository,
    val registerForm: RegisterForm
) : ViewModel() {
    var errorAuth by mutableStateOf<String?>(null)
        private set
    var loadingRegister by mutableStateOf(false)
        private set

    fun handleChangeEmail(value: String) {
        registerForm.updateEmail(value)
    }
    fun handleChangePassword(value: String) {
        registerForm.updatePassword(value)
    }

    fun handleChangeName(value: String) {
        registerForm.updateName(value)
    }

    fun handleSubmit(){
        errorAuth = null
        registerForm.validate{ data ->
             viewModelScope.launch {
                 loadingRegister = true
                 val result = authRepository.registerWithCredentials(
                    name = data.name,
                    email = data.email,
                    password = data.password,
                )
                if(result is Result.Error){
                    errorAuth = result.error
                }
                 loadingRegister = false
            }
        }
    }

    fun loginGoogle(){
        errorAuth = null
        viewModelScope.launch {
            loadingRegister = true
            val result = authRepository.signInWithGoogle()
            if(result is Result.Error){
                errorAuth = result.error
            }
            loadingRegister = false
        }
    }
}