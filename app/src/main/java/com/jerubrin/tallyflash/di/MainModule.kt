package com.jerubrin.tallyflash.di

import android.content.Context
import com.jerubrin.tallyflash.data.SharedPrefMainProvider
import com.jerubrin.tallyflash.flashlight.FlashTimer
import com.jerubrin.tallyflash.prefs.SharedPrefConnectionProvider
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton
import android.content.Intent
import com.jerubrin.tallyflash.flashlight.FlashControlService


@Module
@InstallIn(SingletonComponent::class)
object MainModule {
    
    @Provides
    fun providesSharedPrefMainProvider(
        @ApplicationContext context: Context
    ) = SharedPrefMainProvider(context)
    
    @Provides
    fun providesSharedPrefConnectionProvider(
        @ApplicationContext context: Context
    ) = SharedPrefConnectionProvider(context)
    
    @Singleton
    @Provides
    fun providesFlashTimer(): FlashTimer =
        FlashTimer(200L)
    
    @Singleton
    @Provides
    fun provideServiceIntent(
        @ApplicationContext context: Context
    ) = Intent(context, FlashControlService::class.java)
}