package org.example.pointofsalesystem.ui.screens.dashboard.inventory.components

import androidx.compose.foundation.layout.*
import androidx.compose.material3.Slider
import androidx.compose.material3.SliderDefaults
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import org.example.pointofsalesystem.domain.model.InventoryFilterModel
import org.example.pointofsalesystem.domain.utils.ModalManager
import org.example.pointofsalesystem.ui.components.buttons.ButtonPrimary
import org.example.pointofsalesystem.ui.components.buttons.ButtonScan
import org.example.pointofsalesystem.ui.components.buttons.ButtonSecondary
import org.example.pointofsalesystem.ui.components.cards.Card
import org.example.pointofsalesystem.ui.components.forms.CheckboxField
import org.example.pointofsalesystem.ui.components.forms.Field
import org.example.pointofsalesystem.ui.components.forms.FieldSelect
import org.example.pointofsalesystem.ui.components.text.Body1Medium
import org.example.pointofsalesystem.ui.components.text.SubHeading
import org.example.pointofsalesystem.ui.components.widgets.Search
import org.example.pointofsalesystem.ui.screens.dashboard.inventory.ModalFilterViewModel
import org.example.pointofsalesystem.ui.theme.*
import org.koin.compose.viewmodel.koinViewModel



@Composable
fun ModalFilter(
    modifier: Modifier = Modifier,
    viewModel: ModalFilterViewModel = koinViewModel(),
    onApplyFilter: (InventoryFilterModel) -> Unit,
){
    val minPrice = 0f
    val maxPrice = 500f

    Card(
        modifier = modifier
            .fillMaxWidth(),
        paddingValues = PaddingValues(16.dp)
    ) {
        Column(
            modifier = Modifier.fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            SubHeading("Filter")

            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(8.dp),
            ) {
                Search(
                    placeholder = "Search product name or code",
                    value = viewModel.searchText,
                    onChange = {viewModel.changeSearchText(it)},
                    modifier = Modifier.weight(0.8f),
                    paddingValues = PaddingValues(horizontal = 12.dp, vertical = 10.dp),
                )
                ButtonScan(
                    onClick = {
                        if(viewModel.activeScan){
                            viewModel.stopServer()
                        }else{
                            viewModel.startServer()
                        }
                    },
                    isActive = viewModel.activeScan,
                    modifier = Modifier.weight(0.2f)
                )
            }
            Spacer(modifier = Modifier.height(16.dp))
            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                // Texto para mostrar el precio actual
                Body1Medium(
                    text = "Quantity: ${viewModel.quantity.toInt()}",
                    modifier = Modifier.weight(0.3f),
                    color = grey700
                )

                // Slider
                Slider(
                    value = viewModel.quantity,
                    onValueChange = { viewModel.changeQuantity(it) },
                    valueRange = minPrice..maxPrice,
                    steps = ((maxPrice - minPrice) / 10).toInt() - 1, // Opcional: definir pasos intermedios
                    modifier = Modifier.weight(0.7f),
                    colors = SliderDefaults.colors(
                        thumbColor = Primary600,
                        activeTrackColor = Primary200,
                        inactiveTrackColor = grey200,
                    )
                )
            }
            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                // Texto para mostrar el precio actual
                Body1Medium(
                    text = "Sale Price: ${viewModel.salePrice.toInt()}",
                    modifier = Modifier.weight(0.3f),
                    grey700
                )

                // Slider
                Slider(
                    value = viewModel.salePrice,
                    onValueChange = { viewModel.changeSalePrice(it) },
                    valueRange = minPrice..maxPrice,
                    modifier = Modifier.weight(0.7f),
                    colors = SliderDefaults.colors(
                        thumbColor = Primary600,
                        activeTrackColor = Primary200,
                        inactiveTrackColor = grey200,
                    )
                )
            }
            Row(
                verticalAlignment = Alignment.CenterVertically,
            ){
                Body1Medium(
                    text = "Product Category",
                    color = grey700,
                    modifier = Modifier.weight(0.3f),
                )
                FieldSelect(
                    placeholder = "Select the category",
                    value = if(viewModel.categorySelect == null) "" else viewModel.categorySelect.toString(),
                    onChange = {
                        //categoryOption = it
                        viewModel.changeCategory(if(it.key == "null") null else it.key.toLong())
                    },
                    options = viewModel.categoriesOption,
                    modifier = Modifier.weight(0.7f),
                )

            }
            Spacer(modifier = Modifier.height(12.dp))
            Row(
                verticalAlignment = Alignment.CenterVertically,
            ){
                Body1Medium(
                    text = "Availability",
                    color = grey700,
                    modifier = Modifier.weight(0.3f),
                )
                Row(
                    modifier = Modifier.weight(0.7f),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    CheckboxField(
                        label = "In stock",
                        value = viewModel.inStock,
                        onValueChange = { viewModel.changeInStock(it) },
                        colorText = success600,
                        checkedColor = success600,
                        uncheckedColor = success600,
                    )
                    CheckboxField(
                        label = "Low stock",
                        value = viewModel.lowStock,
                        onValueChange = { viewModel.changeLowStock(it) },
                        colorText = warning600,
                        checkedColor = warning600,
                        uncheckedColor = warning600,
                    )
                    CheckboxField(
                        label = "Out of stock",
                        value = viewModel.outOfStock,
                        onValueChange = { viewModel.changeOutOfStock(it) },
                        colorText = error600,
                        checkedColor = error600,
                        uncheckedColor = error600,
                    )
                }

            }
            Spacer(modifier = Modifier.height(12.dp))
            Row(
                verticalAlignment = Alignment.CenterVertically,
            ){
                Body1Medium(
                    text = "Brand",
                    color = grey700,
                    modifier = Modifier.weight(0.3f),
                )
                Field(
                    placeholder = "Brand",
                    value = viewModel.brand,
                    onChange = { viewModel.changeBrand(it) },
                    modifier = Modifier.weight(0.7f),
                )
            }
            Spacer(modifier = Modifier.height(16.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.End,
            ) {

                ButtonSecondary(
                    text = "Discard",
                    onClick = {
                        ModalManager.closeModal()
                    },
                )
                Spacer(modifier = Modifier.width(8.dp))
                ButtonPrimary(
                    text = "Apply filter",
                    onClick = {
                        onApplyFilter(viewModel.getFilters())
                        ModalManager.closeModal()
                    },
                )
            }
        }
    }
}