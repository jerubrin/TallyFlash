package com.jerubrin.tallyflash.entity

import android.graphics.Color
import com.jerubrin.tallyflash.entity.SharedSettingsConst.DEFAULT_ACTIVE_COLOR
import com.jerubrin.tallyflash.entity.SharedSettingsConst.DEFAULT_ACTIVE_FLASH
import com.jerubrin.tallyflash.entity.SharedSettingsConst.DEFAULT_ACTIVE_TEXT
import com.jerubrin.tallyflash.entity.SharedSettingsConst.DEFAULT_BLINK_AFTER
import com.jerubrin.tallyflash.entity.SharedSettingsConst.DEFAULT_OFF_COLOR
import com.jerubrin.tallyflash.entity.SharedSettingsConst.DEFAULT_OFF_TEXT
import com.jerubrin.tallyflash.entity.SharedSettingsConst.DEFAULT_PREVIEW_COLOR
import com.jerubrin.tallyflash.entity.SharedSettingsConst.DEFAULT_PREVIEW_FLASH
import com.jerubrin.tallyflash.entity.SharedSettingsConst.DEFAULT_PREVIEW_TEXT


data class SettingsData (
    val activeColor: Int = DEFAULT_ACTIVE_COLOR,
    val previewColor: Int = DEFAULT_PREVIEW_COLOR,
    val offColor: Int = DEFAULT_OFF_COLOR,
    val activeText: String = DEFAULT_ACTIVE_TEXT,
    val previewText: String = DEFAULT_PREVIEW_TEXT,
    val offText: String = DEFAULT_OFF_TEXT,
    val activeFlash : FlashReactionState = DEFAULT_ACTIVE_FLASH,
    val previewFlash : FlashReactionState = DEFAULT_PREVIEW_FLASH,
    val blinkAfterActive : Boolean = DEFAULT_BLINK_AFTER
)

enum class FlashReactionState(val state: Int) {
    OFF(0),
    ON(1),
    BLINK(2)
}

object SharedSettingsConst {
    const val SHARED_SETTINGS = "com.jerubrin.tallyflash.shared.settings"
    
    const val SHARED_ACTIVE_COLOR = "com.jerubrin.tallyflash.shared.color.active"
    const val SHARED_ACTIVE_TEXT = "com.jerubrin.tallyflash.shared.text.active"
    const val SHARED_ACTIVE_FLASH = "com.jerubrin.tallyflash.shared.flash.active"
    
    const val SHARED_PREVIEW_COLOR = "com.jerubrin.tallyflash.shared.color.preview"
    const val SHARED_PREVIEW_TEXT = "com.jerubrin.tallyflash.shared.text.preview"
    const val SHARED_PREVIEW_FLASH = "com.jerubrin.tallyflash.shared.flash.preview"
    
    const val SHARED_OFF_COLOR = "com.jerubrin.tallyflash.shared.color.off"
    const val SHARED_OFF_TEXT = "com.jerubrin.tallyflash.shared.text.off"
    
    const val SHARED_BLINK_AFTER = "com.jerubrin.tallyflash.shared.blink"
    
    
    const val DEFAULT_ACTIVE_COLOR = Color.RED
    const val DEFAULT_ACTIVE_TEXT = "Active"
    val DEFAULT_ACTIVE_FLASH = FlashReactionState.ON
    
    const val DEFAULT_PREVIEW_COLOR = -29696
    const val DEFAULT_PREVIEW_TEXT = "Preview"
    val DEFAULT_PREVIEW_FLASH = FlashReactionState.BLINK
    
    const val DEFAULT_OFF_COLOR = Color.BLACK
    const val DEFAULT_OFF_TEXT = "Off"
    
    const val DEFAULT_BLINK_AFTER = false
}