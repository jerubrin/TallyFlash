package com.jerubrin.tallyflash.domain.usecase

import com.jerubrin.tallyflash.data.retrofit.VMixApi
import com.jerubrin.tallyflash.entity.ConnectionData
import javax.inject.Inject

class ConnectionUseCase @Inject constructor(
    private val vMixApi: VMixApi
) {
    fun execute(params: ConnectionData) {
        vMixApi.renewRetrofit(params)
    }
}