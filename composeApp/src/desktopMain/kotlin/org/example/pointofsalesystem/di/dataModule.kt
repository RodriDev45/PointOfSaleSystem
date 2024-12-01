package org.example.pointofsalesystem.di

import org.example.pointofsalesystem.data.datasource.SupabaseClientProvider
import org.example.pointofsalesystem.data.interfaces.AuthRepository
import org.example.pointofsalesystem.data.interfaces.UserRepository
import org.example.pointofsalesystem.data.repository.AuthRepositoryImpl
import org.example.pointofsalesystem.data.repository.ProductRepositoryImpl
import org.example.pointofsalesystem.data.repository.UserRepositoryImpl
import org.example.pointofsalesystem.data.services.GoogleAuthService
import org.example.pointofsalesystem.domain.utils.Config
import org.example.pointofsalesystem.domain.utils.SessionState
import org.koin.dsl.module

val dataModule = module {
    single { SupabaseClientProvider.client }
    single { ProductRepositoryImpl() }
    single { GoogleAuthService(clientId = Config.GOOGLE_CLIENT_ID, clientSecret = Config.GOOGLE_CLIENT_SECRET) }
    single<UserRepository> { UserRepositoryImpl() }
    single<AuthRepository> { AuthRepositoryImpl(get(), get()) }
}
