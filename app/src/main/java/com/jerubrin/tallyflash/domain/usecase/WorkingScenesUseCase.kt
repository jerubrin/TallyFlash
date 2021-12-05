package com.jerubrin.tallyflash.domain.usecase

import com.jerubrin.tallyflash.data.VMixRepository
import com.jerubrin.tallyflash.domain.State
import com.jerubrin.tallyflash.entity.Scene
import com.jerubrin.tallyflash.entity.SceneState
import javax.inject.Inject
import javax.inject.Singleton


@Singleton
class WorkingScenesUseCase @Inject constructor(
    private val vMixRepository: VMixRepository
) : BaseUseCase<State, Scene>() {
    
    override suspend fun run(params: Scene): State =
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
            State.Ready(sceneState)
        } catch (e: Exception) {
            State.Error(e.localizedMessage ?: "")
        }
    
    override suspend fun start(params: Scene): State =
        State.Loading()
    
}