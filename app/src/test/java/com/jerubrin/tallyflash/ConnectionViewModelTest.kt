package com.jerubrin.tallyflash

import android.os.Build
import androidx.activity.viewModels
import com.jerubrin.tallyflash.di.AppModule
import com.jerubrin.tallyflash.di.DataModule
import com.jerubrin.tallyflash.di.SharedPrefUseCaseModule
import com.jerubrin.tallyflash.domain.UiState
import com.jerubrin.tallyflash.entity.ConnectionData
import com.jerubrin.tallyflash.presentation.vm.ConnectionViewModel
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import dagger.hilt.android.testing.HiltTestApplication
import dagger.hilt.android.testing.UninstallModules
import junit.framework.TestCase.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.Robolectric
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config


@HiltAndroidTest
@Config(
    sdk = [Build.VERSION_CODES.Q],
    application = HiltTestApplication::class
)
@RunWith(RobolectricTestRunner::class)
@UninstallModules(AppModule::class, DataModule::class, SharedPrefUseCaseModule::class)
class ConnectionViewModelTest {
    
    @get:Rule
    var hiltRule = HiltAndroidRule(this)
    
    private lateinit var mainActivity: MainActivity
    private lateinit var viewModel: ConnectionViewModel
    
    @Before
    fun start() {
        hiltRule.inject()
        mainActivity = Robolectric.buildActivity(MainActivity::class.java).setup().start().get()
        viewModel = mainActivity.viewModels<ConnectionViewModel>().value
    }
    
    @Test
    fun checkGetConnectionData() {
        assertEquals(
            TestReadSharedPrefConnectionUseCase.DEFAULT_IP,
            viewModel.connectionDataState.data.ip
        )
        assertEquals(
            TestReadSharedPrefConnectionUseCase.DEFAULT_PORT,
            viewModel.connectionDataState.data.port
        )
    }
    
    @Test
    fun checkAndSaveConnectionData() {
        var uiState = UiState.Ready( ConnectionData("192.168.0.1", "8088") )
        viewModel.checkAndSaveConnectionData(uiState).also {
            assert(it is UiState.Ready<*>)
        }
    
        uiState = UiState.Ready( ConnectionData("192.168.1", "8088") )
        viewModel.checkAndSaveConnectionData(uiState).also {
            assert(it is UiState.Error)
        }
    
        uiState = UiState.Ready( ConnectionData("192.168.0.1.1", "8088") )
        viewModel.checkAndSaveConnectionData(uiState).also {
            assert(it is UiState.Error)
        }
    
        uiState = UiState.Ready( ConnectionData("192.168.0.1", "") )
        viewModel.checkAndSaveConnectionData(uiState).also {
            assert(it is UiState.Error)
        }
    
        uiState = UiState.Ready( ConnectionData("192.168.0.1", "---") )
        viewModel.checkAndSaveConnectionData(uiState).also {
            assert(it is UiState.Error)
        }
    }
}