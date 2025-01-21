package org.example.pointofsalesystem.domain.utils

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import org.example.pointofsalesystem.domain.model.ToastModel
import org.example.pointofsalesystem.domain.model.ToastType

object ToastManager {
    private val _toasts = mutableStateListOf<ToastModel>()
    val toasts
        get() = _toasts.toList()

    private var _countToasts by mutableStateOf(0)
    val countToasts
    get() = _countToasts

    fun success(message: String) {
        _toasts.add(
            ToastModel(
                type = ToastType.Success,
                message = message
            )
        )
        _countToasts = _toasts.size
    }

    fun error(message: String) {
        _toasts.add(
            ToastModel(
                type = ToastType.Error,
                message = message
            )
        )
        _countToasts = _toasts.size

    }

    fun info(message: String) {
        _toasts.add(
            ToastModel(
                type = ToastType.Info,
                message = message
            )
        )
        _countToasts = _toasts.size

    }

    fun removeToast() {
        _countToasts -= 1
    }

    fun clearToasts() {
        _toasts.clear()
    }
}