package org.example.pointofsalesystem.ui.components.buttons

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.unit.dp
import org.example.pointofsalesystem.ui.components.text.Body1Medium
import org.example.pointofsalesystem.ui.theme.*
import org.jetbrains.compose.resources.painterResource
import pointofsalesystem.composeapp.generated.resources.Res
import pointofsalesystem.composeapp.generated.resources.scan
import pointofsalesystem.composeapp.generated.resources.scan_barcode

@Composable
fun ButtonScan(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    isActive: Boolean = false,
){
    Button(
        modifier = modifier,
        colors = ButtonDefaults.buttonColors(
            containerColor = Color.White,
            contentColor = Color.White,
        ),
        contentPadding = PaddingValues(vertical = 9.dp),
        border = BorderStroke(1.dp, if(isActive) success400 else grey100),
        shape = RoundedCornerShape(Shapes.ROUNDED_CORNER_SHAPE),
        onClick = onClick,
    ){
        Image(
            painter = painterResource(if(isActive) Res.drawable.scan_barcode else Res.drawable.scan),
            contentDescription = null,
            colorFilter = ColorFilter.tint(if(isActive) success600 else grey600)
        )
    }
}