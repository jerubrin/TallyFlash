package com.jerubrin.tallyflash.presentation.vm

import androidx.lifecycle.ViewModel
import com.jerubrin.tallyflash.entity.Scene
import com.jerubrin.tallyflash.domain.usecase.prefs.ReadSharedPrefMainUseCase
import com.jerubrin.tallyflash.entity.SceneState
import com.jerubrin.tallyflash.presentation.service.SceneStateServiceConnection
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject


@HiltViewModel
class TallyViewModel @Inject constructor(
    val readSharedPrefMainUseCase: ReadSharedPrefMainUseCase,
    private val serviceConnection: SceneStateServiceConnection
) : ViewModel() {
    
    private var currentScene: Scene = Scene()
    
    val number get() = currentScene.number
    val name get() = currentScene.shortTitle
    
    val sceneState: StateFlow<SceneState>? = serviceConnection.service?.sceneState
    
    fun setNewScene(scene: Scene) {
        currentScene = scene
        serviceConnection.service?.currentScene = scene
    }
}