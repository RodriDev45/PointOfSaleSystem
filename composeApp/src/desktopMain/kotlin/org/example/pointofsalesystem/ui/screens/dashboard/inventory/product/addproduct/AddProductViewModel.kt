package org.example.pointofsalesystem.ui.screens.dashboard.inventory.product.addproduct

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import io.github.jan.supabase.SupabaseClient
import io.github.jan.supabase.storage.storage
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.example.pointofsalesystem.data.interfaces.CategoryRepository
import org.example.pointofsalesystem.data.interfaces.ProductRepository
import org.example.pointofsalesystem.data.model.Category
import org.example.pointofsalesystem.data.model.Product
import org.example.pointofsalesystem.domain.usecase.forms.AddProductForm
import org.example.pointofsalesystem.domain.utils.Result
import org.example.pointofsalesystem.domain.utils.ToastManager
import org.example.pointofsalesystem.domain.utils.generateUUID
import org.example.pointofsalesystem.ui.components.forms.OptionData
import java.io.File
import java.net.ServerSocket
import java.net.Socket
import java.util.Date
import javax.imageio.ImageIO
import kotlin.concurrent.thread

class AddProductViewModel(
    val addProductForm: AddProductForm,
    private val categoryRepository: CategoryRepository,
    private val productRepository: ProductRepository,
    private val supabaseClient: SupabaseClient
): ViewModel() {
    private var serverSocket: ServerSocket? = null
    var loadingSave by mutableStateOf(false)
        private set
    var categoriesOption by mutableStateOf<List<OptionData>>(emptyList())
        private set
    var imageBitmap by mutableStateOf<java.awt.image.BufferedImage?>(null)
        private set

    var activeScan by mutableStateOf(false)
        private set

    init {
        viewModelScope.launch(Dispatchers.IO) {
            val result = categoryRepository.getAllCategories()
            if(result is Result.Success) {
                withContext(Dispatchers.Main) {
                    categoriesOption = result.data.map { OptionData(it.id.toString(), it.name) }
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
                                    addProductForm.setCode(receivedMessage)
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

    fun setImage(file: File) {
        addProductForm.setImage(file)
        imageBitmap = ImageIO.read(file)
    }

    fun resetState(){
        addProductForm.resetFields()
        imageBitmap = null
    }

    fun handleSubmit(){
        addProductForm.validate{ data ->
            viewModelScope.launch {
                loadingSave = true
                var imageUrl: String? = null
                if(data.image!=null) {

                    val bucket = supabaseClient.storage.from("products")
                    val path = "${generateUUID()}.${data.image.extension}"
                    bucket.upload(path, data.image.readBytes()) {
                        upsert = false
                    }
                    imageUrl = supabaseClient.storage.from("products").publicUrl(path)
                }
                val result = productRepository.insertProduct(
                    Product(
                        code = data.code.toLong(),
                        name = data.name,
                        category = Category(
                            id = data.category,
                            name = "",
                            created_at = Date()
                        ),
                        brand = data.brand,
                        quantity = data.quantity,
                        minStock = data.minStock,
                        maxStock = data.maxStock,
                        purchasePrice = data.purchasePrice,
                        salePrice = data.salePrice,
                        image = imageUrl,
                        createdAt = Date()
                    )
                )
                when(result){
                    is Result.Success -> {
                        ToastManager.success(result.data)
                        resetState()
                    }
                    is Result.Error -> {
                        ToastManager.error(result.error)
                    }
                }
                loadingSave = false
                println(data)
            }
        }
    }
}