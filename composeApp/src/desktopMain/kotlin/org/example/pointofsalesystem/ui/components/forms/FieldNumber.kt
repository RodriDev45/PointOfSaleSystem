package org.example.pointofsalesystem.ui.components.forms

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import org.example.pointofsalesystem.ui.components.text.Body1
import org.example.pointofsalesystem.ui.components.text.Body1Medium
import org.example.pointofsalesystem.ui.theme.*
import org.jetbrains.compose.resources.painterResource
import pointofsalesystem.composeapp.generated.resources.Res
import pointofsalesystem.composeapp.generated.resources.chevron_down
import pointofsalesystem.composeapp.generated.resources.chevron_up

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FieldNumber(
    label: String? = null,
    error: String? = null,
    placeholder: String = "",
    value: Double,
    onChange: (Double) -> Unit,
    modifier: Modifier = Modifier,
    disable: Boolean = false,
    step: Double = 1.0,
) {
    var isFocused by remember { mutableStateOf(false) }
    var number by remember { mutableStateOf(if(value == 0.0) "" else value.toString()) }
    val numericRegex = Regex("^-?$|^-?\\d+(\\.\\d*)?$")
    LaunchedEffect(value){
        if(value == 0.0) number = ""
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

        // Caja para el campo de texto con bordes personalizados
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color.White, shape = RoundedCornerShape(Shapes.ROUNDED_CORNER_SHAPE))
                .border(
                    width = 1.dp,
                    color = if(!error.isNullOrEmpty()){
                        error500
                    } else if(isFocused) {
                        Primary600
                    } else grey200, // Color del borde
                    shape = RoundedCornerShape(Shapes.ROUNDED_CORNER_SHAPE)
                )
                .padding(horizontal = 12.dp, vertical = 6.dp) // Ajusta el padding aquí para reducir la altura
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {

                BasicTextField(
                    value = number,
                    maxLines = 1,
                    enabled = !disable,
                    onValueChange = { newValue ->
                        try {
                            if(numericRegex.matches(newValue)) {
                                if(newValue != "-" && newValue != "") {
                                    println(newValue.toDouble())
                                    onChange(newValue.toDouble())
                                }
                                number = newValue
                            }

                            if(newValue == "") {
                                number = ""
                                onChange(0.0)
                            }
                        }catch (e: Exception){
                            e.printStackTrace()
                        }

                    },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    textStyle = TextStyle(
                        color = grey500,
                        fontWeight = FontWeight.Normal,
                        fontFamily = MaterialTheme.typography.headlineMedium.fontFamily,
                        fontSize = 16.sp
                    ),
                    cursorBrush = SolidColor(grey400),
                    decorationBox = { innerTextField ->
                        if(number.isEmpty()){
                            Body1Medium(
                                text = "0.0",
                                color = grey300
                            )
                        }
                        innerTextField()
                    },
                    modifier = Modifier
                        .onFocusChanged { focusState ->
                            isFocused = focusState.isFocused
                        }
                )
                Column {
                    Image(
                        painter = painterResource(Res.drawable.chevron_up),
                        contentDescription = null,
                        colorFilter = ColorFilter.tint(grey500),
                        modifier = Modifier.size(16.dp)
                            .clickable{
                                val newValue = value+step
                                val rounded = String.format("%.6f", newValue).toDouble()
                                number = rounded.toString()
                                onChange(rounded)
                            }
                    )
                    Image(
                        painter = painterResource(Res.drawable.chevron_down),
                        contentDescription = null,
                        colorFilter = ColorFilter.tint(grey500),
                        modifier = Modifier.size(16.dp)
                            .clickable{
                                val newValue = value-step
                                val rounded = String.format("%.6f", newValue).toDouble()
                                number = rounded.toString()
                                onChange(rounded)
                            }
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
