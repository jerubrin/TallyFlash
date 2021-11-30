package com.jerubrin.tallyflash.domain.usecase

import android.content.Context
import android.content.SharedPreferences
import com.jerubrin.tallyflash.entity.ConnectionData
import com.jerubrin.tallyflash.entity.SharedConnectConst.DEFAULT_IP_ADDRESS
import com.jerubrin.tallyflash.entity.SharedConnectConst.DEFAULT_PORT
import com.jerubrin.tallyflash.entity.SharedConnectConst.SHARED_CONNECT
import com.jerubrin.tallyflash.entity.SharedConnectConst.SHARED_IP_ADDRESS
import com.jerubrin.tallyflash.entity.SharedConnectConst.SHARED_PORT


class ReadSharedPrefConnectionUseCase (
    private val context: Context
) : BaseUseCase<ConnectionData, Unit>() {
    
    private val sharedPref: SharedPreferences
        get() = context.getSharedPreferences(SHARED_CONNECT, Context.MODE_PRIVATE)
    
    
    override fun execute(params: Unit): ConnectionData {
        val url = sharedPref.getString(SHARED_IP_ADDRESS, DEFAULT_IP_ADDRESS) ?: DEFAULT_IP_ADDRESS
        val port = sharedPref.getString(SHARED_PORT, DEFAULT_PORT) ?: DEFAULT_PORT
        
        return ConnectionData(url, port)
    }
    
}