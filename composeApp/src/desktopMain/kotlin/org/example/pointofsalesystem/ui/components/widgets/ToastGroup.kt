package org.example.pointofsalesystem.ui.components.widgets

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import org.example.pointofsalesystem.domain.utils.ToastManager
import org.example.pointofsalesystem.ui.components.text.Heading1

@Composable
fun ToastGroup(){
    LaunchedEffect(ToastManager.countToasts){
        if(ToastManager.countToasts <= 0){
            ToastManager.clearToasts()
        }
    }

    LazyColumn(
        reverseLayout = true,
    ){
        items(ToastManager.toasts) {
            Toast(
                message = it.message,
                type = it.type,
                onDismiss = {
                    ToastManager.removeToast()
                }
            )
        }
    }

}