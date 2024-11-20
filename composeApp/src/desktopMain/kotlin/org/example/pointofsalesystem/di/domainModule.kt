package org.example.pointofsalesystem.di

import org.example.pointofsalesystem.domain.usecase.forms.LoginForm
import org.example.pointofsalesystem.domain.usecase.forms.RegisterForm
import org.koin.dsl.module

val domainModule = module {
    single { LoginForm() }
    single { RegisterForm() }
}
