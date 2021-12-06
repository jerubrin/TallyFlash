package com.jerubrin.tallyflash.domain.usecase

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow


abstract class BaseUseCase<Type, Params>() {
    
    open suspend fun start(params: Params?): Type? =
        null
    
    abstract suspend fun run(params: Params): Type
    
    fun execute(params: Params): Flow<Type> = flow {
        start(params).also {
            if(it != null) emit(it)
        }
        emit(run(params))
    }
    
}