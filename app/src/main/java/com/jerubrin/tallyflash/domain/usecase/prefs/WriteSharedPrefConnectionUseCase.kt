package com.jerubrin.tallyflash.domain.usecase.prefs

import android.content.SharedPreferences
import android.content.res.Resources
import com.jerubrin.tallyflash.R
import com.jerubrin.tallyflash.entity.ConnectionData


class WriteSharedPrefConnectionUseCase (
    private val resources: Resources,
    private val sharedPref: SharedPreferences
) : BasePrefsUseCase<Boolean, ConnectionData>() {
    
    override fun execute(params: ConnectionData): Boolean {
        with(resources) {
            sharedPref.edit()?.apply {
                putString(getString(R.string.ip_address_key), params.ip)
                putString(getString(R.string.port_key), params.port)
                return commit()
            }
        }
        return false
    }
    
}