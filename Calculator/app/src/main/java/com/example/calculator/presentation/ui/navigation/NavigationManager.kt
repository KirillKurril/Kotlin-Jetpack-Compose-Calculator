package com.example.calculator.presentation.ui.navigation

import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow

object NavigationManager {
    private val _navigationCommands = MutableSharedFlow<String>(extraBufferCapacity = 1)
    val navigationCommands = _navigationCommands.asSharedFlow()

    fun navigate(destination: String) {
        _navigationCommands.tryEmit(destination)
    }
}
