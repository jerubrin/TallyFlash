package com.jerubrin.tallyflash.domain.usecase

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow


abstract class BaseAsyncUseCase<Type, in Params>(
    private val baseData: Type?
) {
    
    abstract suspend fun run(params: Params): Type
    
    fun execute(params: Params): Flow<Type> = flow {
        if(baseData != null) {
            emit(baseData)
        }
        emit(run(params))
    }
    
}