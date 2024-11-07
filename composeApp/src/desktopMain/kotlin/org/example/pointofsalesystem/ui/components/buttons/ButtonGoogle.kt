package org.example.pointofsalesystem.ui.components.buttons

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import org.example.pointofsalesystem.ui.components.text.Body1Medium
import org.example.pointofsalesystem.ui.theme.Shapes
import org.example.pointofsalesystem.ui.theme.grey300
import org.example.pointofsalesystem.ui.theme.grey700
import org.example.pointofsalesystem.ui.theme.grey800
import org.jetbrains.compose.resources.painterResource
import pointofsalesystem.composeapp.generated.resources.Res
import pointofsalesystem.composeapp.generated.resources.google

@Composable
fun ButtonGoogle(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    content: @Composable() (RowScope.() -> Unit)? = null
){
    OutlinedButton(
        modifier = modifier,
        shape = RoundedCornerShape(Shapes.ROUNDED_CORNER_SHAPE),
        border = BorderStroke(
            width = 1.dp,
            color = grey300
        ),
        onClick = onClick,
    ){
        Image(
            painter = painterResource(Res.drawable.google),
            contentDescription = "google",
            modifier = Modifier.size(20.dp)
        )
        Spacer(modifier = Modifier.width(10.dp))
        Body1Medium(
            text = text,
            color = grey800,
        )
    }
}