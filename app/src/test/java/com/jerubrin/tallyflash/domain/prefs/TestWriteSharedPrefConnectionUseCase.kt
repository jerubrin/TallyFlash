package com.jerubrin.tallyflash.domain.prefs

import com.jerubrin.tallyflash.domain.usecase.prefs.BasePrefsUseCase
import com.jerubrin.tallyflash.entity.ConnectionData

class TestWriteSharedPrefConnectionUseCase : BasePrefsUseCase<Boolean, ConnectionData>() {
    
    override fun execute(params: ConnectionData): Boolean = true
    
}