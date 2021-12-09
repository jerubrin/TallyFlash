package com.jerubrin.tallyflash.di

import android.app.Notification
import android.content.Context
import android.os.Build
import androidx.annotation.RequiresApi
import com.jerubrin.tallyflash.R
import com.jerubrin.tallyflash.domain.usecase.prefs.BasePrefsUseCase
import com.jerubrin.tallyflash.entity.SettingsData
import com.jerubrin.tallyflash.presentation.service.FlashController
import com.jerubrin.tallyflash.presentation.service.FlashTimer
import com.jerubrin.tallyflash.presentation.service.SceneStateService.Companion.CHANNEL_DEFAULT_IMPORTANCE
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ServiceComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.android.scopes.ServiceScoped


@Module
@InstallIn(ServiceComponent::class)
object ServiceModule {
    
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
        readSharedPrefMainUseCase: BasePrefsUseCase<SettingsData, Unit>
    ) =
        FlashController(context, flashTimer, readSharedPrefMainUseCase)
    
    @ServiceScoped
    @Provides
    fun providesFlashTimer(): FlashTimer =
        FlashTimer(200L)
}