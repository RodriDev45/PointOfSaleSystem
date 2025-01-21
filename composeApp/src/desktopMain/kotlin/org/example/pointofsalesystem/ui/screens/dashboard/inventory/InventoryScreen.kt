package org.example.pointofsalesystem.ui.screens.dashboard.inventory

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import org.example.pointofsalesystem.domain.utils.ModalManager
import org.example.pointofsalesystem.domain.utils.ModalTypes
import org.example.pointofsalesystem.ui.screens.dashboard.inventory.components.TableProducts
import org.example.pointofsalesystem.ui.components.containers.ContainerDashboard
import org.example.pointofsalesystem.ui.navgraph.Route
import org.example.pointofsalesystem.ui.screens.dashboard.inventory.components.ModalFilter
import org.example.pointofsalesystem.ui.screens.dashboard.inventory.components.OverallInventory
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun InventoryScreen(
    viewModel: InventoryViewModel = koinViewModel(),
    navController: NavHostController
) {
    LaunchedEffect(Unit) {
        viewModel.getProducts()
        viewModel.getTotalProducts()
        viewModel.getTotalCategories()
        viewModel.getTotalTopCategories()
        viewModel.getTotalValue()
        viewModel.getTotalLowStock()
        viewModel.getTotalOutOfStock()
    }
    ContainerDashboard {
        Column(
            verticalArrangement = Arrangement.spacedBy(16.dp),
        ) {
            OverallInventory(
                totalCategories = viewModel.totalCategories,
                totalProducts = viewModel.totalProducts,
                totalTopCategories = viewModel.totalTopCategories,
                totalValue = viewModel.totalValue,
                totalLowStock = viewModel.totalLowStock,
                totalOutOfStock = viewModel.totalOutOfStock,
            )
            TableProducts(
                products = viewModel.products,
                onNavToAddProduct = {navController.navigate(Route.Dashboard.InventoryAddProduct.route)},
                onPreview = {viewModel.previewPage()},
                onNextPage = {viewModel.nextPage()},
                page = viewModel.page,
                totalPage = viewModel.totalPages,
                onFilter = {
                    ModalManager.showModal(modal = {
                        ModalFilter{ filters->
                            println(filters)
                            viewModel.applyFilters(filters)
                        }
                    })
                }
            )
        }
    }
}