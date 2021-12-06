package com.jerubrin.tallyflash.di

import android.content.SharedPreferences
import com.jerubrin.tallyflash.data.ResourcesInterface
import com.jerubrin.tallyflash.domain.usecase.prefs.BasePrefsUseCase
import com.jerubrin.tallyflash.domain.usecase.prefs.ReadSharedPrefConnectionUseCase
import com.jerubrin.tallyflash.domain.usecase.prefs.ReadSharedPrefMainUseCase
import com.jerubrin.tallyflash.domain.usecase.prefs.WriteSharedPrefConnectionUseCase
import com.jerubrin.tallyflash.entity.ConnectionData
import com.jerubrin.tallyflash.entity.SettingsData
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object SharedPrefUseCaseModule {
    
    @Provides
    fun providesReadSharedPrefMainUseCase(
        resources: ResourcesInterface,
        sharedPrefs: SharedPreferences
    ): BasePrefsUseCase<SettingsData, Unit> =
        ReadSharedPrefMainUseCase(resources, sharedPrefs)
    
    @Provides
    fun providesReadSharedPrefConnectionUseCase(
        resources: ResourcesInterface,
        sharedPrefs: SharedPreferences
    ): BasePrefsUseCase<ConnectionData, Unit> =
        ReadSharedPrefConnectionUseCase(resources, sharedPrefs)
    
    @Provides
    fun providesWriteSharedPrefConnectionUseCase(
        resources: ResourcesInterface,
        sharedPrefs: SharedPreferences
    ): BasePrefsUseCase<Boolean, ConnectionData> =
        WriteSharedPrefConnectionUseCase(resources, sharedPrefs)
}