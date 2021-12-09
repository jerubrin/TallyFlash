package com.jerubrin.tallyflash.presentation.service

import android.content.ComponentName
import android.content.ServiceConnection
import android.os.IBinder


object SceneStateServiceConnection : ServiceConnection {
    
    private var mService: SceneStateService? = null
    val service get() = mService as SceneStateServiceControl
    
    override fun onServiceConnected(className: ComponentName, service: IBinder) {
        val binder = service as SceneStateService.LocalBinder
        mService = binder.getService()
    }
    
    override fun onServiceDisconnected(p0: ComponentName?) {
        mService = null
    }
}