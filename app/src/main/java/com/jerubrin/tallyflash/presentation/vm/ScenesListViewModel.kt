package com.jerubrin.tallyflash.presentation.vm

import androidx.lifecycle.ViewModel
import com.jerubrin.tallyflash.domain.UiState
import com.jerubrin.tallyflash.domain.usecase.BaseUseCase
import com.jerubrin.tallyflash.domain.usecase.prefs.BasePrefsUseCase
import com.jerubrin.tallyflash.entity.ConnectionData
import com.jerubrin.tallyflash.entity.Scene
import com.jerubrin.tallyflash.presentation.service.SceneStateServiceControl
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject


@HiltViewModel
class ScenesListViewModel @Inject constructor(
    private val sceneListUseCase: BaseUseCase<UiState, ConnectionData>,
    private val readSharedPrefConnectionUseCase: BasePrefsUseCase<ConnectionData, Unit>,
    private val service: SceneStateServiceControl
) : ViewModel() {
    
    val connectionDataState get() =
        UiState.Ready(
            readSharedPrefConnectionUseCase.execute(Unit)
        )
    
    fun loadSceneList(): Flow<UiState> =
        sceneListUseCase.execute(
            connectionDataState.data
        )
    
    fun resetService() {
        //clear scene in service
        service.setCurrentScene(
            UiState.Ready( Scene() )
        )
    }
    
}