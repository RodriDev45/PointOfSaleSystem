package org.example.pointofsalesystem.ui.components.buttons

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import org.example.pointofsalesystem.ui.components.text.Body1Medium
import org.example.pointofsalesystem.ui.theme.Shapes
import org.example.pointofsalesystem.ui.theme.grey100
import org.example.pointofsalesystem.ui.theme.grey50
import org.example.pointofsalesystem.ui.theme.grey600

@Composable
fun ButtonSecondary(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    isLoading: Boolean = false,
    content: @Composable() (RowScope.() -> Unit)? = null
){
    Button(
        modifier = modifier,
        colors = ButtonDefaults.buttonColors(
            containerColor = Color.White,
            contentColor = Color.White,
        ),
        border = BorderStroke(1.dp, grey100),
        shape = RoundedCornerShape(Shapes.ROUNDED_CORNER_SHAPE),
        onClick = onClick,
    ){
        if(isLoading){
            CircularProgressIndicator(
                modifier = Modifier.size(24.dp),
                color = Color.White,
                strokeWidth = 2.dp
            )
            Spacer(modifier = Modifier.width(8.dp))
        }
        if(content !== null){
            content()
            Spacer(modifier = Modifier.width(8.dp))
        }
        Body1Medium(
            text = text,
            color = grey600,
        )
    }
}