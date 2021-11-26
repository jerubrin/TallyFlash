package com.jerubrin.tallyflash.retrofit

import com.jerubrin.tallyflash.data.Scene
import com.jerubrin.tallyflash.data.WorkingScenes
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class VMixRepository @Inject constructor(
    private val vMixApi: VMixApi
) {
    
    suspend fun getScenesList(): List<Scene>? {
        withContext(Dispatchers.IO) {
            vMixApi.renewRetrofit()
        }
        return vMixApi.getData()?.inputs?.map {
            Scene(
                it.key,
                it.number,
                it.type,
                it.shortTitle
            )
        }
    }
    
    suspend fun getWorkingScenes() =
        WorkingScenes( getActive(), getPreview() )
    
    
    
    private suspend fun getPreview() =
        withContext(Dispatchers.IO) {
            vMixApi.getData()?.preview ?: -1
        }
    
    private suspend fun getActive() =
        withContext(Dispatchers.IO) {
            vMixApi.getData()?.active ?: -1
        }
}