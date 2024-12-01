package org.example.pointofsalesystem.domain.utils

import com.auth0.jwt.JWT
import com.auth0.jwt.algorithms.Algorithm
import com.auth0.jwt.exceptions.JWTDecodeException
import com.auth0.jwt.exceptions.JWTVerificationException
import com.auth0.jwt.interfaces.DecodedJWT
import org.example.pointofsalesystem.data.model.User
import java.io.File
import java.util.*

object TokenManager {
    private val configFile = File(System.getProperty("user.home"), ".app_config.properties")

    fun generateJwt(userId: String, email: String, role: String): String {
        val secret = Config.JWT_SECRET_KEY // Reemplázala por algo seguro
        val algorithm = Algorithm.HMAC256(secret)

        return JWT.create()
            .withIssuer("tu-sistema")
            .withSubject(userId)
            .withClaim("email", email)
            .withClaim("role", role)
            .withIssuedAt(Date())
            .withExpiresAt(Date(System.currentTimeMillis() + 3600 * 1000)) // 1 hora
            .sign(algorithm)
    }

    fun validateOwnJwt(token: String?): DecodedJWT? {
        if (token == null) return null
        val secret = Config.JWT_SECRET_KEY
        val algorithm = Algorithm.HMAC256(secret)

        return try {
            val verifier = JWT.require(algorithm).withIssuer("tu-sistema").build()
            val decodedJwt = verifier.verify(token)
            val expirationTime = decodedJwt.expiresAt?.time ?: 0
            val currentTime = System.currentTimeMillis()
            println("JWT válido para usuario: ${decodedJwt.subject}")
            if(currentTime < expirationTime) decodedJwt
            else null// El token es válido si no ha expirado
        } catch (ex: JWTVerificationException) {
            println("JWT inválido: ${ex.message}")
            null
        } catch (e: JWTDecodeException) {
            println("Error al decodificar el token: ${e.message}")
            null
        }
    }

    fun saveToken(token: String) {
        val properties = Properties().apply { setProperty("authToken", token) }
        configFile.outputStream().use { properties.store(it, null) }
    }

    fun getToken(): String? {
        if (!configFile.exists()) return null
        val properties = Properties().apply { configFile.inputStream().use { load(it) } }
        return properties.getProperty("authToken")
    }

    fun clearToken() {
        if (configFile.exists()) configFile.delete()
    }

}