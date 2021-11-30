package com.jerubrin.tallyflash.domain.usecase

abstract class BaseUseCase<Type, in Params> {
    
    abstract fun execute(params: Params): Type
}