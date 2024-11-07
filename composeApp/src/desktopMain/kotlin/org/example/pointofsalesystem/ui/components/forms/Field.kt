package org.example.pointofsalesystem.ui.components.forms

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import org.example.pointofsalesystem.ui.components.text.Body1
import org.example.pointofsalesystem.ui.components.text.Body1Medium
import org.example.pointofsalesystem.ui.theme.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Field(
    label: String? = null,
    error: String? = null,
    placeholder: String = "",
    value: String,
    onChange: (String) -> Unit,
    modifier: Modifier = Modifier,
) {
    var isFocused by remember { mutableStateOf(false) }
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
                .padding(horizontal = 12.dp, vertical = 12.dp) // Ajusta el padding aquí para reducir la altura
        ) {
            BasicTextField(
                value = value,
                onValueChange = onChange,
                textStyle = TextStyle(
                    color = grey500,
                    fontWeight = FontWeight.Normal,
                    fontFamily = MaterialTheme.typography.headlineMedium.fontFamily,
                    fontSize = 16.sp
                ),
                cursorBrush = SolidColor(grey400),
                decorationBox = { innerTextField ->
                    if (value.isEmpty()) {
                        Body1Medium(
                            text = placeholder,
                            color = grey300
                        )
                    }
                    innerTextField()
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .onFocusChanged { focusState ->
                        isFocused = focusState.isFocused
                    }
            )
        }

        if (!error.isNullOrEmpty()) {
            Body1(
                text = error,
                color = error500
            )
        }
    }
}
