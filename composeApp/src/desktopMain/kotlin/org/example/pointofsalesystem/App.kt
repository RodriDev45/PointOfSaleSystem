package org.example.pointofsalesystem

import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.*
import org.example.pointofsalesystem.ui.navgraph.NavGraph
import org.example.pointofsalesystem.ui.navgraph.Route
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.koin.compose.KoinContext


@Composable
@Preview
fun App() {
    KoinContext {
        MaterialTheme {
            NavGraph(
                startDestination = Route.Auth.AuthMain.route,
            )
        }
    }
}