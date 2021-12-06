package com.jerubrin.tallyflash.presentation.vm

import androidx.lifecycle.ViewModel
import com.jerubrin.tallyflash.R
import com.jerubrin.tallyflash.data.ResourcesInterface
import com.jerubrin.tallyflash.domain.usecase.prefs.BasePrefsUseCase
import com.jerubrin.tallyflash.entity.ConnectionData
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject


@HiltViewModel
class ConnectionViewModel @Inject constructor(
    readSharedPrefConnectionUseCase: BasePrefsUseCase<ConnectionData, Unit>,
    private val writeSharedPrefConnectionUseCase: BasePrefsUseCase<Boolean, ConnectionData>,
    private val resources: ResourcesInterface
) : ViewModel() {
    
    private val connectionData = readSharedPrefConnectionUseCase.execute(Unit)
    val url = connectionData.ip
    val port = connectionData.port
    
    fun checkAndSaveConnectionData(url: String, port: String): CorrectInputState {
        checkIpAndPort(url, port).also {
            if (it is CorrectInputState.Correct) {
                val connectionData = ConnectionData(ip = url, port = port)
                writeSharedPrefConnectionUseCase.execute(connectionData)
            }
            return it
        }
    }
    
    private fun checkIpAndPort(url: String, port: String): CorrectInputState {
        if (url.count{ ".".contains(it) } != 3)
            return CorrectInputState.WrongIp(resources.getString(R.string.wrong_ip))
        if (url.replace(".", "").toLongOrNull() == null)
            return CorrectInputState.WrongIp(resources.getString(R.string.wrong_ip))
        if (port.toIntOrNull() == null)
            return CorrectInputState.WrongPort(resources.getString(R.string.wrong_port))
        return CorrectInputState.Correct("")
    }
    
    
    sealed class CorrectInputState(val errorMsg: String) {
        
        data class Correct(val message: String) : CorrectInputState(message)
        
        data class WrongIp(val message: String) : CorrectInputState(message)
        
        data class WrongPort(val message: String): CorrectInputState(message)
    }
}