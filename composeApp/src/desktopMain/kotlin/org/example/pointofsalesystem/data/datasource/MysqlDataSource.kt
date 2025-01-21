package org.example.pointofsalesystem.data.datasource

import com.zaxxer.hikari.HikariConfig
import com.zaxxer.hikari.HikariDataSource
import java.sql.Connection
import java.sql.DriverManager

object MysqlDataSource {
    private const val URL = "jdbc:mysql://localhost:3306/kanban"
    private const val USER = "root"
    private const val PASSWORD = "root1234"

    fun getConnection(): HikariDataSource {
        val config = HikariConfig().apply {
            jdbcUrl = URL
            username = USER
            password = PASSWORD
            driverClassName = "com.mysql.cj.jdbc.Driver"
            maximumPoolSize = 10 // Máximo de conexiones que el pool puede manejar
            idleTimeout = 600000 // Tiempo de espera antes de considerar una conexión inactiva
            connectionTimeout = 30000 // Tiempo máximo de espera para obtener una conexión del pool
        }

        // Crear y devolver un HikariDataSource
        return HikariDataSource(config)
    }
}