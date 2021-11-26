package com.jerubrin.tallyflash.di

import android.content.Context
import android.content.Intent
import com.jerubrin.tallyflash.flashlight.FlashControlService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ServiceComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.android.scopes.ServiceScoped

@Module
@InstallIn(ServiceComponent::class)
object ServiceModule {
    
    @ServiceScoped
    @Provides
    fun provideContext(
        @ApplicationContext context: Context
    ) = context

}