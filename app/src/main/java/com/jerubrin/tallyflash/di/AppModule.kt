package com.jerubrin.tallyflash.di

import android.content.Context
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton
import com.jerubrin.tallyflash.data.retrofit.VMixApi
import com.jerubrin.tallyflash.domain.usecase.ReadSharedPrefConnectionUseCase
import com.jerubrin.tallyflash.domain.usecase.ReadSharedPrefMainUseCase
import com.jerubrin.tallyflash.domain.usecase.WriteSharedPrefConnectionUseCase
import com.jerubrin.tallyflash.domain.usecase.WriteSharedPrefMainUseCase
import com.jerubrin.tallyflash.service.SceneStateServiceConnection


@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    
    @Provides
    @Singleton
    fun providesVMixApi() =
        VMixApi()
    
    @Provides
    fun providesReadSharedPrefMainUseCase(
        @ApplicationContext context: Context
    ) = ReadSharedPrefMainUseCase(context)
    
    @Provides
    fun providesReadSharedPrefConnectionUseCase(
        @ApplicationContext context: Context
    ) = ReadSharedPrefConnectionUseCase(context)
    
    @Provides
    fun providesWriteSharedPrefMainUseCase(
        @ApplicationContext context: Context
    ) = WriteSharedPrefMainUseCase(context)
    
    @Provides
    fun providesWriteSharedPrefConnectionUseCase(
        @ApplicationContext context: Context
    ) = WriteSharedPrefConnectionUseCase(context)
    
    @Singleton
    @Provides
    fun providesServiceConnection() =
        SceneStateServiceConnection()
    
}