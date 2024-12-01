package org.example.pointofsalesystem.data.model

import kotlinx.serialization.Serializable

@Serializable
data class JwtPayload(
    val iss: String,
    val azp: String,
    val aud: String,
    val sub: String,
    val email: String,
    val email_verified: Boolean,
    val at_hash: String,
    val name: String,
    val picture: String,
    val given_name: String,
    val family_name: String,
    val iat: Long,
    val exp: Long
)
