package com.jerubrin.tallyflash.di

import com.jerubrin.tallyflash.data.VMixRepository
import com.jerubrin.tallyflash.domain.UiState
import com.jerubrin.tallyflash.domain.usecase.BaseUseCase
import com.jerubrin.tallyflash.domain.usecase.SceneListUseCase
import com.jerubrin.tallyflash.domain.usecase.WorkingScenesUseCase
import com.jerubrin.tallyflash.entity.ConnectionData
import com.jerubrin.tallyflash.entity.Scene
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object UseCaseModule {
    
    @Provides
    fun providesSceneListAsyncUseCase(
        vMixRepository: VMixRepository
    ): BaseUseCase<UiState, ConnectionData> =
        SceneListUseCase(vMixRepository)
    
    @Provides
    fun providesWorkingScenesAsyncUseCase(
        vMixRepository: VMixRepository
    ): BaseUseCase<UiState, Scene> =
        WorkingScenesUseCase(vMixRepository)
}