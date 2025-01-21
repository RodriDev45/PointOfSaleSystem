package org.example.pointofsalesystem.ui.components.widgets

import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.window.Dialog
import org.example.pointofsalesystem.domain.utils.ModalManager
import org.example.pointofsalesystem.domain.utils.ModalTypes
import org.example.pointofsalesystem.ui.theme.modalColor

@Composable
fun ModalGroup(
    modifier: Modifier = Modifier
) {
    if(ModalManager.modalContent != null) {
        Dialog(
            onDismissRequest = { },
        ){
            ModalManager.modalContent!!()
        }

    }
}