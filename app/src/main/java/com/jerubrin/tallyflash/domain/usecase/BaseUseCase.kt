package com.jerubrin.tallyflash.domain.usecase

interface BaseUseCase<Type, in Params> {
    
    fun execute(params: Params): Type
    
}