package org.example.pointofsalesystem.data.datasource
import java.sql.Connection
import java.sql.DriverManager

object OracleDataSource {
    private const val URL = "jdbc:oracle:thin:@localhost:1521:xe"
    private const val USER = "system"
    private const val PASSWORD = "root1234"

    fun getConnection(): Connection {
        return DriverManager.getConnection(URL, USER, PASSWORD)
    }
}