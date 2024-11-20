package org.example.pointofsalesystem

import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.WindowState
import androidx.compose.ui.window.application
import org.example.pointofsalesystem.di.appModule
import org.koin.core.context.startKoin

fun main(){
    startKoin {
        modules(appModule)
    }
    application {
        val windowState = WindowState(
            placement = androidx.compose.ui.window.WindowPlacement.Maximized  // Indica que el tamaño de la ventana será ajustado automáticamente
        )
        Window(
            onCloseRequest = ::exitApplication,
            state = windowState,
            title = "PointOfSaleSystem",
        ) {
            App()
        }
    }
}