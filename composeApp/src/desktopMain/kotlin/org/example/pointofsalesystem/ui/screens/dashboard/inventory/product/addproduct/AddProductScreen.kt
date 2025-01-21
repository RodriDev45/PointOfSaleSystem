package org.example.pointofsalesystem.ui.screens.dashboard.inventory.product.addproduct

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.painter.BitmapPainter
import androidx.compose.ui.graphics.toComposeImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.unit.dp
import org.example.pointofsalesystem.domain.model.ToastType
import org.example.pointofsalesystem.domain.utils.Result
import org.example.pointofsalesystem.ui.components.buttons.ButtonPrimary
import org.example.pointofsalesystem.ui.components.buttons.ButtonScan
import org.example.pointofsalesystem.ui.components.buttons.ButtonSecondary
import org.example.pointofsalesystem.ui.components.cards.Card
import org.example.pointofsalesystem.ui.components.containers.ContainerDashboard
import org.example.pointofsalesystem.ui.components.forms.Field
import org.example.pointofsalesystem.ui.components.forms.FieldNumber
import org.example.pointofsalesystem.ui.components.forms.FieldSelect
import org.example.pointofsalesystem.ui.components.forms.OptionData
import org.example.pointofsalesystem.ui.components.text.*
import org.example.pointofsalesystem.ui.components.widgets.Toast
import org.example.pointofsalesystem.ui.components.widgets.dashedBorder
import org.example.pointofsalesystem.ui.theme.*
import org.koin.compose.viewmodel.koinViewModel
import java.awt.FileDialog
import java.awt.Frame
import java.io.File
import javax.imageio.ImageIO

@Composable
fun AddProductScreen(
    viewModel: AddProductViewModel = koinViewModel(),
    onNavToInventory: ()->Unit,
) {
    val image by viewModel.addProductForm.image.collectAsState()
    val code by viewModel.addProductForm.code.collectAsState()
    val name by viewModel.addProductForm.name.collectAsState()
    val category by viewModel.addProductForm.category.collectAsState()
    var categoryOption by remember { mutableStateOf<OptionData?>(null) }
    val brand by viewModel.addProductForm.brand.collectAsState()
    val quantity by viewModel.addProductForm.quantity.collectAsState()
    val minStock by viewModel.addProductForm.minimumStock.collectAsState()
    val maxStock by viewModel.addProductForm.maximumStock.collectAsState()
    val purchasePrice by viewModel.addProductForm.purchasePrice.collectAsState()
    val salePrice by viewModel.addProductForm.salePrice.collectAsState()

    ContainerDashboard {
        Card(
            modifier = Modifier.fillMaxSize()
        ) {
            Column {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    SubHeadingMedium(
                        text = "Add Product",
                        color = grey800
                    )

                    Row(
                        horizontalArrangement = Arrangement.spacedBy(12.dp),
                    ) {
                        ButtonPrimary(
                            text = "Save",
                            isLoading = viewModel.loadingSave,
                            onClick = {viewModel.handleSubmit()}
                        )
                        ButtonSecondary(
                            text = "Cancel",
                            onClick = {onNavToInventory()},
                        )
                    }
                }
                Row(
                    modifier = Modifier.fillMaxWidth(),
                ) {
                    LazyColumn(
                        modifier = Modifier.weight(0.5f).fillMaxHeight(),
                        verticalArrangement = Arrangement.SpaceBetween,
                    ) {
                        //Primary Details
                        item {

                            Column(
                                verticalArrangement = Arrangement.spacedBy(12.dp),
                            ) {
                               Body1SemiBold(
                                   text = "Primary Details",
                                   color = grey700
                               )
                                Row(
                                    verticalAlignment = Alignment.CenterVertically,
                                ){
                                   Body1Medium(
                                       text = "Product Code",
                                       color = grey400,
                                       modifier = Modifier.weight(0.4f),
                                   )
                                   Field(
                                       placeholder = "Enter the code",
                                       value = code.value,
                                       error = code.error,
                                       onChange = {viewModel.addProductForm.setCode(it)},
                                       modifier = Modifier.weight(0.43f),
                                   )
                                    Spacer(Modifier.weight(0.02f))
                                    ButtonScan(
                                        onClick = {
                                            if(!viewModel.activeScan){
                                                viewModel.startServer()
                                            }else{
                                                viewModel.stopServer()
                                            }
                                        },
                                        isActive = viewModel.activeScan,
                                        modifier = Modifier.weight(0.15f)
                                    )
                                }
                                Row(
                                    verticalAlignment = Alignment.CenterVertically,
                                ){
                                    Body1Medium(
                                        text = "Product Name",
                                        color = grey400,
                                        modifier = Modifier.weight(0.4f),
                                    )
                                    Field(
                                        placeholder = "Enter the name",
                                        value = name.value,
                                        error = name.error,
                                        onChange = {viewModel.addProductForm.setName(it)},
                                        modifier = Modifier.weight(0.6f),
                                    )
                                }
                                Row(
                                    verticalAlignment = Alignment.CenterVertically,
                                ){
                                    Body1Medium(
                                        text = "Product Category",
                                        color = grey400,
                                        modifier = Modifier.weight(0.4f),
                                    )
                                    FieldSelect(
                                        placeholder = "Select the category",
                                        value = if(category.value == null) "" else category.value.toString(),
                                        onChange = {
                                            //categoryOption = it
                                            viewModel.addProductForm.setCategory(it.key.toLong())
                                        },
                                        error = category.error,
                                        options = viewModel.categoriesOption,
                                        modifier = Modifier.weight(0.6f),
                                    )

                                }
                                Row(
                                    verticalAlignment = Alignment.CenterVertically,
                                ){
                                    Body1Medium(
                                        text = "Brand",
                                        color = grey400,
                                        modifier = Modifier.weight(0.4f),
                                    )
                                    Field(
                                        placeholder = "Enter the brand",
                                        value = brand.value,
                                        error = brand.error,
                                        onChange = {viewModel.addProductForm.setBrand(it)},
                                        modifier = Modifier.weight(0.6f),
                                    )
                                }
                            }
                        }
                        //Stock Information
                        item {

                            Column(
                                verticalArrangement = Arrangement.spacedBy(12.dp),
                            ) {
                                Body1SemiBold(
                                    text = "Stock Information",
                                    color = grey700
                                )
                                Row(
                                    verticalAlignment = Alignment.CenterVertically,
                                ){
                                    Body1Medium(
                                        text = "Quantity",
                                        color = grey400,
                                        modifier = Modifier.weight(0.4f),
                                    )
                                    FieldNumber(
                                        placeholder = "Enter the quantity",
                                        value = quantity.value.toDouble(),
                                        error = quantity.error,
                                        onChange = {
                                            viewModel.addProductForm.setQuantity(it.toInt())
                                        },
                                        modifier = Modifier.weight(0.6f),
                                    )
                                }
                                Row(
                                    verticalAlignment = Alignment.CenterVertically,
                                ){
                                    Body1Medium(
                                        text = "Minimum Stock",
                                        color = grey400,
                                        modifier = Modifier.weight(0.4f),
                                    )
                                    FieldNumber(
                                        placeholder = "Enter the minimum stock",
                                        value = minStock.value.toDouble(),
                                        error = minStock.error,
                                        onChange = {
                                            viewModel.addProductForm.setMinimumStock(it.toInt())
                                        },
                                        modifier = Modifier.weight(0.6f),
                                    )
                                }
                                Row(
                                    verticalAlignment = Alignment.CenterVertically,
                                ){
                                    Body1Medium(
                                        text = "Maximum Stock",
                                        color = grey400,
                                        modifier = Modifier.weight(0.4f),
                                    )
                                    FieldNumber(
                                        placeholder = "Enter the maximum stock",
                                        value = maxStock.value.toDouble(),
                                        error = maxStock.error,
                                        onChange = {viewModel.addProductForm.setMaximumStock(it.toInt())},
                                        modifier = Modifier.weight(0.6f),
                                    )
                                }
                            }
                        }
                        //Financial Information
                        item {

                            Column(
                                verticalArrangement = Arrangement.spacedBy(12.dp),
                            ) {
                                Body1SemiBold(
                                    text = "Financial Information",
                                    color = grey700
                                )
                                Row(
                                    verticalAlignment = Alignment.CenterVertically,
                                ){
                                    Body1Medium(
                                        text = "Purchase Price",
                                        color = grey400,
                                        modifier = Modifier.weight(0.4f),
                                    )
                                    FieldNumber(
                                        placeholder = "Enter the purchase price",
                                        value = purchasePrice.value,
                                        error = purchasePrice.error,
                                        onChange = {viewModel.addProductForm.setPurchasePrice(it)},
                                        modifier = Modifier.weight(0.6f),
                                    )
                                }
                                Row(
                                    verticalAlignment = Alignment.CenterVertically,
                                ){
                                    Body1Medium(
                                        text = "Sale Price",
                                        color = grey400,
                                        modifier = Modifier.weight(0.4f),
                                    )
                                    FieldNumber(
                                        placeholder = "Enter the sale price",
                                        value = salePrice.value,
                                        error = salePrice.error,
                                        onChange = {viewModel.addProductForm.setSalePrice(it)},
                                        modifier = Modifier.weight(0.6f),
                                    )
                                }
                            }
                        }
                    }
                    Spacer(Modifier.weight(0.2f).background(grey800))
                    //Image Product
                    Column(
                        modifier = Modifier
                            .weight(0.3f),
                        verticalArrangement = Arrangement.spacedBy(12.dp),
                    ) {

                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(400.dp)
                                .dashedBorder(
                                    color = grey300,
                                    shape = RectangleShape,
                                )
                                .clickable {
                                    val selectedFile = openFilePicker()
                                    if (selectedFile != null) {
                                        viewModel.setImage(selectedFile)
                                    }
                                }
                                .padding(8.dp),
                            contentAlignment = Alignment.Center
                        ){
                            if(viewModel.imageBitmap == null){
                                Body1Medium(
                                    text = "Add image"
                                )
                            }else{
                                Image(
                                    painter = BitmapPainter(viewModel.imageBitmap!!.toComposeImageBitmap()),
                                    contentDescription = null,
                                    modifier = Modifier.fillMaxSize(),
                                    contentScale = ContentScale.Crop,
                                )
                            }
                        }
                        if(image.error != null){

                            Body1(
                                text = image.error!!,
                                color = error500
                            )
                        }
                    }
                }
            }
        }
    }
}

fun openFilePicker(): File? {
    val fileDialog = FileDialog(Frame(), "Seleccionar imagen", FileDialog.LOAD)
    fileDialog.isVisible = true
    return if (fileDialog.file != null) {
        File(fileDialog.directory, fileDialog.file)
    } else {
        null
    }
}