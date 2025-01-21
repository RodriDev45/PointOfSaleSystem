package org.example.pointofsalesystem.domain.utils

import androidx.compose.foundation.layout.BoxScope
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue

object ModalManager {

    private var _modalContent by mutableStateOf<(@Composable() ()-> Unit)?>(null)
    val modalContent get() = _modalContent

    fun showModal(modal: @Composable() ()-> Unit) {
        _modalContent = modal
    }

    fun closeModal() {
        _modalContent = null
    }

}