package com.jerubrin.tallyflash.domain


sealed class State {
    
    data class Error(
        val errorMessage: String
    ) : State()
    
    class Loading : State()
    
    data class Ready<out T>(
        val data: T
    ) : State()
    
    class Idle : State()
}
