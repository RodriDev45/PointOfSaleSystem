package org.example.pointofsalesystem.ui.components.widgets

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import org.jetbrains.compose.resources.painterResource
import pointofsalesystem.composeapp.generated.resources.Res
import pointofsalesystem.composeapp.generated.resources.logo_point

@Composable
fun Logo(
    width: Dp = 100.dp,
    height: Dp = 100.dp,
    modifier: Modifier = Modifier,
) {
    Image(
        painter = painterResource(Res.drawable.logo_point),
        contentDescription = "Logo",
        modifier = modifier.width(width).height(height),
    )
}