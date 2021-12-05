package com.jerubrin.tallyflash.presentation.service

import android.content.ComponentName
import android.content.ServiceConnection
import android.os.IBinder


object SceneStateServiceConnection : ServiceConnection {
    
    private var mService: SceneStateService? = null
    val service get() = mService as SceneStateServiceControl
    
    private var mBind: Boolean = false
    val bind get() = mBind
    
    override fun onServiceConnected(className: ComponentName, service: IBinder) {
        val binder = service as SceneStateService.LocalBinder
        mService = binder.getService()
        mBind = true
    }
    
    override fun onServiceDisconnected(p0: ComponentName?) {
        mService = null
        mBind = false
    }
}