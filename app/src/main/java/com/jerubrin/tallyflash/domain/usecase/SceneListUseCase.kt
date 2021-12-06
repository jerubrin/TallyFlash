package com.jerubrin.tallyflash.domain.usecase

import com.jerubrin.tallyflash.data.VMixRepository
import com.jerubrin.tallyflash.domain.UiState
import com.jerubrin.tallyflash.entity.ConnectionData
import javax.inject.Inject
import javax.inject.Singleton


@Singleton
class SceneListUseCase @Inject constructor(
    private val vMixRepository: VMixRepository
) : BaseUseCase<UiState, ConnectionData>() {
    
    override suspend fun run(params: ConnectionData): UiState =
        try {
            UiState.Ready(vMixRepository.getScenesList(params))
        } catch (e: Exception) {
            UiState.Error(e.localizedMessage ?: "")
        }
    
    override suspend fun start(params: ConnectionData?): UiState =
        UiState.Loading()
    
}