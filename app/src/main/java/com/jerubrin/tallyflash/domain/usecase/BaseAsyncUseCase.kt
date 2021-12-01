package com.jerubrin.tallyflash.domain.usecase

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow


abstract class BaseAsyncUseCase<Type, in Params>() {
    
    abstract suspend fun start(params: Params): Type
    
    abstract suspend fun run(params: Params): Type
    
    fun execute(params: Params): Flow<Type> = flow {
        emit(start(params))
        emit(run(params))
    }
    
}