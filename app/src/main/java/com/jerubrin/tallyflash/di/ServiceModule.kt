package com.jerubrin.tallyflash.di

import android.app.Notification
import android.content.Context
import android.os.Build
import androidx.annotation.RequiresApi
import com.jerubrin.tallyflash.R
import com.jerubrin.tallyflash.domain.usecase.ReadSharedPrefMainUseCase
import com.jerubrin.tallyflash.service.FlashController
import com.jerubrin.tallyflash.service.FlashTimer
import com.jerubrin.tallyflash.service.SceneStateService.Companion.CHANNEL_DEFAULT_IMPORTANCE
import com.jerubrin.tallyflash.service.SceneStateServiceConnection
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ServiceComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.android.scopes.ServiceScoped
import javax.inject.Singleton

@Module
@InstallIn(ServiceComponent::class)
class ServiceModule {
    
    @RequiresApi(Build.VERSION_CODES.O)
    @ServiceScoped
    @Provides
    fun providesNotification(
        @ApplicationContext context: Context
    ) = Notification
        .Builder(context, CHANNEL_DEFAULT_IMPORTANCE)
        .setSmallIcon(R.drawable.ic_flare)
    
    @ServiceScoped
    @Provides
    fun providesFlashController(
        @ApplicationContext context: Context,
        flashTimer: FlashTimer,
        readSharedPrefMainUseCase: ReadSharedPrefMainUseCase
    ) =
        FlashController(context, flashTimer, readSharedPrefMainUseCase)
    
    @ServiceScoped
    @Provides
    fun providesFlashTimer(): FlashTimer =
        FlashTimer(200L)
}