package com.jerubrin.tallyflash.entity


data class SettingsData (
    val activeColor: Int,
    val previewColor: Int,
    val offColor: Int,
    val activeText: String,
    val previewText: String,
    val offText: String,
    val activeFlash : FlashReactionState,
    val previewFlash : FlashReactionState,
    val blinkAfterActive : Boolean
)

enum class FlashReactionState(val state: Int) {
    OFF(0),
    ON(1),
    BLINK(2)
}