package org.example.pointofsalesystem.ui.navgraph

import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.*
import org.example.pointofsalesystem.ui.components.buttons.ButtonPrimary
import org.example.pointofsalesystem.ui.components.text.Body2
import org.example.pointofsalesystem.ui.screens.login.LoginScreen
import org.example.pointofsalesystem.ui.screens.register.RegisterScreen
import org.example.pointofsalesystem.ui.screens.splash.SplashScreen
import org.example.pointofsalesystem.viewmodel.AuthViewModel
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
            else -> Route.Dashboard.Main.route
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
                LoginScreen(
                    navToRegister = {
                        navController.navigate(route = Route.Auth.Register.route)
                    },
                    navToForwardPassword = {
                        navController.navigate(route = Route.Auth.ForwardPassword.route)
                    }
                )
            }
            composable(route = Route.Auth.Register.route) {
                RegisterScreen(
                    navToLogin = {
                        navController.popBackStack()
                    }
                )
            }
        }
        // Dashboard con sidebar estático
        composable(route = Route.Dashboard.Main.route) {
            DashboardScreen()
        }

    }
}

@Composable
fun DashboardScreen() {
    // Controlador interno para manejar las rutas del Dashboard
    val dashboardNavController = rememberNavController()
    Row(modifier = Modifier.fillMaxSize()) {
        // Sidebar estático
        Column(
            modifier = Modifier
                .width(250.dp)
                .fillMaxHeight()
                .background(Color.Gray)
        ) {
            DashboardSidebar(dashboardNavController)
        }

        // Contenido dinámico (NavHost)
        Box(
            modifier = Modifier
                .fillMaxHeight()
                .weight(1f)
        ) {
            DashboardNavHost(dashboardNavController)
        }
    }
}

@Composable
fun DashboardNavHost(navController: NavHostController) {
    NavHost(
        navController = navController,
        startDestination = Route.Dashboard.Home.route,
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
        composable(route = Route.Dashboard.Home.route) {
            Column(modifier = Modifier.fillMaxSize()) {
                Text("Home Screen")
            }
        }
        composable(route = Route.Dashboard.Inventory.route) {
            Column(modifier = Modifier.fillMaxSize()) {
                Text("Inventory Screen")
            }
        }
    }
}

@Composable
fun DashboardSidebar(
    navController: NavHostController,
    viewModel: AuthViewModel = koinViewModel()
) {
    Column(
        modifier = Modifier.padding(16.dp)
    ) {
        Text("Sidebar")
        Button(onClick = { navController.navigate(Route.Dashboard.Home.route) }) {
            Text("Home")
        }
        Button(onClick = { navController.navigate(Route.Dashboard.Inventory.route) }) {
            Text("Inventory")
        }
        ButtonPrimary("LogOut", onClick = {viewModel.signOut()})
    }
}
