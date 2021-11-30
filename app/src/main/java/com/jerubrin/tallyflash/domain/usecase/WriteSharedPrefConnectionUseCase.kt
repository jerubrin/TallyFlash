package com.jerubrin.tallyflash.domain.usecase

import android.content.Context
import android.content.SharedPreferences
import com.jerubrin.tallyflash.entity.ConnectionData
import com.jerubrin.tallyflash.entity.SharedConnectConst.SHARED_CONNECT
import com.jerubrin.tallyflash.entity.SharedConnectConst.SHARED_IP_ADDRESS
import com.jerubrin.tallyflash.entity.SharedConnectConst.SHARED_PORT

class WriteSharedPrefConnectionUseCase (
    private val context: Context
) : BaseUseCase<Boolean, ConnectionData>(){
    
    private val sharedPref: SharedPreferences
        get() = context.getSharedPreferences(SHARED_CONNECT, Context.MODE_PRIVATE)
    
    override fun execute(params: ConnectionData): Boolean {
        sharedPref.edit()?.apply{
            putString(SHARED_IP_ADDRESS, params.ip)
            putString(SHARED_PORT, params.port)
            return commit()
        }
        return false
    }
    
}