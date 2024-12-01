package org.example.pointofsalesystem.domain.utils

import org.mindrot.jbcrypt.BCrypt
import java.util.UUID

fun generateUUID(): String {
    return UUID.randomUUID().toString()
}

fun hashPassword(password: String): String {
    val salt = BCrypt.gensalt()  // Genera un "salt" aleatorio
    return BCrypt.hashpw(password, salt)  // Hashea la contraseña con el salt
}

fun verifyPassword(plainPassword: String, hashedPassword: String): Boolean {
    return BCrypt.checkpw(plainPassword, hashedPassword)
}
