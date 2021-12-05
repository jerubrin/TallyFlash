package com.jerubrin.tallyflash.presentation.vm

import androidx.lifecycle.ViewModel
import com.jerubrin.tallyflash.domain.usecase.prefs.BasePrefsUseCase
import com.jerubrin.tallyflash.entity.Scene
import com.jerubrin.tallyflash.entity.SceneState
import com.jerubrin.tallyflash.entity.SettingsData
import com.jerubrin.tallyflash.presentation.service.SceneStateServiceControl
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

@HiltViewModel
class TallyViewModel @Inject constructor(
    private val readSharedPrefMainUseCase: BasePrefsUseCase<SettingsData, Unit>,
    private val service: SceneStateServiceControl
) : ViewModel() {
    
    private var currentScene: Scene = Scene()
    
    val number get() = currentScene.number
    val name get() = currentScene.shortTitle
    
    val sceneState: StateFlow<SceneState> get() = service.getSceneState()
    
    fun setNewScene(scene: Scene) {
        currentScene = scene
        service.setCurrentScene(scene)
    }
    
    val getSettingsData get() = readSharedPrefMainUseCase.execute(Unit)
}