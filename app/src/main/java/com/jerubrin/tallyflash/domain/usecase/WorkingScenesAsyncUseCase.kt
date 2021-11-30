package com.jerubrin.tallyflash.domain.usecase

import com.jerubrin.tallyflash.domain.UiState
import com.jerubrin.tallyflash.data.VMixRepository
import com.jerubrin.tallyflash.entity.Scene
import com.jerubrin.tallyflash.entity.SceneState
import javax.inject.Inject
import javax.inject.Singleton


@Singleton
class WorkingScenesAsyncUseCase @Inject constructor(
    private val vMixRepository: VMixRepository
) : BaseAsyncUseCase<UiState, Scene>(UiState.Loading()) {
    
    override suspend fun run(params: Scene): UiState =
        try {
            val workingScenes = vMixRepository.getWorkingScenes()
            val active = workingScenes.active
            val preview = workingScenes.preview
            val sceneState: SceneState
                = when(params.number) {
                    active -> SceneState.ACTIVE
                    preview -> SceneState.PREVIEW
                    else -> SceneState.OFF
                }
            UiState.Ready(sceneState)
        } catch (e: Exception) {
            UiState.Error(e.localizedMessage ?: "")
        }
    
}