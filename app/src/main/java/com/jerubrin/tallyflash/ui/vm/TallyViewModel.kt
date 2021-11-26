package com.jerubrin.tallyflash.ui.vm

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jerubrin.tallyflash.data.Scene
import com.jerubrin.tallyflash.data.SharedPrefMainProvider
import com.jerubrin.tallyflash.data.WorkingScenes
import com.jerubrin.tallyflash.domain.UiState
import com.jerubrin.tallyflash.domain.VMixInteractor
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.last
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TallyViewModel @Inject constructor(
    private val vMixInteractor: VMixInteractor,
    val sharedPrefMain: SharedPrefMainProvider
) : ViewModel() {
    
    var isUpdating: Boolean = false
    
    var currentScene: MutableStateFlow<Scene?> = MutableStateFlow(null)
    
    private var _sceneState: MutableStateFlow<SceneState> = MutableStateFlow(SceneState.OFF)
    val sceneState: StateFlow<SceneState> = _sceneState
    
    private var _number: MutableStateFlow<String> = MutableStateFlow("")
    val number: StateFlow<String> = _number
    
    private var _name: MutableStateFlow<String> = MutableStateFlow("")
    val name: StateFlow<String> = _name
    
    init {
        viewModelScope.launch {
            currentScene.collectLatest {
                _number.value = it?.number.toString()
                _name.value = it?.shortTitle ?: ""
            }
        }

        autoReadState()
    }
    
    private fun autoReadState() {
        viewModelScope.launch {
            while (true) {
                if (isUpdating) {
                    vMixInteractor.getWorkingScenes()
                    val uiState = vMixInteractor.uiState.value
                    if (uiState is UiState.Ready<*>) {
                        val workingScenes = uiState.data as WorkingScenes
                        val active = workingScenes.active
                        val preview = workingScenes.preview
                        when(currentScene.value?.number ?: -2) {
                            active -> _sceneState.value = SceneState.ACTIVE
                            preview -> _sceneState.value = SceneState.PREVIEW
                            else -> _sceneState.value = SceneState.OFF
                        }
                    }
                    if (uiState is UiState.Error) {
                        _sceneState.value = SceneState.ERROR
                    }
                }
                
                delay(200)
            }
        }
    }
    
    enum class SceneState(val state: String) {
        ACTIVE("Active"),
        PREVIEW("Preview"),
        OFF("Off"),
        ERROR("Error")
    }
}