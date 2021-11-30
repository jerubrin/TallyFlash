package com.jerubrin.tallyflash.ui.vm

import androidx.lifecycle.ViewModel
import com.jerubrin.tallyflash.domain.UiState
import com.jerubrin.tallyflash.domain.usecase.ReadSharedPrefConnectionUseCase
import com.jerubrin.tallyflash.domain.usecase.SceneListAsyncUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

@HiltViewModel
class ScenesListViewModel @Inject constructor(
    private val sceneListUseCase: SceneListAsyncUseCase,
    val readSharedPrefConnectionUseCase: ReadSharedPrefConnectionUseCase
) : ViewModel() {
    
    fun loadSceneList(): Flow<UiState> =
        sceneListUseCase.execute(readSharedPrefConnectionUseCase.execute())
}