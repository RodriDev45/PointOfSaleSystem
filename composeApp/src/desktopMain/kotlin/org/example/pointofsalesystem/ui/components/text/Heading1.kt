package org.example.pointofsalesystem.ui.components.text

import androidx.compose.material.Text
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import org.jetbrains.compose.resources.Font
import pointofsalesystem.composeapp.generated.resources.Inter
import pointofsalesystem.composeapp.generated.resources.Res


@Composable
fun Heading1(text: String, modifier: Modifier = Modifier) {
    Text(
        text = text,
        modifier = modifier,
        style = TextStyle(
            fontFamily = MaterialTheme.typography.headlineMedium.fontFamily,
            fontSize = 30.sp,
            fontWeight = FontWeight.Normal,
        )
    )
}

@Composable
fun Heading1Medium(text: String, modifier: Modifier = Modifier) {
    Text(
        text = text,
        modifier = modifier,
        style = TextStyle(
            fontFamily = MaterialTheme.typography.headlineMedium.fontFamily,
            fontSize = 30.sp,
            fontWeight = FontWeight.Medium,
        )
    )
}

@Composable
fun Heading1SemiBold(text: String, modifier: Modifier = Modifier) {
    Text(
        text = text,
        modifier = modifier,
        style = TextStyle(
            fontFamily = MaterialTheme.typography.headlineMedium.fontFamily,
            fontSize = 30.sp,
            fontWeight = FontWeight.SemiBold,
        )
    )
}

@Composable
fun Heading1Bold(text: String, modifier: Modifier = Modifier) {
    Text(
        text = text,
        modifier = modifier,
        style = TextStyle(
            fontFamily = MaterialTheme.typography.headlineMedium.fontFamily,
            fontSize = 30.sp,
            fontWeight = FontWeight.Bold,
        )
    )
}