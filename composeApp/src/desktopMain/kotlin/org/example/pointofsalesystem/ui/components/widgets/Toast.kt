package org.example.pointofsalesystem.ui.components.widgets

import androidx.compose.animation.*
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.delay
import org.example.pointofsalesystem.domain.model.ToastType
import org.example.pointofsalesystem.ui.components.text.Body1Medium
import org.example.pointofsalesystem.ui.theme.Primary500
import org.example.pointofsalesystem.ui.theme.Shapes
import org.example.pointofsalesystem.ui.theme.error500
import org.example.pointofsalesystem.ui.theme.success500




@Composable
fun Toast(
    message: String,
    durationMillis: Int = 3000,
    type: ToastType = ToastType.Info,
    onDismiss: () -> Unit = {}
) {
    var visible by remember { mutableStateOf(false) } // Inicialmente oculto
    val backgroundColor = when (type) {
        ToastType.Info -> Primary500
        ToastType.Success -> success500
        ToastType.Error -> error500
    }

    // Maneja el estado de visibilidad y la duración del toast
    LaunchedEffect(Unit) {
        visible = true // Activa la visibilidad después de que el Composable se carga
        delay(durationMillis.toLong())
        visible = false // Oculta el toast después de la duración
    }

    AnimatedVisibility(
        visible = visible,
        enter = fadeIn() + slideInHorizontally(initialOffsetX = { it }),
        exit = fadeOut() + slideOutHorizontally(targetOffsetX = { it }),

    ) {
        DisposableEffect(Unit) {
            onDispose {
                if (!visible) {
                    onDismiss()
                }
            }
        }
        Box(
            modifier = Modifier
                .width(300.dp)
                .padding(16.dp)
                .clip(RoundedCornerShape(Shapes.ROUNDED_CORNER_SHAPE))
                .background(backgroundColor)
                .padding(12.dp),
            contentAlignment = Alignment.Center
        ) {
            Body1Medium(
                text = message,
                color = Color.White,
            )
        }
    }
}

