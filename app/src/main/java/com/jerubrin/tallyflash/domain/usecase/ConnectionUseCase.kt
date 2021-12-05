package com.jerubrin.tallyflash.domain.usecase

import com.jerubrin.tallyflash.data.retrofit.VMixApi
import com.jerubrin.tallyflash.domain.State
import com.jerubrin.tallyflash.entity.ConnectionData
import javax.inject.Inject


class ConnectionUseCase @Inject constructor(
    private val vMixApi: VMixApi
) : BaseUseCase<Unit, State>() {
    
    fun execute(params: ConnectionData) {
        vMixApi.renewRetrofit(params)
    }
    
    override suspend fun start(params: State) {
        TODO("Not yet implemented")
    }
    
    override suspend fun run(params: State) {
        TODO("Not yet implemented")
    }
}