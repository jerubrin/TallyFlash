package com.jerubrin.tallyflash.domain.usecase.prefs

abstract class BasePrefsUseCase<Type, Params> {
    
    abstract fun execute(params: Params): Type
    
}