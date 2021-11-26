package com.jerubrin.tallyflash.data

import android.content.Context
import android.content.SharedPreferences
import android.graphics.Color
import com.jerubrin.tallyflash.ui.ConnectFragment
import com.jerubrin.tallyflash.ui.SettingsFragment

class SharedPrefMainProvider (
    private val context: Context
) {
    
    private val sharedPref: SharedPreferences
        get() = context.getSharedPreferences(SHARED_SETTINGS, Context.MODE_PRIVATE)
    
    fun getActiveColor() =
        sharedPref.getInt(SHARED_ACTIVE_COLOR, DEFAULT_ACTIVE_COLOR)
    fun setActiveColor(color: Int) =
        sharedPref.edit()?.apply{
            putInt(SHARED_ACTIVE_COLOR, color)
            apply()
        }
    
    fun getPreviewColor() =
        sharedPref.getInt(SHARED_PREVIEW_COLOR, DEFAULT_PREVIEW_COLOR)
    fun setPreviewColor(color: Int) =
        sharedPref.edit()?.apply{
            putInt(SHARED_PREVIEW_COLOR, color)
            apply()
        }
    
    fun getOffColor() =
        sharedPref.getInt(SHARED_OFF_COLOR, DEFAULT_OFF_COLOR)
    fun setOffColor(color: Int) =
        sharedPref.edit()?.apply{
            putInt(SHARED_OFF_COLOR, color)
            apply()
        }
    
    fun getActiveText() =
        sharedPref.getString(SHARED_ACTIVE_TEXT, DEFAULT_ACTIVE_TEXT) ?: DEFAULT_ACTIVE_TEXT
    fun setActiveText(text: String) =
        sharedPref.edit()?.apply{
            putString(SHARED_ACTIVE_TEXT, text)
            apply()
        }
    
    fun getPreviewText() =
        sharedPref.getString(SHARED_PREVIEW_TEXT, DEFAULT_PREVIEW_TEXT) ?: DEFAULT_PREVIEW_TEXT
    fun setPreviewText(text: String) =
        sharedPref.edit()?.apply{
            putString(SHARED_PREVIEW_TEXT, text)
            apply()
        }
    
    fun getOffText() =
        sharedPref.getString(SHARED_OFF_TEXT, DEFAULT_OFF_TEXT) ?: DEFAULT_OFF_TEXT
    fun setOffText(text: String) =
        sharedPref.edit()?.apply{
            putString(SHARED_OFF_TEXT, text)
            apply()
        }
    
    fun getActiveFlash() : FlashReactionState =
        when (sharedPref.getInt(SHARED_ACTIVE_FLASH, DEFAULT_ACTIVE_FLASH.state)) {
            FlashReactionState.ON.state -> FlashReactionState.ON
            FlashReactionState.BLINK.state -> FlashReactionState.BLINK
            else -> FlashReactionState.OFF
        }
    fun setActiveFlash(flashReactionState: FlashReactionState) =
        sharedPref.edit()?.apply{
            putInt(SHARED_ACTIVE_FLASH, flashReactionState.state)
            apply()
        }
    
    fun getPreviewFlash() : FlashReactionState =
        when (sharedPref.getInt(SHARED_PREVIEW_FLASH, DEFAULT_PREVIEW_FLASH.state)) {
            FlashReactionState.ON.state -> FlashReactionState.ON
            FlashReactionState.BLINK.state -> FlashReactionState.BLINK
            else -> FlashReactionState.OFF
        }
    fun setPreviewFlash(flashReactionState: FlashReactionState) =
        sharedPref.edit()?.apply{
            putInt(SHARED_PREVIEW_FLASH, flashReactionState.state)
            apply()
        }
    
    
    companion object {
        const val SHARED_SETTINGS = "com.jerubrin.tallyflash.shared.settings"
        
        const val SHARED_ACTIVE_COLOR = "com.jerubrin.tallyflash.shared.color.active"
        const val SHARED_ACTIVE_TEXT = "com.jerubrin.tallyflash.shared.text.active"
        const val SHARED_ACTIVE_FLASH = "com.jerubrin.tallyflash.shared.flash.active"
        
        const val SHARED_PREVIEW_COLOR = "com.jerubrin.tallyflash.shared.color.preview"
        const val SHARED_PREVIEW_TEXT = "com.jerubrin.tallyflash.shared.text.preview"
        const val SHARED_PREVIEW_FLASH = "com.jerubrin.tallyflash.shared.flash.preview"
        
        const val SHARED_OFF_COLOR = "com.jerubrin.tallyflash.shared.color.off"
        const val SHARED_OFF_TEXT = "com.jerubrin.tallyflash.shared.text.off"
        
        
        const val DEFAULT_ACTIVE_COLOR = Color.RED
        const val DEFAULT_ACTIVE_TEXT = "Active"
        val DEFAULT_ACTIVE_FLASH = FlashReactionState.ON
        
        const val DEFAULT_PREVIEW_COLOR = -29696
        const val DEFAULT_PREVIEW_TEXT = "Preview"
        val DEFAULT_PREVIEW_FLASH = FlashReactionState.BLINK
        
        const val DEFAULT_OFF_COLOR = Color.BLACK
        const val DEFAULT_OFF_TEXT = "Off"
    }
    
    enum class FlashReactionState(val state: Int) {
        OFF(0),
        ON(1),
        BLINK(2)
    }
}