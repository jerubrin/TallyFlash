package com.jerubrin.tallyflash.presentation.service

import android.app.Service
import android.content.Context
import android.content.pm.PackageManager
import android.hardware.camera2.CameraCharacteristics
import android.hardware.camera2.CameraManager
import android.os.Build
import com.jerubrin.tallyflash.domain.usecase.prefs.BasePrefsUseCase
import com.jerubrin.tallyflash.entity.FlashReactionState
import com.jerubrin.tallyflash.entity.SceneState
import com.jerubrin.tallyflash.entity.SettingsData
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Singleton


@Singleton
class FlashController(
    private val context: Context,
    private val flashTimer: FlashTimer,
    private val readSharedPrefMainUseCase: BasePrefsUseCase<SettingsData, Unit>
) {
    
    private var isBlinking = false
    private var wasSceneState: SceneState = SceneState.OFF
    
    private var cameraManager: CameraManager? = null
    
    fun execute(): Boolean =
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            cameraManager = context.getSystemService(Service.CAMERA_SERVICE) as CameraManager
            doBlink()
            true
        } else {
            false
        }
    
    fun changeFlashLightState(sceneState: SceneState) {
        val isFlashAvailable = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            cameraManager
                ?.getCameraCharacteristics("0")
                ?.get(CameraCharacteristics.FLASH_INFO_AVAILABLE) ?: false
        } else {
            false
        }
        //            .get(CameraCharacteristics.FLASH_INFO_AVAILABLE)
        if (
            context.packageManager.hasSystemFeature(PackageManager.FEATURE_CAMERA_ANY) &&
            context.packageManager.hasSystemFeature(PackageManager.FEATURE_CAMERA_FLASH) &&
            isFlashAvailable
        ) {
            if (sceneState != wasSceneState) {
                when (sceneState) {
                    SceneState.ACTIVE ->
                        doActiveFlash()
                    SceneState.PREVIEW ->
                        doPreviewFlash()
                    SceneState.OFF ->
                        doOffFlash()
                    else ->
                        Unit
                }
                
                wasSceneState = sceneState
            }
        }
    }
    
    private fun doActiveFlash() {
        setFlashLight(readSharedPrefMainUseCase.execute(Unit).activeFlash)
    }
    
    private fun doPreviewFlash() {
        setFlashLight(readSharedPrefMainUseCase.execute(Unit).previewFlash)
    }
    
    private fun doOffFlash() {
        setFlashLight(false)
        offBlink()
    }
    
    private fun setFlashLight(flashReactionState: FlashReactionState) {
        when(flashReactionState) {
            FlashReactionState.ON -> {
                offBlink()
                setFlashLight(true)
            }
            FlashReactionState.BLINK -> {
                if (
                    wasSceneState != SceneState.ACTIVE ||
                    readSharedPrefMainUseCase.execute(Unit).blinkAfterActive
                ) {
                    onBlink()
                } else {
                    doOffFlash()
                }
            }
            else -> doOffFlash()
        }
    }
    
    private fun onBlink() { isBlinking = true }
    
    private fun offBlink() { isBlinking = false }
    
    private fun setFlashLight(switch: Boolean) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            cameraManager?.setTorchMode("0", switch)
        }
    }
    
    private fun doBlink() {
        CoroutineScope(Dispatchers.IO).launch {
            flashTimer.isFlashOn.collectLatest {
                if(isBlinking){
                    setFlashLight(flashTimer.isFlashOn.value)
                }
            }
        }
    }
    
}