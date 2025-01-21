package org.example.pointofsalesystem.di

import org.example.pointofsalesystem.domain.usecase.forms.AddProductForm
import org.example.pointofsalesystem.domain.usecase.forms.LoginForm
import org.example.pointofsalesystem.domain.usecase.forms.RegisterForm
import org.example.pointofsalesystem.domain.usecase.forms.SettingsForm
import org.koin.dsl.module

val domainModule = module {
    single { LoginForm() }
    single { RegisterForm() }
    single { AddProductForm() }
    single { SettingsForm() }
}
