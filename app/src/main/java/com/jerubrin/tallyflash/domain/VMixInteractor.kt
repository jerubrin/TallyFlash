package com.jerubrin.tallyflash.domain


import com.jerubrin.tallyflash.retrofit.VMixRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class VMixInteractor @Inject constructor(
    private val vMixRepository: VMixRepository
) {
    private val _uiState = MutableStateFlow<UiState>(UiState.Idle())
    val uiState: StateFlow<UiState> = _uiState
    
    suspend fun getVMix() {
        _uiState.value = getData(V_MIX_LIST)
    }
    
    suspend fun getWorkingScenes() {
        _uiState.value = getData(WORKING_SCENES)
    }
    

    private suspend fun getData(type: Int): UiState =
        try {
            when(type){
                V_MIX_LIST -> UiState.Ready(
                    vMixRepository.getScenesList()
                )
                WORKING_SCENES -> UiState.Ready(
                    vMixRepository.getWorkingScenes()
                )
                else -> throw Exception("Wrong data type")
            }
        } catch (e: Exception) {
            UiState.Error("Connection Error!")
        }
            
    
    
    companion object {
        private const val V_MIX_LIST = 0
        private const val WORKING_SCENES = 1
    }
}