package com.jerubrin.tallyflash.presentation.service

import com.jerubrin.tallyflash.entity.Scene
import com.jerubrin.tallyflash.entity.SceneState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class TestSceneStateServiceControl: SceneStateServiceControl {
    
    private var currentScene: Scene = DEFAULT_SCENE
    
    override fun setCurrentScene(scene: Scene){
        currentScene = scene
    }
    
    override fun getCurrentScene(): Scene =
        currentScene
    
    override fun getSceneState(): StateFlow<SceneState> =
        MutableStateFlow(DEFAULT_SCENE_STATE)
    
    companion object {
        val DEFAULT_SCENE = Scene()
        val DEFAULT_SCENE_STATE = SceneState.OFF
    }
}