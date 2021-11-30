package com.jerubrin.tallyflash.service

import android.app.Service
import android.content.Intent
import android.os.Binder
import android.os.Build
import android.os.IBinder
import com.jerubrin.tallyflash.domain.UiState
import com.jerubrin.tallyflash.domain.usecase.WorkingScenesAsyncUseCase
import com.jerubrin.tallyflash.entity.Scene
import com.jerubrin.tallyflash.entity.SceneState
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import java.util.*
import javax.inject.Inject

@AndroidEntryPoint
class SceneStateService : Service() {
    
    @Inject
    lateinit var workingScenesUseCase: WorkingScenesAsyncUseCase
    
    @Inject
    lateinit var flashController: FlashController
    
    private var _sceneState: MutableStateFlow<SceneState> = MutableStateFlow(SceneState.OFF)
    val sceneState: StateFlow<SceneState> = _sceneState
    
    var currentScene: Scene = Scene()
    
    private var countErrorStates = 0
    
    private val binder = LocalBinder()
    var flashControllerIsRunning: Boolean = false
    
    override fun onCreate() {
        super.onCreate()
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            flashControllerIsRunning = flashController.execute()
        }
        startAutoUpdate()
    }
    
    
    private fun startAutoUpdate() {
        CoroutineScope(Dispatchers.IO).launch {
            while (true) {
                if (currentScene.number >= 0) {
                    updateUiState()
                } else {
                    _sceneState.value = SceneState.OFF
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                        flashController.changeFlashLightState(SceneState.OFF)
                    }
                }
                delay(200L)
            }
        }
    }
    
    private suspend fun updateUiState() {
        workingScenesUseCase.execute(currentScene).collectLatest {
            if (it is UiState.Ready<*>) {
                _sceneState.value = it.data as SceneState
            }
            
            if (it is UiState.Error) {
                if(countErrorStates > MAX_ERROR_COUNT) {
                    _sceneState.value = SceneState.ERROR
                } else {
                    countErrorStates++
                }
            } else {
                countErrorStates = 0
            }
    
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                flashController.changeFlashLightState(_sceneState.value)
            }
        }
    }
    
    
    
    inner class LocalBinder : Binder() {
        fun getService(): SceneStateService = this@SceneStateService
    }
    
    override fun onBind(p0: Intent?): IBinder {
        return binder
    }
    
    override fun onDestroy() {
        super.onDestroy()
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            flashController.changeFlashLightState(SceneState.OFF)
        }
    }
    
    companion object {
        const val MAX_ERROR_COUNT = 5
    }
    
}