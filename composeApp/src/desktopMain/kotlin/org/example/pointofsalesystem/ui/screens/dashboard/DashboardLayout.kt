package org.example.pointofsalesystem.ui.screens.dashboard

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.rememberNavController
import org.example.pointofsalesystem.ui.components.sidebars.SidebarDashboard
import org.example.pointofsalesystem.ui.components.widgets.HeaderDashboard
import org.example.pointofsalesystem.ui.navgraph.DashboardNavHost
import org.example.pointofsalesystem.ui.theme.grey50
import org.example.pointofsalesystem.viewmodel.AuthViewModel
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun DashboardLayout(
    authViewModel: AuthViewModel = koinViewModel()
){
    // Controlador interno para manejar las rutas del Dashboard
    val dashboardNavController = rememberNavController()
    val profile = authViewModel.profile.collectAsState()
    Row(
        modifier = Modifier.fillMaxSize(),

    ) {
        SidebarDashboard(
            navController = dashboardNavController,
            onLogOut = { authViewModel.signOut() }
        )

        // Contenido dinámico (NavHost)
        Column(
            modifier = Modifier
                .fillMaxHeight()
                .weight(1f)
                .background(grey50)
                .padding(start=4.dp)
        ) {
            HeaderDashboard(
                imageProfile = if (profile.value != null) profile.value!!.photo_url else null,
            )
            Box(
                modifier = Modifier.fillMaxSize()
            ){
                DashboardNavHost(dashboardNavController)
            }
        }
    }
}