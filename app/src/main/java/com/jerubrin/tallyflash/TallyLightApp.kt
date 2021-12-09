package com.jerubrin.tallyflash

import android.app.Application
import android.content.Context
import android.content.Intent
import android.os.Build
import com.jerubrin.tallyflash.presentation.service.SceneStateService
import com.jerubrin.tallyflash.presentation.service.SceneStateServiceConnection
import dagger.hilt.android.HiltAndroidApp


@HiltAndroidApp
class TallyLightApp : Application() {
    
    override fun onCreate() {
        super.onCreate()
        val serviceIntent = Intent(this, SceneStateService::class.java).also {
            bindService(it, SceneStateServiceConnection, Context.BIND_AUTO_CREATE)
        }
        
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            startForegroundService(serviceIntent)
        } else {
            startService(serviceIntent)
        }
    }
    
}