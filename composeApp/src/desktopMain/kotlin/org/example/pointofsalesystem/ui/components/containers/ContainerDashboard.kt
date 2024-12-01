package org.example.pointofsalesystem.ui.components.containers

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun ContainerDashboard(
    modifier: Modifier = Modifier,
    contentAlignment: Alignment = Alignment.TopStart,
    content: @Composable() (BoxScope.() -> Unit)
) {
    Box(
        modifier = modifier.fillMaxSize().padding(20.dp),
        contentAlignment = contentAlignment,
        content = content
    )
}