package com.jerubrin.tallyflash.domain.usecase.prefs

import android.content.SharedPreferences
import android.content.res.Resources
import com.jerubrin.tallyflash.R
import com.jerubrin.tallyflash.entity.ConnectionData
import javax.inject.Inject


class ReadSharedPrefConnectionUseCase @Inject constructor (
    private val resources: Resources,
    private val sharedPref: SharedPreferences
) : BasePrefsUseCase<ConnectionData, Unit>() {
    
    override fun execute(params: Unit): ConnectionData {
        with(resources) {
            val url = sharedPref.getString(
                getString(R.string.ip_address_key),
                getString(R.string.default_ip_address)
            ) ?: getString(R.string.default_ip_address)
    
            val port = sharedPref.getString(
                getString(R.string.port_key),
                getString(R.string.default_port)
            ) ?: getString(R.string.default_port)
    
            return ConnectionData(url, port)
        }
    }
    
}