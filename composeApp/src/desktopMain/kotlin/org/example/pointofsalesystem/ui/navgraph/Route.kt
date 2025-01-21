package org.example.pointofsalesystem.ui.navgraph

sealed class Route(val route: String) {
    // Rutas de autenticación agrupadas
    sealed class Auth(route: String) : Route(route) {
        object AuthMain : Auth("auth")
        object Login : Auth("auth/login")
        object Register : Auth("auth/register")
        object ForwardPassword : Auth("auth/forwardPassword")
    }

    sealed class Splash(route: String) : Route(route) {
        object SplashMain : Splash("splash")
        object SplashLoading : Splash("loading")
    }

    // Rutas del panel de control agrupadas
    sealed class Dashboard(route: String) : Route(route) {
        object Main : Dashboard("dashboard")
        object Home : Dashboard("dashboard/home")
        object Inventory : Dashboard("dashboard/inventory")
        object InventoryAddProduct : Dashboard("dashboard/inventory/add-product")

        object Reports : Dashboard("dashboard/reports")
        object Suppliers : Dashboard("dashboard/suppliers")
        object Orders : Dashboard("dashboard/orders")
        object ManageStore : Dashboard("dashboard/manage_store")
    }
}