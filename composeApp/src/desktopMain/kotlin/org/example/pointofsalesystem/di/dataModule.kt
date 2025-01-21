package org.example.pointofsalesystem.di

import com.zaxxer.hikari.HikariDataSource
import org.example.pointofsalesystem.data.datasource.MysqlDataSource
import org.example.pointofsalesystem.data.datasource.SupabaseClientProvider
import org.example.pointofsalesystem.data.interfaces.*
import org.example.pointofsalesystem.data.repository.*
import org.example.pointofsalesystem.data.services.GoogleAuthService
import org.example.pointofsalesystem.domain.utils.Config
import org.example.pointofsalesystem.domain.utils.SessionState
import org.koin.dsl.module

val dataModule = module {
    single { SupabaseClientProvider.client }
    single { MysqlDataSource.getConnection() }
    single<ProductRepository> { ProductRepositoryImpl(get()) }
    single { GoogleAuthService(clientId = Config.GOOGLE_CLIENT_ID, clientSecret = Config.GOOGLE_CLIENT_SECRET) }
    single<UserRepository> { UserRepositoryImpl(get ()) }
    single<AuthRepository> { AuthRepositoryImpl(get(), get()) }
    single<CategoryRepository> { CategoryRepositoryImpl(get())}
    single<OverallInventoryRepository> { OverallInventoryRepositoryImpl(get()) }
}
