package org.example.pointofsalesystem.data.model

import kotlinx.serialization.*

@Serializable
data class TokenGoogle(
    val access_token: String,
    val expires_in: Long,
    val scope: String,
    val token_type: String,
    val id_token: String
)
