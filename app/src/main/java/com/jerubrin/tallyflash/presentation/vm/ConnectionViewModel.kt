package com.jerubrin.tallyflash.presentation.vm

import androidx.lifecycle.ViewModel
import com.jerubrin.tallyflash.R
import com.jerubrin.tallyflash.data.ResourcesInterface
import com.jerubrin.tallyflash.domain.UiState
import com.jerubrin.tallyflash.domain.usecase.prefs.BasePrefsUseCase
import com.jerubrin.tallyflash.entity.ConnectionData
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject


@HiltViewModel
class ConnectionViewModel @Inject constructor(
    private val readSharedPrefConnectionUseCase: BasePrefsUseCase<ConnectionData, Unit>,
    private val writeSharedPrefConnectionUseCase: BasePrefsUseCase<Boolean, ConnectionData>,
    private val resources: ResourcesInterface
) : ViewModel() {
    
    val connectionDataState get() =
        UiState.Ready(readSharedPrefConnectionUseCase.execute(Unit))
    
    fun checkAndSaveConnectionData(uiState: UiState.Ready<ConnectionData>): UiState {
        checkIpAndPort(uiState.data).also {
            if (it is UiState.Ready<*>) {
                writeSharedPrefConnectionUseCase.execute(it.data as ConnectionData)
            }
            return it
        }
    }
    
    private fun checkIpAndPort(connectionData: ConnectionData): UiState {
        if (connectionData.ip.count{ ".".contains(it) } != 3)
            return UiState.Error(resources.getString(R.string.wrong_ip))
        if (connectionData.ip.replace(".", "").toLongOrNull() == null)
            return UiState.Error(resources.getString(R.string.wrong_ip))
        if (connectionData.port.toIntOrNull() == null)
            return UiState.Error(resources.getString(R.string.wrong_port))
        return UiState.Ready(connectionData)
    }
    
    
    sealed class CorrectInputState(val errorMsg: String) {
        
        data class Correct(val message: String) : CorrectInputState(message)
        
        data class WrongIp(val message: String) : CorrectInputState(message)
        
        data class WrongPort(val message: String): CorrectInputState(message)
    }
}