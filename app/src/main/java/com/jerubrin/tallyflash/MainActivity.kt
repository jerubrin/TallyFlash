package com.jerubrin.tallyflash

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.jerubrin.tallyflash.flashlight.FlashControlService
import com.jerubrin.tallyflash.flashlight.FlashControlService.Companion.CURRENT_SCENE_NUMBER
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }
}