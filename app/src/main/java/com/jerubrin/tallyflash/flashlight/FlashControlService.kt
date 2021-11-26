package com.jerubrin.tallyflash.flashlight

import android.app.Service
import android.content.Intent
import android.content.pm.PackageManager
import android.hardware.camera2.CameraManager
import android.os.Build
import android.os.IBinder
import androidx.annotation.RequiresApi
import com.jerubrin.tallyflash.data.SharedPrefMainProvider
import com.jerubrin.tallyflash.data.WorkingScenes
import com.jerubrin.tallyflash.domain.UiState
import com.jerubrin.tallyflash.domain.VMixInteractor
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject
import javax.inject.Singleton

@AndroidEntryPoint
class FlashControlService : Service() {
    
    @Inject
    lateinit var vMixInteractor: VMixInteractor
    
    @Inject
    lateinit var flashTimer: FlashTimer
    
    @Inject
    lateinit var sharedPrefMain: SharedPrefMainProvider
    
    var cameraManager: CameraManager? = null
    
    private var currentSceneNumber = -1
    
    @RequiresApi(Build.VERSION_CODES.M)
    override fun onCreate() {
        super.onCreate()
        cameraManager = getSystemService(CAMERA_SERVICE) as CameraManager
        doBlink()
    }
    
    @RequiresApi(Build.VERSION_CODES.M)
    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        currentSceneNumber = intent?.getIntExtra(CURRENT_SCENE_NUMBER, -1) ?: -1
        runFlashControl()
        return super.onStartCommand(intent, flags, startId)
    }
    
    @RequiresApi(Build.VERSION_CODES.M)
    override fun onDestroy() {
        super.onDestroy()
        doOffFlash()
    }
    
    @RequiresApi(Build.VERSION_CODES.M)
    fun runFlashControl() {
        if (
            packageManager.hasSystemFeature(PackageManager.FEATURE_CAMERA_ANY) &&
            packageManager.hasSystemFeature(PackageManager.FEATURE_CAMERA_FLASH)
        ) {
            CoroutineScope(Dispatchers.IO).launch {
                vMixInteractor.uiState.collectLatest { uiState ->
                    if (
                        uiState is UiState.Ready<*> &&
                        uiState.data is WorkingScenes &&
                        currentSceneNumber != -1
                    ) {
                        val workingScenes: WorkingScenes = uiState.data
                        when(currentSceneNumber) {
                            workingScenes.active ->
                                doActiveFlash()
                            workingScenes.preview ->
                                doPreviewFlash()
                            else ->
                                doOffFlash()
                        }
                    } else {
                        doOffFlash()
                    }
                    delay(200L)
                }
            }
        } else return;
    }
    
    
    @RequiresApi(Build.VERSION_CODES.M)
    private fun doActiveFlash() {
        setFlashLight(sharedPrefMain.getActiveFlash())
    }
    
    @RequiresApi(Build.VERSION_CODES.M)
    private fun doPreviewFlash() {
        setFlashLight(sharedPrefMain.getPreviewFlash())
    }
    
    @RequiresApi(Build.VERSION_CODES.M)
    private fun doOffFlash() {
        offLight()
        offBlink()
    }
    
    @RequiresApi(Build.VERSION_CODES.M)
    private fun setFlashLight(flashReactionState: SharedPrefMainProvider.FlashReactionState) {
        when(flashReactionState) {
            SharedPrefMainProvider.FlashReactionState.ON -> {
                doOffFlash()
                onLight()
            }
            SharedPrefMainProvider.FlashReactionState.BLINK -> {
                doOffFlash()
                onBlink()
            }
            else -> doOffFlash()
        }
    }
    
    private var isBlinking = false
    
    private fun onBlink() { isBlinking = true }
    
    private fun offBlink() { isBlinking = false }
    
    @RequiresApi(Build.VERSION_CODES.M)
    private fun onLight() {
        cameraManager?.setTorchMode("0", true)
    }
    
    @RequiresApi(Build.VERSION_CODES.M)
    private fun offLight() {
        cameraManager?.setTorchMode("0", false)
    }
    
    @RequiresApi(Build.VERSION_CODES.M)
    private fun doBlink() {
        CoroutineScope(Dispatchers.IO).launch {
            flashTimer.isFlashOn.collectLatest {
                if(isBlinking){
                    if (flashTimer.isFlashOn.value) onLight() else offLight()
                }
            }
        }
    }
    
    override fun onBind(p0: Intent?): IBinder? {
        return null
    }
    
    companion object {
        const val CURRENT_SCENE_NUMBER = "com.jerubrin.tallyflash.flashlight.number"
    }
}