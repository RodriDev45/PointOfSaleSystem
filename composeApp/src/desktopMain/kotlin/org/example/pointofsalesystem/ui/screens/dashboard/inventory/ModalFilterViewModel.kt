package org.example.pointofsalesystem.ui.screens.dashboard.inventory

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.example.pointofsalesystem.data.interfaces.CategoryRepository
import org.example.pointofsalesystem.domain.model.InventoryFilterModel
import org.example.pointofsalesystem.domain.utils.Result
import org.example.pointofsalesystem.ui.components.forms.OptionData
import java.net.ServerSocket
import java.net.Socket
import kotlin.concurrent.thread

class ModalFilterViewModel(
    private val categoryRepository: CategoryRepository
): ViewModel() {
    private var serverSocket: ServerSocket? = null
    var searchText by mutableStateOf("")
        private set
    var brand by mutableStateOf("")
        private set
    var quantity by mutableStateOf(0f)
        private set
    var salePrice by mutableStateOf(0f)
        private set
    var categorySelect by mutableStateOf<Long?>(null)
        private set

    var categoriesOption by mutableStateOf(listOf(OptionData(key = "null", "None")))
        private set

    var activeScan by mutableStateOf(false)
        private set

    var inStock by mutableStateOf(false)
        private set

    var lowStock by mutableStateOf(false)
        private set

    var outOfStock by mutableStateOf(false)
        private set

    init {
        viewModelScope.launch(Dispatchers.IO) {
            val result = categoryRepository.getAllCategories()
            if(result is Result.Success) {
                withContext(Dispatchers.Main) {
                    val options = result.data.map { OptionData(it.id.toString(), it.name) }
                    categoriesOption = options + OptionData(key = "null", value = "None")
                }
            }
        }
    }

    fun startServer() {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                serverSocket = ServerSocket(12345) // Puerto donde el servidor escucha
                println("Servidor iniciado en el puerto 12345")
                activeScan = true

                while (activeScan) {
                    val client: Socket = serverSocket!!.accept()
                    println("Cliente conectado: ${client.inetAddress.hostAddress}")

                    thread {
                        try {
                            client.getInputStream().bufferedReader().use { reader ->
                                val receivedMessage = reader.readText()
                                println("Mensaje recibido: $receivedMessage")
                                viewModelScope.launch(Dispatchers.Main) {
                                    searchText = receivedMessage
                                }
                            }
                        } catch (e: Exception) {
                            println("Error al manejar la conexión: ${e.message}")
                        } finally {
                            client.close()
                        }
                    }
                }
            }catch (e: Exception) {
                println("Error en el servidor: ${e.message}")
            } finally {
                if(serverSocket != null) {
                    stopServer()
                }
            }

        }
    }

    fun stopServer() {
        try {
            serverSocket?.close() // Cierra el ServerSocket si está activo
            serverSocket = null
            activeScan = false
            println("Servidor detenido")
        } catch (e: Exception) {
            println("Error al detener el servidor: ${e.message}")
        }
    }

    fun getFilters(): InventoryFilterModel{
        return InventoryFilterModel(
            searchText = searchText.ifBlank { null },
            brand = brand.ifBlank { null },
            quantity = if(quantity <= 0f) null else quantity.toInt(),
            salePrice = if(salePrice <= 0f) null else salePrice.toDouble(),
            inStock = inStock,
            lowStock = lowStock,
            outOfStock = outOfStock,
            category = categorySelect
        )
    }

    fun changeQuantity(newQuantity: Float) {
        quantity = newQuantity
    }

    fun changeSalePrice(newSalePrice: Float) {
        salePrice = newSalePrice
    }

    fun changeCategory(newCategory: Long?) {
        categorySelect = newCategory
    }

    fun changeSearchText(newSearchText: String){
        searchText = newSearchText
    }

    fun changeBrand(newBrand: String){
        brand = newBrand
    }

    fun changeInStock(value: Boolean) {
        inStock = value
    }

    fun changeLowStock(value: Boolean) {
        lowStock = value
    }

    fun changeOutOfStock(value: Boolean) {
        outOfStock = value
    }
}