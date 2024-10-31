package org.example.pointofsalesystem

import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import org.example.pointofsalesystem.di.appModule
import org.koin.core.context.startKoin

fun main(){
    startKoin {
        modules(appModule)
    }
    application {
        Window(
            onCloseRequest = ::exitApplication,
            title = "PointOfSaleSystem",
        ) {
            App()
        }
    }
}