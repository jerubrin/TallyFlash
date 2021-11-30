package com.jerubrin.tallyflash.domain.usecase

import android.content.Context
import android.content.SharedPreferences
import com.jerubrin.tallyflash.entity.SettingsData
import com.jerubrin.tallyflash.entity.SharedSettingsConst.SHARED_ACTIVE_COLOR
import com.jerubrin.tallyflash.entity.SharedSettingsConst.SHARED_ACTIVE_FLASH
import com.jerubrin.tallyflash.entity.SharedSettingsConst.SHARED_ACTIVE_TEXT
import com.jerubrin.tallyflash.entity.SharedSettingsConst.SHARED_BLINK_AFTER
import com.jerubrin.tallyflash.entity.SharedSettingsConst.SHARED_OFF_COLOR
import com.jerubrin.tallyflash.entity.SharedSettingsConst.SHARED_OFF_TEXT
import com.jerubrin.tallyflash.entity.SharedSettingsConst.SHARED_PREVIEW_COLOR
import com.jerubrin.tallyflash.entity.SharedSettingsConst.SHARED_PREVIEW_FLASH
import com.jerubrin.tallyflash.entity.SharedSettingsConst.SHARED_PREVIEW_TEXT
import com.jerubrin.tallyflash.entity.SharedSettingsConst.SHARED_SETTINGS

class WriteSharedPrefMainUseCase (
    private val context: Context
) : BaseUseCase<Boolean, SettingsData>() {
    
    private val sharedPref: SharedPreferences
        get() = context.getSharedPreferences(SHARED_SETTINGS, Context.MODE_PRIVATE)
    
    override fun execute(params: SettingsData): Boolean {
        sharedPref.edit()?.apply {
            putInt(SHARED_ACTIVE_COLOR, params.activeColor)
            putInt(SHARED_PREVIEW_COLOR, params.previewColor)
            putInt(SHARED_OFF_COLOR, params.offColor)
            putString(SHARED_ACTIVE_TEXT, params.activeText)
            putString(SHARED_PREVIEW_TEXT, params.previewText)
            putString(SHARED_OFF_TEXT, params.offText)
            putInt(SHARED_ACTIVE_FLASH, params.activeFlash.state)
            putInt(SHARED_PREVIEW_FLASH, params.previewFlash.state)
            putBoolean(SHARED_BLINK_AFTER, params.blinkAfterActive)
            return commit()
        }
        return false
    }
    
    
}