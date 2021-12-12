package com.jerubrin.tallyflash.domain.usecase

import com.jerubrin.tallyflash.data.VMixRepository
import com.jerubrin.tallyflash.domain.UiState
import com.jerubrin.tallyflash.entity.Scene
import com.jerubrin.tallyflash.entity.SceneState
import javax.inject.Inject
import javax.inject.Singleton


@Singleton
class WorkingScenesUseCase @Inject constructor(
    private val vMixRepository: VMixRepository
) : BaseUseCase<UiState, Scene>() {
    
    override suspend fun run(params: Scene): UiState{
        try {
            vMixRepository.getWorkingScenes().also { wScenes ->
                var sceneState: SceneState
                        = when(params.number) {
                    wScenes.active -> SceneState.ACTIVE
                    wScenes.preview -> SceneState.PREVIEW
                    else -> SceneState.OFF
                }
                if (sceneState != SceneState.ACTIVE) {
                    wScenes.overlays.forEach {
                        if (it.value == params.number) {
                            sceneState =
                                if (it.isPreview) SceneState.PREVIEW else SceneState.ACTIVE
                        }
                    }
                }
                return UiState.Ready(sceneState)
            }
        } catch (e: Exception) {
            return UiState.Error(e.localizedMessage ?: "")
        }
    }
    
    override suspend fun start(params: Scene?): UiState =
        UiState.Loading()
    
}