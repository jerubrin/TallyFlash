package com.jerubrin.tallyflash

import android.os.Build
import androidx.activity.viewModels
import app.cash.turbine.test
import com.jerubrin.tallyflash.data.TestVMixRepository
import com.jerubrin.tallyflash.di.AppModule
import com.jerubrin.tallyflash.di.DataModule
import com.jerubrin.tallyflash.di.SharedPrefUseCaseModule
import com.jerubrin.tallyflash.domain.UiState
import com.jerubrin.tallyflash.entity.Scene
import com.jerubrin.tallyflash.presentation.service.SceneStateServiceControl
import com.jerubrin.tallyflash.presentation.vm.ScenesListViewModel
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import dagger.hilt.android.testing.HiltTestApplication
import dagger.hilt.android.testing.UninstallModules
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.Robolectric.buildActivity
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config
import javax.inject.Inject


@HiltAndroidTest
@Config(
    sdk = [Build.VERSION_CODES.Q],
    application = HiltTestApplication::class
)
@RunWith(RobolectricTestRunner::class)
@UninstallModules(AppModule::class, DataModule::class, SharedPrefUseCaseModule::class)
class ScenesListViewModelTest {
    
    @get:Rule
    var hiltRule = HiltAndroidRule(this)
    
    private lateinit var mainActivity: MainActivity
    private lateinit var viewModel: ScenesListViewModel
    
    @Inject
    lateinit var sceneStateServiceControl: SceneStateServiceControl

    @Before
    fun start() {
        hiltRule.inject()
        mainActivity = buildActivity(MainActivity::class.java).setup().start().get()
        viewModel = mainActivity.viewModels<ScenesListViewModel>().value
    }
    
    
    @ExperimentalCoroutinesApi
    @Test
    fun testLoadSceneList() {
        runBlockingTest {
            viewModel.loadSceneList().test {
                val state = awaitItem()
                if (state is UiState.Ready<*> && state.data is List<*>) {
                    val ready = (state as UiState.Ready<List<Scene>>).data
                    Assert.assertEquals(ready, TestVMixRepository.SCENES_LIST)
                }
                cancelAndIgnoreRemainingEvents()
            }
        }
    }
    
    @Test
    fun testGetConnectionData() {
        viewModel.connectionDataState.data.also { connectionData ->
            Assert.assertEquals(
                connectionData.ip,
                TestReadSharedPrefConnectionUseCase.DEFAULT_IP
            )
            Assert.assertEquals(
                connectionData.port,
                TestReadSharedPrefConnectionUseCase.DEFAULT_PORT
            )
        }
    }
    
    @Test
    fun testResetService() {
        val notDefaultSceneState =
            UiState.Ready(
                Scene(key = "1", number = 1, type = "1", shortTitle = "notDefScene")
            )
    
        sceneStateServiceControl.setCurrentScene(notDefaultSceneState)
        viewModel.resetService()
        
        Assert.assertEquals(
            UiState.Ready( Scene() ), //Default scene
            sceneStateServiceControl.getCurrentScene()
        )
    }
    
}