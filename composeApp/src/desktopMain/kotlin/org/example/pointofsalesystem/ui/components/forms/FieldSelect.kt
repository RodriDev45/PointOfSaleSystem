package org.example.pointofsalesystem.ui.components.forms

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.DropdownMenuItem
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import org.example.pointofsalesystem.ui.components.text.Body1
import org.example.pointofsalesystem.ui.components.text.Body1Medium
import org.example.pointofsalesystem.ui.theme.*
import org.jetbrains.compose.resources.painterResource
import pointofsalesystem.composeapp.generated.resources.Res
import pointofsalesystem.composeapp.generated.resources.chart
import pointofsalesystem.composeapp.generated.resources.chevron_down

data class OptionData(
    val key: String,
    val value: String,
)

@Composable
fun FieldSelect(
    label: String? = null,
    error: String? = null,
    placeholder: String = "",
    value: String,
    onChange: (OptionData) -> Unit,
    options: List<OptionData>, // Lista de opciones para el dropdown
    modifier: Modifier = Modifier,
) {
    var isDropdownOpen by remember { mutableStateOf(false) } // Estado para el menú desplegable
    var optionSelected by remember { mutableStateOf(options.find { it.key == value }?.value ?: placeholder) }
    LaunchedEffect(value) {
        if(value == ""){
            optionSelected = placeholder
        }
    }

    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(4.dp),
    ) {
        if (label != null) {
            Body1Medium(
                text = label,
                color = grey700
            )
        }

        // Caja principal
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color.White, shape = RoundedCornerShape(Shapes.ROUNDED_CORNER_SHAPE))
                .border(
                    width = 1.dp,
                    color = if (!error.isNullOrEmpty()) {
                        error500
                    } else if (isDropdownOpen) {
                        Primary600
                    } else grey200,
                    shape = RoundedCornerShape(Shapes.ROUNDED_CORNER_SHAPE)
                )
                .clickable { isDropdownOpen = !isDropdownOpen } // Abre/cierra el dropdown al hacer clic
                .padding(horizontal = 12.dp, vertical = 12.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = optionSelected,
                    style = TextStyle(
                        color = if (value == "") grey300 else grey500,
                        fontWeight = FontWeight.Normal,
                        fontFamily = MaterialTheme.typography.headlineMedium.fontFamily,
                        fontSize = 16.sp
                    )
                )

                Image(
                    painter = painterResource(Res.drawable.chevron_down),
                    contentDescription = null,
                    modifier = Modifier.size(16.dp),
                )
            }
        }

        // Desplegable de opciones
        DropdownMenu(
            expanded = isDropdownOpen,
            onDismissRequest = { isDropdownOpen = false },
            modifier = Modifier.background(Color.White),

        ) {
            options.forEach { option ->
                DropdownMenuItem(
                    onClick = {
                        optionSelected = option.value
                        onChange(option) // Actualiza el valor seleccionado
                        isDropdownOpen = false // Cierra el menú
                    }
                ) {
                    Body1Medium(
                        text = option.value,
                        color = grey500
                    )
                }
            }
        }

        if (!error.isNullOrEmpty()) {
            Body1(
                text = error,
                color = error500
            )
        }
    }
}