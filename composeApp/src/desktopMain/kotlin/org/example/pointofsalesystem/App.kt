package org.example.pointofsalesystem

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import org.example.pointofsalesystem.ui.navgraph.NavGraph
import org.example.pointofsalesystem.ui.navgraph.Route
import org.example.pointofsalesystem.ui.theme.PointOfSaleSystemTheme
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.koin.compose.KoinContext


@Composable
@Preview
fun App() {
    KoinContext {
        PointOfSaleSystemTheme {
            Surface(modifier = Modifier.fillMaxSize()) {
                NavGraph(
                    startDestination = Route.Auth.AuthMain.route,
                )
            }
        }
    }
}