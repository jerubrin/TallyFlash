package com.jerubrin.tallyflash.presentation.service

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch


class FlashTimer (
    private val delayTime: Long
) {
    
    private var _isFlashOn = MutableStateFlow(false)
    val isFlashOn: StateFlow<Boolean> = _isFlashOn
    
    init {
        CoroutineScope(Dispatchers.IO).launch {
            while (true) {
                _isFlashOn.value = !_isFlashOn.value
                delay(delayTime)
            }
        }
    }
}