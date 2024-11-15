package org.example.pointofsalesystem.ui.navgraph

import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Button
import androidx.compose.material3.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import androidx.navigation.compose.rememberNavController
import kotlinx.coroutines.launch
import org.example.pointofsalesystem.ui.components.buttons.ButtonPrimary
import org.example.pointofsalesystem.ui.components.text.Heading1SemiBold
import org.example.pointofsalesystem.ui.screens.login.LoginScreen
import org.example.pointofsalesystem.ui.screens.splash.SplashScreen
import org.example.pointofsalesystem.ui.theme.Primary600
import org.example.pointofsalesystem.ui.theme.warning500
import org.example.pointofsalesystem.viewmodel.AuthViewModel
import org.jetbrains.compose.resources.painterResource
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun NavGraph(
    authViewModel: AuthViewModel = koinViewModel()
){
    val navController = rememberNavController()
    val isLoading by authViewModel.isLoading.collectAsState()
    val isUserAuthenticated by authViewModel.isUserAuthenticated.collectAsState()

    // Navega según el estado de autenticación y carga
    LaunchedEffect(isLoading, isUserAuthenticated) {
        val destination = when {
            isLoading -> Route.Splash.SplashMain.route
            !isUserAuthenticated -> Route.Auth.AuthMain.route
            else -> Route.Dashboard.Home.route
        }
        navController.navigate(destination)
    }
    NavHost(
        navController = navController,
        startDestination = Route.Splash.SplashMain.route,
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
        navigation(startDestination = Route.Splash.SplashLoading.route, route = Route.Splash.SplashMain.route){
            composable(route = Route.Splash.SplashLoading.route) {
                SplashScreen()
            }
        }
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
                val viewModel: AuthViewModel = koinViewModel()
                Column(modifier = Modifier.fillMaxSize()) {
                    Text(text = "Dashboard")
                    ButtonPrimary("LogOut", onClick = {
                        viewModel.signOut()
                    })
                }
            }
            composable(route = Route.Dashboard.Inventory.route) {

            }
        }
    }
}