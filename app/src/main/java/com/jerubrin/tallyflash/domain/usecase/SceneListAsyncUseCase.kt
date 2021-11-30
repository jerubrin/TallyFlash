package com.jerubrin.tallyflash.domain.usecase


import com.jerubrin.tallyflash.domain.UiState
import com.jerubrin.tallyflash.data.VMixRepository
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SceneListAsyncUseCase @Inject constructor(
    private val vMixRepository: VMixRepository
) : BaseAsyncUseCase<UiState, Unit>(UiState.Loading()) {
    
    override suspend fun run(params: Unit): UiState =
        try {
            UiState.Ready(vMixRepository.getScenesList())
        } catch (e: Exception) {
            UiState.Error(e.localizedMessage ?: "")
        }
    
}