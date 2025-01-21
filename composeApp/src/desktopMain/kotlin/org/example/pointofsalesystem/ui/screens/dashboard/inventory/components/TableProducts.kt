package org.example.pointofsalesystem.ui.screens.dashboard.inventory.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import org.example.pointofsalesystem.data.model.Product
import org.example.pointofsalesystem.domain.utils.ToastManager
import org.example.pointofsalesystem.ui.components.buttons.ButtonPrimary
import org.example.pointofsalesystem.ui.components.buttons.ButtonSecondary
import org.example.pointofsalesystem.ui.components.cards.Card
import org.example.pointofsalesystem.ui.components.text.Body1Medium
import org.example.pointofsalesystem.ui.components.text.Body2Medium
import org.example.pointofsalesystem.ui.components.text.SubHeadingMedium
import org.example.pointofsalesystem.ui.theme.*
import org.jetbrains.compose.resources.painterResource
import pointofsalesystem.composeapp.generated.resources.Res
import pointofsalesystem.composeapp.generated.resources.edit
import pointofsalesystem.composeapp.generated.resources.filter

@Composable
fun TableProducts(
    onNavToAddProduct: () -> Unit,
    products: List<Product>,
    page: Int,
    totalPage: Int,
    onPreview: () -> Unit,
    onNextPage: () -> Unit,
    onFilter: () -> Unit,
) {
    val headers = listOf("Products", "Buying Price", "Quantity", "Category", "Brand", "Availability", "Edit")
    Card(
        modifier = Modifier.fillMaxWidth(),
        paddingValues = PaddingValues(16.dp)
    ) {
        Column(
            modifier = Modifier.fillMaxWidth(),
        ) {

           Row(
               modifier = Modifier.fillMaxWidth(),
               horizontalArrangement = Arrangement.SpaceBetween,
           ) {
               SubHeadingMedium(
                   text = "Products"
               )

               Row(
                   horizontalArrangement = Arrangement.spacedBy(16.dp),
               ) {
                   ButtonPrimary(
                       text = "Add product",
                       onClick = {onNavToAddProduct()}
                   )
                   ButtonSecondary(
                       text = "Filter",
                       content = {
                           Image(
                               painter = painterResource(Res.drawable.filter),
                               contentDescription = null,
                               modifier = Modifier.size(18.dp),
                               colorFilter = ColorFilter.tint(grey600)
                           )
                       },
                       onClick = {
                           onFilter()
                       }
                   )
                   ButtonSecondary(
                       text = "Download all",
                       onClick = {
                           ToastManager.error("Opps! ocurrio un error")
                           println(ToastManager.toasts)
                       }
                   )
               }
           }
            Spacer(modifier = Modifier.height(16.dp))

            Row(
                modifier = Modifier.fillMaxWidth().padding(vertical = 12.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
            ) {
                headers.forEach {header ->
                    Body1Medium(
                        text = header,
                        color = grey500,
                        modifier = Modifier.weight(1f),
                        textAlign = if(header == "Products") TextAlign.Start else TextAlign.End
                    )
                }
            }
            LazyColumn {
                items(products) { product ->
                    Spacer(Modifier.height(1.dp).fillMaxWidth().background(grey100))
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 12.dp),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Body2Medium(
                            text = product.name,
                            modifier = Modifier.weight(1f),
                            color = grey700,
                            textAlign = TextAlign.Start
                        )
                        Body2Medium(
                            text = "S/ ${product.salePrice}",
                            modifier = Modifier.weight(1f),
                            color = grey700,
                            textAlign = TextAlign.End

                        )
                        Body2Medium(
                            text = product.quantity.toString(),
                            modifier = Modifier.weight(1f),
                            color = grey700,
                            textAlign = TextAlign.End

                        )
                        Body2Medium(
                            text = product.category.name,
                            modifier = Modifier.weight(1f),
                            color = grey700,
                            textAlign = TextAlign.End

                        )
                        Body2Medium(
                            text = product.brand,
                            modifier = Modifier.weight(1f),
                            color = grey700,
                            textAlign = TextAlign.End

                        )
                        Body2Medium(
                            text = when(product.quantity) {
                                in 1..10 -> "Low stock"
                                0 -> "Out of stock"
                                else -> "In stock"
                            },
                            modifier = Modifier.weight(1f),
                            color = when(product.quantity) {
                                in 1..10 -> warning500
                                0 -> error500
                                else -> success500
                            },
                            textAlign = TextAlign.End

                        )
                        Box(
                            modifier = Modifier.weight(1f),
                            contentAlignment = Alignment.TopEnd
                        ){
                            Image(
                                painter = painterResource(Res.drawable.edit),
                                contentDescription = null,
                                modifier = Modifier
                                    .clickable{

                                },
                                colorFilter = ColorFilter.tint(warning600)
                            )
                        }
                    }

                }
            }
            Spacer(Modifier.height(16.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically,
            ) {
                ButtonSecondary(
                    text = "Previous",
                    onClick = {onPreview()}
                )
                Body2Medium(
                    text = "Page $page of $totalPage",
                    color = grey700
                )
                ButtonSecondary(
                    text = "Next",
                    onClick = {onNextPage()}
                )
            }
        }

    }
}