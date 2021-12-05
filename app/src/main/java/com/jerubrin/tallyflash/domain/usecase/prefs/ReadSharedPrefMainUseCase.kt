package com.jerubrin.tallyflash.domain.usecase.prefs

import android.content.SharedPreferences
import android.content.res.Resources
import androidx.core.graphics.toColorInt
import com.jerubrin.tallyflash.R
import com.jerubrin.tallyflash.domain.State
import com.jerubrin.tallyflash.domain.usecase.BaseUseCase
import com.jerubrin.tallyflash.entity.FlashReactionState
import com.jerubrin.tallyflash.entity.SettingsData
import javax.inject.Inject


class ReadSharedPrefMainUseCase @Inject constructor(
    private val resources: Resources,
    private val sharedPref: SharedPreferences
) : BasePrefsUseCase<SettingsData, Unit>() {
    
    override fun execute(params: Unit): SettingsData =
        getSettingsData()
    
    private fun getSettingsData(): SettingsData {
        with(resources) {
            val activeColor =
                sharedPref.getInt(
                    getString(R.string.active_color_key),
                    getString(R.string.active_default_color).toColorInt()
                )
            val previewColor =
                sharedPref.getInt(
                    getString(R.string.preview_color_key),
                    getString(R.string.preview_default_color).toColorInt()
                )
            val offColor =
                sharedPref.getInt(
                    getString(R.string.off_color_key),
                    getString(R.string.off_default_color).toColorInt()
                )
            
            val activeText =
                sharedPref.getString(
                    getString(R.string.active_text_key),
                    getString(R.string.active_default_text)
                ) ?: getString(R.string.active_default_text)
            val previewText =
                sharedPref.getString(
                    getString(R.string.preview_text_key),
                    getString(R.string.preview_default_text)
                ) ?: getString(R.string.preview_default_text)
            val offText =
                sharedPref.getString(
                    getString(R.string.off_text_key),
                    getString(R.string.off_default_text)
                ) ?: getString(R.string.off_default_text)
            
            val activeFlash: FlashReactionState =
                intToFlashReactionState(
                    sharedPref.getString(
                        getString(R.string.active_flash_key),
                        getString(R.string.default_value_active)
                    )?.toIntOrNull() ?: getString(R.string.default_value_active).toInt()
                )
            val previewFlash: FlashReactionState =
                intToFlashReactionState(
                    sharedPref.getString(
                        getString(R.string.preview_flash_key),
                        getString(R.string.default_value_preview)
                    )?.toIntOrNull() ?: getString(R.string.default_value_preview).toInt()
                )
            val blinkAfterActive: Boolean =
                sharedPref.getBoolean(
                    getString(R.string.preview_blink_key),
                    getString(R.string.preview_default_blink).toBooleanStrictOrNull() ?: false
                )
    
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
    }
    
    private fun intToFlashReactionState(state: Int): FlashReactionState =
        when (state) {
            FlashReactionState.ON.state -> FlashReactionState.ON
            FlashReactionState.BLINK.state -> FlashReactionState.BLINK
            else -> FlashReactionState.OFF
        }
    
}