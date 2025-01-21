package org.example.pointofsalesystem.domain.usecase.forms

import org.example.pointofsalesystem.domain.model.FormField
import org.example.pointofsalesystem.domain.model.LoginModel
import org.example.pointofsalesystem.domain.model.SettingModel

class SettingsForm {
    private val _email: FormField<String> = FormField(
        initialValue = "",
        validator = { value ->
            null
        }
    )
    val email = _email.state

    private val _role: FormField<String> = FormField(
        initialValue = "",
        validator = { value ->
            if(value != "admin" || value != "employee"){
                "The role is incorrect"
            }else null
        }
    )
    val role = _role.state

    val _name: FormField<String> = FormField(
        initialValue = "",
        validator = {value ->
            if(value.isEmpty()) {
                "Please enter your name"
            } else null
        }
    )
    val name = _name.state

    fun setName(value: String){
        _name.update(value)
    }

    fun setEmail(value: String){
        _email.update(value)
    }

    fun setRole(value: String){
        _role.update(value)
    }

    fun validate(onSubmit: (data: SettingModel) -> Unit) {
        var isValid = true
        listOf(_email, _name, _role).forEach { field ->
            field.validate()
            if(field.state.value.error != null) isValid = false
        }
        if(isValid) {
            onSubmit(
                SettingModel(
                    email = _email.value,
                    name = _name.value,
                    role = _role.value
                )
            )
        }
    }
}