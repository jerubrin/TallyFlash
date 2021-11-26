package com.jerubrin.tallyflash.ui.vm

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jerubrin.tallyflash.domain.UiState
import com.jerubrin.tallyflash.domain.VMixInteractor
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ScenesListViewModel @Inject constructor(
    private val vMixInteractor: VMixInteractor
) : ViewModel() {
    
    private var _uiState: MutableStateFlow<UiState> = MutableStateFlow<UiState>(UiState.Loading())
    val uiState: StateFlow<UiState> get() = _uiState
    
    init {
        loadVMixList()
    }
    
    fun loadVMixList(){
        _uiState = MutableStateFlow(UiState.Loading())
        viewModelScope.launch {
            vMixInteractor.getVMix()
            vMixInteractor.uiState.collectLatest {
                if (
                    it is UiState.Ready<*> &&
                    it.data is List<*>
                ) {
                    _uiState.value = it
                }
            }
        }
    }
}