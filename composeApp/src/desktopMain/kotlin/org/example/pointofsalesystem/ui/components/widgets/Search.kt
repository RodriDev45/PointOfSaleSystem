package org.example.pointofsalesystem.ui.components.widgets

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import org.example.pointofsalesystem.ui.components.text.Body1Medium
import org.example.pointofsalesystem.ui.theme.*
import org.jetbrains.compose.resources.painterResource
import pointofsalesystem.composeapp.generated.resources.Res
import pointofsalesystem.composeapp.generated.resources.search

@Composable
fun Search(
    placeholder: String = "",
    value: String,
    onChange: (String) -> Unit,
    modifier: Modifier = Modifier,
){
    var isFocused by remember { mutableStateOf(false) }

    // Caja para el campo de texto con bordes personalizados
    Row(
        modifier = modifier
            .fillMaxWidth()
            .background(Color.White, shape = RoundedCornerShape(Shapes.ROUNDED_CORNER_SHAPE))
            .border(
                width = 1.dp,
                color = if(isFocused) {
                    Primary600
                } else grey200, // Color del borde
                shape = RoundedCornerShape(Shapes.ROUNDED_CORNER_SHAPE)
            )
            .padding(horizontal = 12.dp, vertical = 8.dp),
        horizontalArrangement = Arrangement.spacedBy(12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Image(
            painter = painterResource(Res.drawable.search),
            contentDescription = null,
            modifier = Modifier.size(20.dp),
            colorFilter = ColorFilter.tint(grey500)
        )
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

}