package org.example.pointofsalesystem.data.model
import kotlinx.serialization.*

@Serializable
data class Profile(
    val id: String,
    val name: String,
    val email: String,
    val photo_url: String?,
    val photo_path: String?,
)
