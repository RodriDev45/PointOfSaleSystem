package org.example.pointofsalesystem.di

import org.example.pointofsalesystem.ui.screens.login.LoginViewModel
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

val uiModule = module {
    viewModel { LoginViewModel()}
}
