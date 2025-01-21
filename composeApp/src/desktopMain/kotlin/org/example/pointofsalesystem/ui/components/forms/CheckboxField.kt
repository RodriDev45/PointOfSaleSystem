package org.example.pointofsalesystem.ui.components.forms

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import org.example.pointofsalesystem.ui.components.text.Body1Medium
import org.example.pointofsalesystem.ui.components.text.Body2Medium
import org.example.pointofsalesystem.ui.theme.Primary600
import org.example.pointofsalesystem.ui.theme.grey300
import org.example.pointofsalesystem.ui.theme.grey500
import org.example.pointofsalesystem.ui.theme.grey700

@Composable
fun CheckboxField(
    value: Boolean,
    onValueChange: (Boolean) -> Unit,
    label: String? = null,
    modifier: Modifier = Modifier,
    colorText: Color = grey700,
    checkedColor: Color = Primary600,
    uncheckedColor: Color = grey300,

    ){
  Row(
      modifier = modifier,
      horizontalArrangement = Arrangement.spacedBy(4.dp),
      verticalAlignment = Alignment.CenterVertically,
  ) {
      Box(
          modifier = modifier
              .size(20.dp) // Tamaño del checkbox
              .background(
                  color = if (value) checkedColor else Color.White,
                  shape = RoundedCornerShape(4.dp)
              )
              .border(
                  width = 1.dp,
                  color = if (value) checkedColor else uncheckedColor,
                  shape = RoundedCornerShape(4.dp)
              )
              .clickable { onValueChange(!value) }, // Cambia el estado al hacer clic
          contentAlignment = Alignment.Center
      ) {
          // Muestra un símbolo si está marcado
          if (value) {
              Body1Medium(
                  text = "✓",
                  color = Color.White
              )
          }
      }
      if(!label.isNullOrEmpty()){
          Body1Medium(
              text = label,
              color = colorText
          )
      }
  }
}