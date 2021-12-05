package com.jerubrin.tallyflash.di

import com.jerubrin.tallyflash.presentation.service.SceneStateServiceControl
import com.jerubrin.tallyflash.presentation.service.TestSceneStateServiceControl
import dagger.Module
import dagger.Provides
import dagger.hilt.components.SingletonComponent
import dagger.hilt.testing.TestInstallIn
import javax.inject.Singleton


@Module
@TestInstallIn(
    components = [SingletonComponent::class],
    replaces = [AppModule::class]
)
object TestAppModule {
    
    @Singleton
    @Provides
    fun providesService(): SceneStateServiceControl =
        TestSceneStateServiceControl()
    
}