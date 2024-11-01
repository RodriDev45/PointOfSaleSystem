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
fun SubHeading(text: String, modifier: Modifier = Modifier) {
    Text(
        text = text,
        modifier = modifier,
        style = TextStyle(
            fontFamily = MaterialTheme.typography.headlineMedium.fontFamily,
            fontSize = 20.sp,
            fontWeight = FontWeight.Normal,
        )
    )
}

@Composable
fun SubHeadingMedium(text: String, modifier: Modifier = Modifier) {
    Text(
        text = text,
        modifier = modifier,
        style = TextStyle(
            fontFamily = MaterialTheme.typography.headlineMedium.fontFamily,
            fontSize = 20.sp,
            fontWeight = FontWeight.Medium,
        )
    )
}

@Composable
fun SubHeadingSemiBold(text: String, modifier: Modifier = Modifier) {
    Text(
        text = text,
        modifier = modifier,
        style = TextStyle(
            fontFamily = MaterialTheme.typography.headlineMedium.fontFamily,
            fontSize = 20.sp,
            fontWeight = FontWeight.SemiBold,
        )
    )
}

@Composable
fun SubHeadingBold(text: String, modifier: Modifier = Modifier) {
    Text(
        text = text,
        modifier = modifier,
        style = TextStyle(
            fontFamily = MaterialTheme.typography.headlineMedium.fontFamily,
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
        )
    )
}