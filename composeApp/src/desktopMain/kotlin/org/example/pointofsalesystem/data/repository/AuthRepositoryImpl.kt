package org.example.pointofsalesystem.data.repository

import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.engine.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import io.ktor.server.cio.*
import kotlinx.coroutines.CompletableDeferred
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.example.pointofsalesystem.data.interfaces.AuthRepository
import org.example.pointofsalesystem.data.interfaces.UserRepository
import org.example.pointofsalesystem.data.model.User
import org.example.pointofsalesystem.data.services.GoogleAuthService
import org.example.pointofsalesystem.domain.utils.*
import java.awt.Desktop
import java.net.URI

class AuthRepositoryImpl(
    private val googleAuthService: GoogleAuthService,
    private val userRepository: UserRepository
) : AuthRepository {

    override suspend fun signInWithGoogle(): Result<String> {
        val authCodeDeferred = CompletableDeferred<String>()

        // Iniciar el servidor local en un hilo aparte
        val server = startLocalServer(authCodeDeferred)

        try {
            // Generar la URL de autenticación y abrir en el navegador
            val authUrl = googleAuthService.generateAuthUrl()
            Desktop.getDesktop().browse(URI(authUrl))

            // Esperar el código de autorización
            val authCode = authCodeDeferred.await()

            // Manejar la autenticación con el código recibido
            return handleAuthGoogle(authCode)

        } catch (e: Exception) {
            return Result.Error(e.message ?: "Something went wrong")
        } finally {
            server.stop(1000, 2000) // Detener el servidor
        }
    }

    private suspend fun handleAuthGoogle(code: String): Result<String> {
        return try {
            val token = googleAuthService.exchangeCodeForToken(code)
            println(token)
            val payload = token?.let { googleAuthService.getUserPayload(it) }
            if (payload != null) {
                println("Usuario autenticado: $payload")
                val resultEmail = userRepository.getUserByEmail(payload.email)
                when(resultEmail) {
                    is Result.Success -> {
                        println("Usuario ya registrado solo se obtiene")
                        val user = resultEmail.data
                        println("GoogleId: ${user.googleId == payload.sub}")
                        println("Password: ${user.password}")
                        if(user.googleId == payload.sub && user.password == null){
                            val jwtToken = TokenManager.generateJwt(
                                user.id,
                                user.name,
                                user.email,
                            )
                            SessionState.login(jwtToken, user)
                            println("Luego de ejecutarse")
                            Result.Success("User logged in successfully")
                        }else{
                            Result.Error("This user is being targeted by credentials")
                        }
                    }
                    is Result.Error -> {
                        println("Usuario no registradro se crea uno nuevo")
                        val newUser = User(
                            id = generateUUID(),
                            name = payload.name,
                            email = payload.email,
                            password = null,
                            googleId = payload.sub,
                            picture = payload.picture,
                        )
                        val result = userRepository.insertUser(newUser)
                        when(result){
                            is Result.Success ->{
                                val jwtToken = TokenManager.generateJwt(
                                    newUser.id,
                                    newUser.name,
                                    newUser.email,
                                )
                                SessionState.login(jwtToken, newUser)
                                Result.Success(result.data)
                            }
                            is Result.Error -> {
                                Result.Error(result.error)
                            }
                        }
                    }
                }
            } else {
                Result.Error("Invalid token")
            }
        } catch (e: Exception) {
            e.printStackTrace()
            Result.Error(e.message ?: "Something went wrong")
        }
    }

    private fun startLocalServer(authCodeDeferred: CompletableDeferred<String>): EmbeddedServer<CIOApplicationEngine, CIOApplicationEngine.Configuration> {
        return embeddedServer(CIO, port = 8080) {
            routing {
                get("/") {
                    val code = call.request.queryParameters["code"]
                    if (code != null) {
                        // Devolver el código de autorización
                        authCodeDeferred.complete(code)
                        call.respondText(
                            "Autenticación completada. Puedes cerrar esta ventana.",
                            ContentType.Text.Html
                        )
                    } else {
                        call.respondText(
                            "Error: No se recibió ningún código de autenticación.",
                            ContentType.Text.Html
                        )
                    }
                }
            }
        }.start(wait = false) // Ejecutar el servidor en un hilo aparte
    }

    override suspend fun registerWithCredentials(name: String, email: String, password: String): Result<String> {
        val result = userRepository.getUserByEmail(email)
        return when(result) {
            is Result.Success -> {
                Result.Error("Already registered user")
            }
            is Result.Error -> {
                val newUser = User(
                    id = generateUUID(),
                    name = name,
                    email = email,
                    password = hashPassword(password),
                    picture = null,
                    googleId = null
                )
                val userResult = userRepository.insertUser(newUser)
                when(userResult){
                    is Result.Success -> {
                        val jwtToken = TokenManager.generateJwt(
                            newUser.id,
                            newUser.name,
                            newUser.email,
                        )
                        SessionState.login(jwtToken, newUser)
                        Result.Success("User registered successfully")
                    }
                    is Result.Error -> {
                        Result.Error(userResult.error)
                    }
                }
            }
        }
    }

    override suspend fun signInWithCredentials(email: String, password: String): Result<String> {
        val result = userRepository.getUserByEmail(email)
        return when (result) {
            is Result.Success -> {
                val user = result.data
                if(user.googleId == null && user.password !== null) {
                    if(verifyPassword(password, user.password)){
                        val jwtToken = TokenManager.generateJwt(
                            user.id,
                            user.name,
                            user.email,
                        )
                        SessionState.login(jwtToken, user)
                        Result.Success("Correct credentials")
                    }else{
                        Result.Error("The password is incorrect")
                    }
                }else{
                    Result.Error("This user is being targeted by Google")
                }
            }
            is Result.Error -> {
                Result.Error(result.error)
            }
        }
    }
}
