package com.jerubrin.tallyflash

import android.app.Application
import android.content.Context
import android.content.Intent
import android.os.Build
import com.jerubrin.tallyflash.presentation.service.SceneStateService
import com.jerubrin.tallyflash.presentation.service.SceneStateServiceConnection
import dagger.hilt.android.HiltAndroidApp
import javax.inject.Inject


@HiltAndroidApp
class TallyLightApp : Application() {
    @Inject
    lateinit var serviceConnection: SceneStateServiceConnection
    
    override fun onCreate() {
        super.onCreate()
        val serviceIntent = Intent(this, SceneStateService::class.java).also {
            bindService(it, serviceConnection, Context.BIND_AUTO_CREATE)
        }
        
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            startForegroundService(serviceIntent)
        } else {
            startService(serviceIntent)
        }
    }
    
}