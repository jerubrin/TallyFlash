package com.jerubrin.tallyflash.domain.prefs

import com.jerubrin.tallyflash.domain.usecase.prefs.BasePrefsUseCase
import com.jerubrin.tallyflash.entity.FlashReactionState
import com.jerubrin.tallyflash.entity.SettingsData

class TestReadSharedPrefMainUseCase : BasePrefsUseCase<SettingsData, Unit>() {
    
    override fun execute(params: Unit): SettingsData =
        SettingsData(
            activeColor = ACTIVE_COLOR,
            previewColor = PREVIEW_COLOR,
            offColor = OFF_COLOR,
            activeText = ACTIVE_TEXT,
            previewText = PREVIEW_TEXT,
            offText = OFF_TEXT,
            activeFlash = ACTIVE_FLASH,
            previewFlash = PREVIEW_FLASH,
            blinkAfterActive = BLINK_AFTER_ACTIVE
        )
    
    companion object {
        const val ACTIVE_COLOR = 0
        const val PREVIEW_COLOR = 1
        const val OFF_COLOR = 2
        const val ACTIVE_TEXT = "Active"
        const val PREVIEW_TEXT = "Preview"
        const val OFF_TEXT = "Off"
        val ACTIVE_FLASH = FlashReactionState.ON
        val PREVIEW_FLASH get() = FlashReactionState.BLINK
        const val BLINK_AFTER_ACTIVE = false
    }
}