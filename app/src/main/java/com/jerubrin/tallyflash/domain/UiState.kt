package com.jerubrin.tallyflash.domain


sealed class UiState {
    
    data class Error(
        val errorMessage: String
    ) : UiState()
    
    class Loading : UiState()
    
    data class Ready<T>(
        val data: T
    ) : UiState()
    
    class Idle : UiState()
}
