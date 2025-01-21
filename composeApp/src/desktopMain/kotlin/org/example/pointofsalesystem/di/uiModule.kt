package org.example.pointofsalesystem.di

import org.example.pointofsalesystem.ui.screens.dashboard.inventory.InventoryViewModel
import org.example.pointofsalesystem.ui.screens.dashboard.inventory.ModalFilterViewModel
import org.example.pointofsalesystem.ui.screens.dashboard.inventory.product.addproduct.AddProductViewModel
import org.example.pointofsalesystem.ui.screens.dashboard.settings.SettingsViewModel
import org.example.pointofsalesystem.ui.screens.login.LoginViewModel
import org.example.pointofsalesystem.ui.screens.register.RegisterViewModel
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

val uiModule = module {
    viewModel { LoginViewModel(get(), get())}
    viewModel { RegisterViewModel(get(), get())}
    viewModel { AddProductViewModel(get(), get(), get(), get())}
    viewModel { InventoryViewModel(get(), get()) }
    viewModel { SettingsViewModel(get()) }
    viewModel { ModalFilterViewModel(get()) }
}
