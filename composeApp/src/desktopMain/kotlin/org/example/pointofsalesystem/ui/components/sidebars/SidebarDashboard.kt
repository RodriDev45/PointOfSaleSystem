package org.example.pointofsalesystem.ui.components.sidebars

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import org.example.pointofsalesystem.ui.components.text.Body1Medium
import org.example.pointofsalesystem.ui.components.text.SubHeading
import org.example.pointofsalesystem.ui.components.widgets.Logo
import org.example.pointofsalesystem.ui.navgraph.Route
import org.example.pointofsalesystem.ui.theme.Primary500
import org.example.pointofsalesystem.ui.theme.grey600
import org.jetbrains.compose.resources.DrawableResource
import org.jetbrains.compose.resources.painterResource
import org.koin.compose.viewmodel.koinViewModel
import pointofsalesystem.composeapp.generated.resources.*
import pointofsalesystem.composeapp.generated.resources.Res
import pointofsalesystem.composeapp.generated.resources.home

data class NavItem(
    val id: Int,
    val text: String,
    val icon: DrawableResource,
    val route: String
)

@Composable
fun SidebarDashboard(
    navController: NavHostController,
    onLogOut: ()->Unit
){
    val navItems = listOf(
        NavItem(
            id = 1,
            text = "Dashboard",
            icon = Res.drawable.home,
            route = Route.Dashboard.Home.route
        ),
        NavItem(
            id = 2,
            text = "Inventory",
            icon = Res.drawable.package_open,
            route = Route.Dashboard.Inventory.route
        ),
        NavItem(
            id = 3,
            text = "Reports",
            icon = Res.drawable.chart,
            route = Route.Dashboard.Reports.route
        ),
        NavItem(
            id = 4,
            text = "Suppliers",
            icon = Res.drawable.user,
            route = Route.Dashboard.Suppliers.route
        ),
        NavItem(
            id = 5,
            text = "Orders",
            icon = Res.drawable.box,
            route = Route.Dashboard.Orders.route
        ),
        NavItem(
            id = 6,
            text = "Manage Store",
            icon = Res.drawable.file_text,
            route = Route.Dashboard.Orders.route
        ),
    )
    var idSelectItem by remember { mutableStateOf(1) }
    Column(
        modifier = Modifier.background(Color.White)
            .fillMaxHeight()
            .width(250.dp)
            .padding(16.dp),
        verticalArrangement = Arrangement.SpaceBetween,
    ) {
        Column {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(8.dp),
            ) {
                Logo(
                    width = 50.dp,
                    height = 50.dp,
                )
                SubHeading(
                    text = "KANBAN",
                    color = Primary500,
                )
            }
            Spacer(modifier = Modifier.height(32.dp))
            LazyColumn(
                verticalArrangement = Arrangement.spacedBy(24.dp),
            ) {

                items(navItems) { navItem ->
                    ItemNavSidebarDashboard(
                        icon = navItem.icon,
                        text = navItem.text,
                        isSelected = navItem.id == idSelectItem
                    ){
                        if(navItem.id != idSelectItem){
                            idSelectItem = navItem.id
                            navController.navigate(navItem.route)
                        }
                    }
                }
            }
        }
        Column(
            verticalArrangement = Arrangement.spacedBy(24.dp),
        ) {
            ItemNavSidebarDashboard(
                icon = Res.drawable.settings,
                text = "Settings",
            ){

            }
            ItemNavSidebarDashboard(
                icon = Res.drawable.log_out,
                text = "Log Out",
                onClick = onLogOut
            )
        }
    }
}

@Composable
fun ItemNavSidebarDashboard(
    icon: DrawableResource,
    text: String,
    isSelected: Boolean = false,
    onClick: () -> Unit
){
    Row(
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.clickable(onClick = onClick)
    ) {
        Image(
            painter = painterResource(icon),
            contentDescription = null,
            modifier = Modifier.size(24.dp),
            colorFilter = ColorFilter.tint(if(isSelected) Primary500 else grey600)
        )
        Body1Medium(
            text = text,
            color = if(isSelected) Primary500 else grey600,
        )
    }
}