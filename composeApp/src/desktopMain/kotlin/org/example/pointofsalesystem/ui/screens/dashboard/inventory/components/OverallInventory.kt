package org.example.pointofsalesystem.ui.screens.dashboard.inventory.components

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import org.example.pointofsalesystem.ui.components.cards.Card
import org.example.pointofsalesystem.ui.components.text.Body1
import org.example.pointofsalesystem.ui.components.text.Body1SemiBold
import org.example.pointofsalesystem.ui.components.text.Body2
import org.example.pointofsalesystem.ui.components.text.SubHeadingMedium
import org.example.pointofsalesystem.ui.theme.*

data class InfoInventory(
    val info: String,
    val label: String,
)

@Composable
fun OverallInventory(
    totalCategories: Int,
    totalProducts: Int,
    totalTopCategories: Int,
    totalValue: Double,
    totalLowStock: Int,
    totalOutOfStock: Int,
){
    Card(
        modifier = Modifier.fillMaxWidth(),
    ) {
        Column {
            SubHeadingMedium("Overall Inventory")
            Spacer(Modifier.height(14.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
            ) {
                InfoOverallInventory(
                    text = "Categories",
                    color = Primary500,
                    infos = listOf(
                        InfoInventory(
                            info = totalCategories.toString(),
                            label = "Total"
                        ),
                        InfoInventory(
                            info = totalTopCategories.toString(),
                            label = "Popular"
                        ),
                    ),
                    modifier = Modifier.weight(0.25f)
                )
                InfoOverallInventory(
                    text = "Total Products",
                    color = warning500,
                    infos = listOf(
                        InfoInventory(
                            info = totalProducts.toString(),
                            label = "Last 7 days"
                        )
                    ),
                    isBorder = true,
                    modifier = Modifier.weight(0.25f)

                )
                InfoOverallInventory(
                    text = "Total Value",
                    color = success500,
                    infos = listOf(
                        InfoInventory(
                            info = "S/ $totalValue",
                            label = "Last 7 days"
                        )
                    ),
                    isBorder = true,
                    modifier = Modifier.weight(0.25f)
                )
                InfoOverallInventory(
                    text = "Low Stocks",
                    color = error500,
                    infos = listOf(
                        InfoInventory(
                            info = totalLowStock.toString(),
                            label = "Total"
                        ),
                        InfoInventory(
                            info = totalOutOfStock.toString(),
                            label = "Out of stock"
                        ),
                    ),
                    isBorder = true,
                    modifier = Modifier.weight(0.25f)

                )

            }
        }
    }
}

@Composable
fun InfoOverallInventory(
    text: String,
    color: Color,
    infos: List<InfoInventory>,
    isBorder: Boolean = false,
    modifier: Modifier = Modifier,
){
    Column(
        modifier = modifier
            .padding(start = if(isBorder) 18.dp else 0.dp)
            .drawBehind {
                if (isBorder) {
                    val borderWidth = 2.dp.toPx()
                    drawRect(
                        color = grey50,
                        topLeft = Offset(0f, 0f),
                        size = Size(borderWidth, size.height)
                    )
                }
            }
            .padding(start = if(isBorder) 18.dp else 0.dp),
        verticalArrangement = Arrangement.spacedBy(10.dp),
    ) {
        Body1(
            text = text,
            color = color,
        )
        LazyRow(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
        ) {
            items(infos){
                Column(
                    verticalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    Body1SemiBold(
                        text = it.info,
                        color = grey600,
                    )
                    Body2(
                        text = it.label,
                        color = grey400,
                    )
                }
            }
        }

    }
}