package org.example.pointofsalesystem.domain.usecase.forms

import org.example.pointofsalesystem.domain.model.FormField
import org.example.pointofsalesystem.domain.model.LoginModel

class LoginForm {
    private val _email: FormField<String> = FormField(
        initialValue = "",
        validator = { value ->
            val emailRegex = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$".toRegex()
            if(value.isBlank()){
                "El email es requerido"
            } else if(!emailRegex.matches(value)){
                "Ingresa un email valido"
            } else null
        }
    )
    val email = _email.state

    val _password: FormField<String> = FormField(
        initialValue = "",
        validator = {value ->
            if(value.isEmpty()) {
                "Please enter your password"
            } else if (value.length < 6) {
                "Password must be at least 6 characters long"
            } else null
        }
    )
    val password = _password.state

    fun updateEmail(newEmail: String) {
        _email.update(newEmail)
    }

    fun updatePassword(newPassword: String) {
        _password.update(newPassword)
    }

    fun validate(onSubmit: (data: LoginModel) -> Unit) {
        var isValid = true
        listOf(_email, _password).forEach { field ->
            field.validate()
            if(field.state.value.error != null) isValid = false
        }
        if(isValid) {
            onSubmit(
                LoginModel(
                    email = _email.value,
                    password = _password.value
                )
            )
            listOf(_email, _password).forEach { field -> field.reset()}
        }
    }
}
