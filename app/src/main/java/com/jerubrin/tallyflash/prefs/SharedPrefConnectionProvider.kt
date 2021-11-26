package com.jerubrin.tallyflash.prefs

import android.content.Context
import android.content.SharedPreferences

class SharedPrefConnectionProvider (
    private val context: Context
) {
    
    private val sharedPref: SharedPreferences
        get() = context.getSharedPreferences(SHARED_CONNECT, Context.MODE_PRIVATE)
    
    
    fun getUrl() =
        sharedPref.getString(SHARED_IP_ADDRESS, DEFAULT_IP_ADDRES) ?: DEFAULT_IP_ADDRES
    fun setUrl(text: String) =
        sharedPref.edit()?.apply{
            putString(SHARED_IP_ADDRESS, text)
            apply()
        }
    
    fun getPort() =
        sharedPref.getString(SHARED_PORT, DEFAULT_PORT) ?: DEFAULT_PORT
    fun setPort(text: String) =
        sharedPref.edit()?.apply{
            putString(SHARED_PORT, text)
            apply()
        }
    
    
    companion object {
        const val SHARED_CONNECT = "com.jerubrin.tallyflash.shared.connect"
        
        const val SHARED_IP_ADDRESS = "com.jerubrin.tallyflash.shared.ipAddress"
        const val SHARED_PORT = "com.jerubrin.tallyflash.shared.port"
        
        const val DEFAULT_IP_ADDRES = "192.168.0.101"
        const val DEFAULT_PORT = "8088"
    }
}