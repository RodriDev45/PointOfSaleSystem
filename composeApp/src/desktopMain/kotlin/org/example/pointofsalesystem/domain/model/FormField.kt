package org.example.pointofsalesystem.domain.model

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

// Cambiamos el FormField para usar StateFlow
class FormField<T>(
    val initialValue: T,
    private val validator: (T) -> String? = { null }
) {
    // MutableStateFlow para el valor y error
    private val _state = MutableStateFlow(FieldState(initialValue, null))
    val state: StateFlow<FieldState<T>> get() = _state

    // Obtenemos el valor actual del campo
    val value: T
        get() = _state.value.value

    // Actualizamos el valor y la validación
    fun update(newValue: T) {
        val error = validator(newValue)
        _state.value = FieldState(newValue, error)
    }

    fun validate(){
        val error = validator(value)
        _state.value = FieldState(value, error)
    }

    fun reset(){
        _state.value = FieldState(initialValue, null)
    }
}
