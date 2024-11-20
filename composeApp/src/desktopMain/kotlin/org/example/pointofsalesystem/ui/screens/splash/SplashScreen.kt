package org.example.pointofsalesystem.ui.screens.splash

import androidx.compose.foundation.layout.*
import androidx.compose.material.Text
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import org.example.pointofsalesystem.ui.components.widgets.Logo
import org.example.pointofsalesystem.ui.theme.LogoColor

@Composable
fun SplashScreen() {
    Column(
        modifier = Modifier
            .fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Logo(
            width = 200.dp,
            height = 200.dp,
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = "KANBAN",
            style = TextStyle(
                fontWeight = FontWeight.SemiBold,
                fontFamily = MaterialTheme.typography.headlineMedium.fontFamily,
                fontSize = 47.sp
            ),
            color = LogoColor
        )
    }
}