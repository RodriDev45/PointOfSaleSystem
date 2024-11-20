package org.example.pointofsalesystem.di

import org.example.pointofsalesystem.ui.screens.login.LoginViewModel
import org.example.pointofsalesystem.ui.screens.register.RegisterViewModel
import org.example.pointofsalesystem.viewmodel.AuthViewModel
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

val uiModule = module {
    viewModel { LoginViewModel(get(), get())}
    viewModel { RegisterViewModel(get(), get())}
    viewModel { AuthViewModel(get()) }
}
