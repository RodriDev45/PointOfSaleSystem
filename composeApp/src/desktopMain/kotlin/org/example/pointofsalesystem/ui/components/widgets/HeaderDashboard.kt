package org.example.pointofsalesystem.ui.components.widgets

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import io.kamel.core.Resource
import io.kamel.image.asyncPainterResource
import org.example.pointofsalesystem.data.model.Profile
import org.example.pointofsalesystem.ui.components.forms.Field
import org.example.pointofsalesystem.ui.theme.grey600
import org.jetbrains.compose.resources.painterResource
import pointofsalesystem.composeapp.generated.resources.Res
import pointofsalesystem.composeapp.generated.resources.notification
import pointofsalesystem.composeapp.generated.resources.user

@Composable
fun HeaderDashboard(
    imageProfile: String?,
    modifier: Modifier = Modifier,
){
    var search by remember { mutableStateOf("") }
    Row(
        modifier = modifier
            .background(MaterialTheme.colorScheme.background)
            .padding(20.dp)
            .fillMaxWidth()
            ,
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Search(
            value = search,
            onChange = {search = it},
            placeholder = "Search product, supplier, order",
            modifier = Modifier.width(300.dp)
        )

        Row(
            horizontalArrangement = Arrangement.spacedBy(16.dp),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Image(
                painter = painterResource(Res.drawable.notification),
                contentDescription = null,
                modifier = Modifier.size(24.dp),
                colorFilter = ColorFilter.tint(grey600)
            )
            Box(
                modifier = Modifier
                    .size(32.dp)
                    .clip(CircleShape),
                contentAlignment = Alignment.Center
            ){
                if(imageProfile != null){
                    when (val resource = asyncPainterResource(imageProfile)) {
                        is Resource.Loading -> {
                            Text("Loading...")
                        }
                        is Resource.Success -> {
                            val painter = resource.value
                            Image(
                                painter,
                                contentDescription = "Profile",
                                modifier = Modifier
                                    .fillMaxSize()
                                    .clip(CircleShape),
                                contentScale = ContentScale.Crop,
                            )
                        }
                        is Resource.Failure -> {
                            val fallbackPainter = painterResource(Res.drawable.user)
                            Image(
                                fallbackPainter,
                                contentDescription = "Profile",
                                modifier = Modifier
                                    .fillMaxSize()
                                    .clip(CircleShape),
                                contentScale = ContentScale.Crop,
                            )
                        }
                    }
                }else{
                    val fallbackPainter = painterResource(Res.drawable.user)
                    Image(
                        fallbackPainter,
                        contentDescription = "Profile",
                        modifier = Modifier
                            .fillMaxSize()
                            .clip(CircleShape),
                        contentScale = ContentScale.Crop,
                    )
                }
            }
        }
    }
}