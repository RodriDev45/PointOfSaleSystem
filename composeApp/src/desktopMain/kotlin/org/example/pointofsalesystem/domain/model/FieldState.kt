package org.example.pointofsalesystem.domain.model

data class FieldState<T>(
    val value: T,       // Valor del campo
    val error: String?  // Mensaje de error (si hay alguno)
)
