package org.example.pointofsalesystem.data.model

data class User(
    val id: String,
    val name: String,
    val email: String,
    val password: String?,
    val picture: String?,
    val googleId: String?,
    val role: String = "employee"
)
