package org.example.pointofsalesystem.ui.navgraph

import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import androidx.navigation.compose.rememberNavController
import org.example.pointofsalesystem.ui.components.text.*

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
                Column(
                    modifier = Modifier.fillMaxSize(),
                ) {
                    Heading1("Login")
                    Heading1Medium("Login")
                    Heading1SemiBold("Login")
                    Heading1Bold("Login")
                    Heading2("Login")
                    Heading2Medium("Login")
                    Heading2SemiBold("Login")
                    Heading2Bold("Login")
                    SubHeading("Login")
                    SubHeadingMedium("Login")
                    SubHeadingSemiBold("Login")
                    SubHeadingBold("Login")
                    Body1("Login")
                    Body1Medium("Login")
                    Body1SemiBold("Login")
                    Body1Bold("Login")
                    Body2("Login")
                    Body2Medium("Login")
                    Body2SemiBold("Login")
                    Body2Bold("Login")

                    Button(onClick = { navController.navigate(route = Route.Auth.Register.route) }) {
                        Text(text = "Ir a Register")
                    }
                }
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