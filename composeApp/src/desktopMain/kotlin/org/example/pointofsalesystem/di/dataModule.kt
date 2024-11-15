package org.example.pointofsalesystem.di

import org.example.pointofsalesystem.data.datasource.SupabaseClientProvider
import org.example.pointofsalesystem.data.repository.AuthRepository
import org.koin.dsl.module

val dataModule = module {
    single { SupabaseClientProvider.client }
    single { AuthRepository(get()) }
}
