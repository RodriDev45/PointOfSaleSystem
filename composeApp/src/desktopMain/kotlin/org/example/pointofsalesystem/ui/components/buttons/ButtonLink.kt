package org.example.pointofsalesystem.ui.components.buttons

import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.runtime.*
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.input.pointer.PointerEventPass
import androidx.compose.ui.input.pointer.PointerEventType
import androidx.compose.ui.input.pointer.PointerInputScope
import androidx.compose.ui.input.pointer.onPointerEvent
import androidx.compose.ui.unit.dp
import org.example.pointofsalesystem.ui.components.text.Body1Medium
import org.example.pointofsalesystem.ui.theme.Primary600

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun ButtonLink(
    text: String,
    modifier: Modifier = Modifier,
    onClick: () -> Unit,
){
    var isHover by remember { mutableStateOf(false) }
    Body1Medium(
        text = text,
        color = Primary600,
        modifier = Modifier
            .clickable {
                onClick()
            }
            .onPointerEvent(
                eventType = PointerEventType.Enter,
                onEvent = {
                    isHover = true
                }
            )
            .onPointerEvent(
                eventType = PointerEventType.Exit,
                onEvent = {
                    isHover = false
                }
            )
            .drawBehind {
                if (isHover) {
                    // Dibuja la línea solo si el borde está visible
                    val strokeWidth = 2.dp.toPx()
                    val y = size.height - strokeWidth / 2
                    drawLine(
                        color = Primary600,
                        start = Offset(0f, y),
                        end = Offset(size.width, y),
                        strokeWidth = strokeWidth
                    )
                }
            }
    )
}
