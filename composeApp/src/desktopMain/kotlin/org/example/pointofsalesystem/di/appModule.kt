package org.example.pointofsalesystem.di

import org.koin.core.module.Module

val appModule: List<Module> = listOf(
    dataModule,
    domainModule,
    uiModule
)
