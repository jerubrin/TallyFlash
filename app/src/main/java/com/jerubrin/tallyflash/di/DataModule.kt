package com.jerubrin.tallyflash.di

import android.content.Context
import android.content.SharedPreferences
import androidx.preference.PreferenceManager
import com.jerubrin.tallyflash.data.VMixRepository
import com.jerubrin.tallyflash.data.VMixRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton
import com.jerubrin.tallyflash.data.retrofit.VMixApi
import dagger.hilt.android.qualifiers.ApplicationContext


@Module
@InstallIn(SingletonComponent::class)
object DataModule {
    
    @Provides
    @Singleton
    fun providesVMixApi(): VMixApi =
        VMixApi()
    
    @Provides
    @Singleton
    fun providesVMixRepository (
        vMixApi: VMixApi
    ): VMixRepository =
        VMixRepositoryImpl(vMixApi)
    
    @Provides
    @Singleton
    fun providesSharedPreferences (
        @ApplicationContext context: Context
    ): SharedPreferences =
        PreferenceManager.getDefaultSharedPreferences(context)
    
}