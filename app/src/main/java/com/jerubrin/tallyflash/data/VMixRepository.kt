package com.jerubrin.tallyflash.data

import com.jerubrin.tallyflash.entity.Scene
import com.jerubrin.tallyflash.entity.WorkingScenes
import com.jerubrin.tallyflash.data.retrofit.VMixApi
import com.jerubrin.tallyflash.entity.ConnectionData
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class VMixRepository @Inject constructor(
    private val vMixApi: VMixApi
) {
    
    suspend fun getScenesList(connectionData: ConnectionData): List<Scene>? {
        withContext(Dispatchers.IO) {
            vMixApi.renewRetrofit(connectionData)
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