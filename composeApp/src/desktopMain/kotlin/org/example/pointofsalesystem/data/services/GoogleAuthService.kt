package org.example.pointofsalesystem.data.services

import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.engine.cio.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.http.*
import io.ktor.serialization.kotlinx.json.*
import kotlinx.serialization.json.Json
import org.example.pointofsalesystem.data.model.JwtPayload
import org.example.pointofsalesystem.data.model.TokenGoogle
import java.net.URLEncoder
import java.util.*
import com.auth0.jwk.JwkProviderBuilder
import com.auth0.jwt.JWT
import com.auth0.jwt.algorithms.Algorithm
import com.auth0.jwt.exceptions.JWTVerificationException
import java.net.URL
import java.util.concurrent.TimeUnit


class GoogleAuthService(
    private val clientId: String,
    private val clientSecret: String
) {
    private val redirectUri = "http://localhost:8080/"

    fun generateAuthUrl(): String {
        val encodedRedirectUri = URLEncoder.encode(redirectUri, "UTF-8")
        val encodedScope = URLEncoder.encode("email profile", "UTF-8")

        return "https://accounts.google.com/o/oauth2/auth" +
                "?client_id=$clientId" +
                "&redirect_uri=$encodedRedirectUri" +
                "&response_type=code" +
                "&scope=$encodedScope"
    }

    suspend fun exchangeCodeForToken(code: String): TokenGoogle? {
        val client = HttpClient(CIO){
            install(ContentNegotiation) {
                json(Json {
                    prettyPrint = true
                    ignoreUnknownKeys = true
                })
            }
        }
        val response: HttpResponse = client.post("https://oauth2.googleapis.com/token") {
            contentType(ContentType.Application.FormUrlEncoded) // Cambia el tipo de contenido a FormUrlEncoded
            setBody(
                "code=$code" +
                "&client_id=$clientId" +
                "&client_secret=$clientSecret" +
                "&redirect_uri=$redirectUri" +
                "&grant_type=authorization_code"
            )
        }
        client.close()
        return if (response.status.isSuccess()) response.body<TokenGoogle>() else null
    }

    private fun decodeJwt(token: String): JwtPayload {
        val parts = token.split(".") // Divide el token en header, payload y signature
        if (parts.size != 3) {
            throw IllegalArgumentException("Token inválido")
        }

        val payload = parts[1] // Segunda parte es el payload
        val decodedBytes = Base64.getUrlDecoder().decode(payload)
        val decodedString = String(decodedBytes, Charsets.UTF_8)
        val jsonConfig = Json {
            ignoreUnknownKeys = true // Ignora claves desconocidas en el JSON
        }

        return jsonConfig.decodeFromString(JwtPayload.serializer(), decodedString) // Convierte el JSON a un Map
    }

    fun getUserPayload(token: TokenGoogle): JwtPayload? {
        return if(validateGoogleJwt(token.id_token) == true) decodeJwt(token.id_token) else null
    }

    private fun validateGoogleJwt(jwtToken: String): Boolean {
        val googleJwksUrl = "https://www.googleapis.com/oauth2/v3/certs"
        val provider = JwkProviderBuilder(URL(googleJwksUrl))
            .cached(10, 24, TimeUnit.HOURS) // Caché de 10 claves por 24 horas
            .rateLimited(10, 1, TimeUnit.MINUTES) // Límite de 10 solicitudes por minuto
            .build()

        return try {
            // Decodifica el JWT sin validar para obtener el `kid`
            val decodedJwt = JWT.decode(jwtToken)
            val kid = decodedJwt.keyId

            // Obtén la clave pública correspondiente al `kid`
            val jwk = provider[kid]
            val publicKey = jwk.publicKey

            // Verifica el JWT con la clave pública y el algoritmo
            val algorithm = Algorithm.RSA256(publicKey as java.security.interfaces.RSAPublicKey, null)
            val verifier = JWT.require(algorithm).withIssuer("https://accounts.google.com").build()

            verifier.verify(jwtToken) // Si no lanza excepción, es válido
            println("JWT válido: ${decodedJwt.subject}")
            true
        } catch (ex: JWTVerificationException) {
            println("Error al verificar JWT: ${ex.message}")
            false
        } catch (ex: Exception) {
            println("Error general: ${ex.message}")
            false
        }
    }
}
