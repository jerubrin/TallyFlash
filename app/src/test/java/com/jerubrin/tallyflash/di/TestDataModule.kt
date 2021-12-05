package com.jerubrin.tallyflash.di

import com.jerubrin.tallyflash.data.TestVMixRepository
import com.jerubrin.tallyflash.data.VMixRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.components.SingletonComponent
import dagger.hilt.testing.TestInstallIn
import javax.inject.Singleton

@Module
@TestInstallIn(
    components = [SingletonComponent::class],
    replaces = [DataModule::class]
)
object TestDataModule {
    
    @Singleton
    @Provides
    fun provideVMixRepository(): VMixRepository =
        TestVMixRepository()
    
}