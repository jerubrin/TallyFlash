package com.jerubrin.tallyflash.presentation.service

import android.content.ComponentName
import android.content.ServiceConnection
import android.os.IBinder


class SceneStateServiceConnection : ServiceConnection {
    
    private var _service: SceneStateService? = null
    val service get() = _service
    
    override fun onServiceConnected(className: ComponentName, service: IBinder) {
        val binder = service as SceneStateService.LocalBinder
        _service = binder.getService()
    }
    
    override fun onServiceDisconnected(p0: ComponentName?) {
        _service = null
    }
}