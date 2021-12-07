package com.jerubrin.tallyflash.presentation.service

import com.jerubrin.tallyflash.domain.UiState
import com.jerubrin.tallyflash.entity.Scene
import com.jerubrin.tallyflash.entity.SceneState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class TestSceneStateServiceControl: SceneStateServiceControl {
    
    private var currentScene: Scene = DEFAULT_SCENE
    
    override fun setCurrentScene(sceneState: UiState.Ready<Scene>){
        currentScene = sceneState.data
    }
    
    override fun getCurrentScene(): UiState =
        UiState.Ready(currentScene)
    
    override fun getSceneState(): StateFlow<SceneState> =
        MutableStateFlow(DEFAULT_SCENE_STATE)
    
    companion object {
        val DEFAULT_SCENE = Scene()
        val DEFAULT_SCENE_STATE = SceneState.OFF
    }
}