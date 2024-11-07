package org.example.pointofsalesystem.ui.navgraph

import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import androidx.navigation.compose.rememberNavController
import org.example.pointofsalesystem.ui.components.buttons.ButtonPrimary
import org.example.pointofsalesystem.ui.components.text.*
import org.example.pointofsalesystem.ui.screens.login.LoginScreen
import org.jetbrains.compose.resources.painterResource
import pointofsalesystem.composeapp.generated.resources.Res
import pointofsalesystem.composeapp.generated.resources.logo_point

@Composable
fun NavGraph(
    startDestination: String,
){
    var navController = rememberNavController()
    NavHost(
        navController = navController,
        startDestination = startDestination,
        enterTransition = {
            slideIntoContainer(
                AnimatedContentTransitionScope.SlideDirection.Left,
                animationSpec = tween(500)
            )
        },
        exitTransition = {
            slideOutOfContainer(
                AnimatedContentTransitionScope.SlideDirection.Left,
                animationSpec = tween(500)
            )
        },
        popEnterTransition = {
            slideIntoContainer(
                AnimatedContentTransitionScope.SlideDirection.Right,
                animationSpec = tween(500)
            )
        },
        popExitTransition = {
            slideOutOfContainer(
                AnimatedContentTransitionScope.SlideDirection.Right,
                animationSpec = tween(500)
            )
        }
    ) {
        navigation(startDestination = Route.Auth.Login.route, route = Route.Auth.AuthMain.route){
            composable(route = Route.Auth.Login.route) {
                LoginScreen()
            }
            composable(route = Route.Auth.Register.route) {
                Column(
                    modifier = Modifier.fillMaxSize(),
                ) {
                    Text(text = "Register")
                    Button(onClick = { navController.popBackStack()}) {
                        Text(text = "Ir a Login")
                    }
                }
            }
        }
        navigation(startDestination = Route.Dashboard.Home.route, route = Route.Dashboard.Main.route){
            composable(route = Route.Dashboard.Home.route) {

            }
            composable(route = Route.Dashboard.Inventory.route) {

            }
        }
    }
}