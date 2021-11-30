package com.jerubrin.tallyflash.domain.usecase

import com.jerubrin.tallyflash.domain.UiState
import com.jerubrin.tallyflash.data.VMixRepository
import com.jerubrin.tallyflash.entity.ConnectionData
import javax.inject.Inject
import javax.inject.Singleton


@Singleton
class SceneListAsyncUseCase @Inject constructor(
    private val vMixRepository: VMixRepository
) : BaseAsyncUseCase<UiState, ConnectionData>(UiState.Loading()) {
    
    override suspend fun run(params: ConnectionData): UiState =
        try {
            UiState.Ready(vMixRepository.getScenesList(params))
        } catch (e: Exception) {
            UiState.Error(e.localizedMessage ?: "")
        }
    
}