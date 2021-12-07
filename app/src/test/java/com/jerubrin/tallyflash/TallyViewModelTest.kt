package com.jerubrin.tallyflash

import android.os.Build
import androidx.activity.viewModels
import androidx.test.internal.events.client.TestEventServiceConnection
import app.cash.turbine.test
import com.jerubrin.tallyflash.di.AppModule
import com.jerubrin.tallyflash.di.DataModule
import com.jerubrin.tallyflash.di.SharedPrefUseCaseModule
import com.jerubrin.tallyflash.domain.UiState
import com.jerubrin.tallyflash.domain.prefs.TestReadSharedPrefMainUseCase
import com.jerubrin.tallyflash.domain.usecase.prefs.BasePrefsUseCase
import com.jerubrin.tallyflash.entity.Scene
import com.jerubrin.tallyflash.entity.SettingsData
import com.jerubrin.tallyflash.presentation.service.SceneStateServiceControl
import com.jerubrin.tallyflash.presentation.service.TestSceneStateServiceControl
import com.jerubrin.tallyflash.presentation.vm.TallyViewModel
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import dagger.hilt.android.testing.HiltTestApplication
import dagger.hilt.android.testing.UninstallModules
import junit.framework.TestCase
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.Robolectric
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
class TallyViewModelTest : TestCase() {
    
    @get:Rule(order = 0)
    var hiltRule = HiltAndroidRule(this)
    
    private lateinit var mainActivity: MainActivity
    private lateinit var viewModel: TallyViewModel
    
    @Before
    fun start() {
        hiltRule.inject()
        mainActivity = Robolectric.buildActivity(MainActivity::class.java).setup().start().get()
        viewModel = mainActivity.viewModels<TallyViewModel>().value
    }
    
    @ExperimentalCoroutinesApi
    @Test
    fun testGetSceneState() {
        runBlockingTest {
            viewModel.sceneState.test {
                Assert.assertEquals(
                    TestSceneStateServiceControl.DEFAULT_SCENE_STATE,
                    awaitItem()
                )
                cancelAndIgnoreRemainingEvents()
            }
        }
    }
    
    @Test
    fun testSetNewScene() {
        val scene = Scene(key = "9", number = 9, type = "newScene", shortTitle = "NewScene")
        viewModel.setNewScene( UiState.Ready(scene) )
    
        val field = viewModel.javaClass.getDeclaredField("_currentScene")
        field.isAccessible = true
        val resScene = field.get(viewModel) as Scene
    
        Assert.assertEquals(scene, resScene)
    }
    
    @Test
    fun testGetGetSettingsData() {
        val settingsData = viewModel.getSettingsData.data
        TestReadSharedPrefMainUseCase.apply {
            Assert.assertEquals(ACTIVE_COLOR, settingsData.activeColor)
            Assert.assertEquals(ACTIVE_FLASH, settingsData.activeFlash)
            Assert.assertEquals(ACTIVE_TEXT, settingsData.activeText)
            Assert.assertEquals(PREVIEW_COLOR, settingsData.previewColor)
            Assert.assertEquals(PREVIEW_FLASH, settingsData.previewFlash)
            Assert.assertEquals(PREVIEW_TEXT, settingsData.previewText)
            Assert.assertEquals(OFF_COLOR, settingsData.offColor)
            Assert.assertEquals(OFF_TEXT, settingsData.offText)
            Assert.assertEquals(BLINK_AFTER_ACTIVE, settingsData.blinkAfterActive)
        }
    }
    
    @Test
    fun checkCurrentSceneUiState() {
        val sceneUiState = UiState.Ready(
            Scene(key = "8", number = 8, type = "newScene", shortTitle = "NewScene")
        )
        
        viewModel.setNewScene(sceneUiState)
    
        Assert.assertEquals(viewModel.currentSceneUiState, sceneUiState)
    }
}