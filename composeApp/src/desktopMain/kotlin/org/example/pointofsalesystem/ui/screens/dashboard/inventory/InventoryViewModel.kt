package org.example.pointofsalesystem.ui.screens.dashboard.inventory

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import io.ktor.client.*
import io.ktor.client.engine.cio.*
import io.ktor.client.plugins.websocket.*
import io.ktor.http.*
import io.ktor.network.sockets.*
import io.ktor.server.application.*
import io.ktor.server.engine.*
import io.ktor.server.netty.*
import io.ktor.server.request.*
import io.ktor.server.routing.*
import io.ktor.websocket.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext
import org.example.pointofsalesystem.data.interfaces.OverallInventoryRepository
import org.example.pointofsalesystem.data.interfaces.ProductRepository
import org.example.pointofsalesystem.data.model.Product
import org.example.pointofsalesystem.domain.model.InventoryFilterModel
import org.example.pointofsalesystem.domain.utils.Result
import kotlin.math.ceil
import java.util.*
import java.net.ServerSocket
import java.net.Socket
import kotlin.concurrent.thread


class InventoryViewModel (
    private val productRepository: ProductRepository,
    private val overallInventoryRepository: OverallInventoryRepository
): ViewModel() {
    var totalCategories by mutableStateOf(0)
    var totalProducts by mutableStateOf(0)
    var totalTopCategories by mutableStateOf(0)
    var totalValue by mutableStateOf(0.0)
    var totalLowStock by mutableStateOf(0)
    var totalOutOfStock by mutableStateOf(0)

    var filter by mutableStateOf(InventoryFilterModel(
        searchText = null,
        quantity = null,
        salePrice = null,
        category = null,
        inStock = false,
        lowStock = false,
        outOfStock = false,
        brand = null
    ))
        private set

    var loading by mutableStateOf(false)
        private set
    var page by mutableStateOf(1)
        private set
    var products by mutableStateOf<List<Product>>(emptyList())
        private set
    var totalPages by mutableStateOf(10)
        private set
    private val pageSize = 6

    fun getTotalCategories(){
        viewModelScope.launch(Dispatchers.IO) {
            val result = overallInventoryRepository.getTotalCategories()
            if(result is Result.Success){
                withContext(Dispatchers.Main){
                    totalCategories = result.data
                }
            }
        }
    }

    fun getTotalProducts(){
        viewModelScope.launch(Dispatchers.IO) {
            val result = overallInventoryRepository.getTotalProducts()
            println(result)
            if(result is Result.Success){
                withContext(Dispatchers.Main){
                    totalProducts = result.data
                }
            }
        }
    }
    fun getTotalTopCategories(){
        viewModelScope.launch(Dispatchers.IO) {
            val result = overallInventoryRepository.getTopCategories()
            println(result)
            if(result is Result.Success){
                withContext(Dispatchers.Main){
                    totalTopCategories = result.data
                }
            }
        }
    }

    fun getTotalValue(){
        viewModelScope.launch(Dispatchers.IO) {
            val result = overallInventoryRepository.getTotalValue()
            println(result)
            if(result is Result.Success){
                withContext(Dispatchers.Main){
                    totalValue = result.data
                }
            }
        }
    }

    fun getTotalLowStock(){
        viewModelScope.launch(Dispatchers.IO) {
            val result = overallInventoryRepository.getTotalLowStock()
            println(result)
            if(result is Result.Success){
                withContext(Dispatchers.Main){
                    totalLowStock = result.data
                }
            }
        }
    }

    fun getTotalOutOfStock(){
        viewModelScope.launch(Dispatchers.IO) {
            val result = overallInventoryRepository.getTotalOutOfStock()
            println(result)
            if(result is Result.Success){
                withContext(Dispatchers.Main){
                    totalOutOfStock = result.data
                }
            }
        }
    }


    fun startServer() {
        viewModelScope.launch(Dispatchers.IO) {
            val serverSocket = ServerSocket(12345) // Puerto donde el servidor escucha
            println("Servidor iniciado en el puerto 12345")

            while (true) {
                val client: Socket = serverSocket.accept()
                println("Cliente conectado: ${client.inetAddress.hostAddress}")

                thread {
                    try {
                        client.getInputStream().bufferedReader().use { reader ->
                            val receivedMessage = reader.readText()
                            println("Mensaje recibido: $receivedMessage")
                        }
                    } catch (e: Exception) {
                        println("Error al manejar la conexión: ${e.message}")
                    } finally {
                        client.close()
                    }
                }
            }
        }
    }

    fun previewPage() {
        val newPage = page - 1
        if(newPage >= 1) {
            page = newPage
            getProducts()
        }
    }

    fun nextPage() {
        val newPage = page + 1
        if(newPage <= totalPages) {
            page = newPage
            getProducts()
        }
    }

    fun applyFilters(filterModel: InventoryFilterModel){
        filter = filterModel
        getProducts()
    }

    fun getProducts(){
        viewModelScope.launch(Dispatchers.IO) {
            loading = true
            val result = productRepository.getProductsRange(page, pageSize, filter)
            println(result)
            if(result is Result.Success){
                withContext(Dispatchers.Main){
                    products = result.data.products
                    totalPages = ceil(result.data.total.toDouble() / pageSize).toInt()
                }
            }
        }
    }

    fun generateReportInventory(){
        viewModelScope.launch(Dispatchers.IO) {

        }
    }

}