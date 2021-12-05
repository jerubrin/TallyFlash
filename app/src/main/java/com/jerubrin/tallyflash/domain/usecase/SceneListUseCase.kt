package com.jerubrin.tallyflash.domain.usecase

import com.jerubrin.tallyflash.data.VMixRepository
import com.jerubrin.tallyflash.domain.State
import com.jerubrin.tallyflash.entity.ConnectionData
import javax.inject.Inject
import javax.inject.Singleton


@Singleton
class SceneListUseCase @Inject constructor(
    private val vMixRepository: VMixRepository
) : BaseUseCase<State, ConnectionData>() {
    
    override suspend fun run(params: ConnectionData): State =
        try {
            State.Ready(vMixRepository.getScenesList(params))
        } catch (e: Exception) {
            State.Error(e.localizedMessage ?: "")
        }
    
    override suspend fun start(params: ConnectionData): State =
        State.Loading()
    
}