package org.example.pointofsalesystem.domain.utils

import io.ktor.server.engine.*
import io.ktor.server.netty.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

class LocalServer(private val onAuthCodeReceived: suspend(String) -> Unit) {
    private var server: EmbeddedServer<NettyApplicationEngine, NettyApplicationEngine.Configuration>? = null

    fun start() {
        server = embeddedServer(Netty, port = 8080) {
            routing {
                get("/") {
                    val code = call.request.queryParameters["code"]
                    if (code != null) {
                        onAuthCodeReceived(code)
                        call.respondText("Autenticación completada. Puedes cerrar esta ventana.")
                        stop()
                    } else {
                        call.respondText("Error al autenticar.")
                    }
                }
            }
        }.start(wait = false)
    }

    fun stop() {
        server?.stop(1000, 1000)
    }
}