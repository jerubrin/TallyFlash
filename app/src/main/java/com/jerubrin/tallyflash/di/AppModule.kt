package com.jerubrin.tallyflash.di

import android.content.Context
import com.jerubrin.tallyflash.data.AppResources
import com.jerubrin.tallyflash.data.ResourcesInterface
import com.jerubrin.tallyflash.presentation.service.SceneStateServiceConnection
import com.jerubrin.tallyflash.presentation.service.SceneStateServiceControl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent


@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    
    @Provides
    fun providesService(): SceneStateServiceControl =
        SceneStateServiceConnection.service
    
    @Provides
    fun providesResources(
        @ApplicationContext context: Context
    ): ResourcesInterface =
        AppResources(context)
    
}