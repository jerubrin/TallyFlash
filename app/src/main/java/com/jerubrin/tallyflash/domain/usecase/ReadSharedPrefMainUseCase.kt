package com.jerubrin.tallyflash.domain.usecase

import android.content.Context
import android.content.SharedPreferences
import com.jerubrin.tallyflash.entity.FlashReactionState
import com.jerubrin.tallyflash.entity.SettingsData
import com.jerubrin.tallyflash.entity.SharedSettingsConst.DEFAULT_ACTIVE_COLOR
import com.jerubrin.tallyflash.entity.SharedSettingsConst.DEFAULT_ACTIVE_FLASH
import com.jerubrin.tallyflash.entity.SharedSettingsConst.DEFAULT_ACTIVE_TEXT
import com.jerubrin.tallyflash.entity.SharedSettingsConst.DEFAULT_BLINK_AFTER
import com.jerubrin.tallyflash.entity.SharedSettingsConst.DEFAULT_OFF_COLOR
import com.jerubrin.tallyflash.entity.SharedSettingsConst.DEFAULT_OFF_TEXT
import com.jerubrin.tallyflash.entity.SharedSettingsConst.DEFAULT_PREVIEW_COLOR
import com.jerubrin.tallyflash.entity.SharedSettingsConst.DEFAULT_PREVIEW_FLASH
import com.jerubrin.tallyflash.entity.SharedSettingsConst.DEFAULT_PREVIEW_TEXT
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


class ReadSharedPrefMainUseCase (
    private val context: Context
) : BaseUseCase<SettingsData, Unit>() {
    
    private val sharedPref: SharedPreferences
        get() = context.getSharedPreferences(SHARED_SETTINGS, Context.MODE_PRIVATE)
    
    override fun execute(params: Unit): SettingsData {
        val activeColor =
            sharedPref.getInt(SHARED_ACTIVE_COLOR, DEFAULT_ACTIVE_COLOR)
        val previewColor =
            sharedPref.getInt(SHARED_PREVIEW_COLOR, DEFAULT_PREVIEW_COLOR)
        val offColor =
            sharedPref.getInt(SHARED_OFF_COLOR, DEFAULT_OFF_COLOR)
        val activeText =
            sharedPref.getString(SHARED_ACTIVE_TEXT, DEFAULT_ACTIVE_TEXT) ?: DEFAULT_ACTIVE_TEXT
        val previewText =
            sharedPref.getString(SHARED_PREVIEW_TEXT, DEFAULT_PREVIEW_TEXT) ?: DEFAULT_PREVIEW_TEXT
        val offText =
            sharedPref.getString(SHARED_OFF_TEXT, DEFAULT_OFF_TEXT) ?: DEFAULT_OFF_TEXT
        val activeFlash : FlashReactionState =
            intToFlashReactionState(
                sharedPref.getInt(SHARED_ACTIVE_FLASH, DEFAULT_ACTIVE_FLASH.state)
            )
        val previewFlash : FlashReactionState =
            intToFlashReactionState(
                sharedPref.getInt(SHARED_PREVIEW_FLASH, DEFAULT_PREVIEW_FLASH.state)
            )
        val blinkAfterActive : Boolean =
            sharedPref.getBoolean(SHARED_BLINK_AFTER, DEFAULT_BLINK_AFTER)
        
        return SettingsData(
            activeColor = activeColor,
            previewColor = previewColor,
            offColor = offColor,
            activeText = activeText,
            previewText = previewText,
            offText = offText,
            activeFlash = activeFlash,
            previewFlash = previewFlash,
            blinkAfterActive = blinkAfterActive
        )
    }
    
    private fun intToFlashReactionState(state: Int): FlashReactionState =
        when (state) {
            FlashReactionState.ON.state -> FlashReactionState.ON
            FlashReactionState.BLINK.state -> FlashReactionState.BLINK
            else -> FlashReactionState.OFF
        }
    
}