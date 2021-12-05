package com.jerubrin.tallyflash.di

import com.jerubrin.tallyflash.TestReadSharedPrefConnectionUseCase
import com.jerubrin.tallyflash.domain.prefs.TestReadSharedPrefMainUseCase
import com.jerubrin.tallyflash.domain.prefs.TestWriteSharedPrefConnectionUseCase
import com.jerubrin.tallyflash.domain.usecase.prefs.BasePrefsUseCase
import com.jerubrin.tallyflash.entity.ConnectionData
import com.jerubrin.tallyflash.entity.SettingsData
import dagger.Module
import dagger.Provides
import dagger.hilt.components.SingletonComponent
import dagger.hilt.testing.TestInstallIn
import javax.inject.Singleton


@Module
@TestInstallIn(
    components = [SingletonComponent::class],
    replaces = [SharedPrefUseCaseModule::class]
)
object TestSharedPrefUseCaseModule {
    
    @Singleton
    @Provides
    fun providesReadSharedPrefMainUseCase(): BasePrefsUseCase<SettingsData, Unit> =
        TestReadSharedPrefMainUseCase()
    
    @Singleton
    @Provides
    fun providesWriteSharedPrefConnectionUseCase(): BasePrefsUseCase<Boolean, ConnectionData> =
        TestWriteSharedPrefConnectionUseCase()
    
    @Singleton
    @Provides
    fun providesReadSharedPrefConnectionUseCase(): BasePrefsUseCase<ConnectionData, Unit> =
        TestReadSharedPrefConnectionUseCase()
    
}