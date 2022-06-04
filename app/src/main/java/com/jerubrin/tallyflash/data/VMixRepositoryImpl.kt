package com.jerubrin.tallyflash.data

import com.jerubrin.tallyflash.data.retrofit.MainOverlay
import com.jerubrin.tallyflash.entity.Scene
import com.jerubrin.tallyflash.entity.WorkingScenes
import com.jerubrin.tallyflash.data.retrofit.VMixApi
import com.jerubrin.tallyflash.entity.ConnectionData
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject
import javax.inject.Singleton


@Singleton
class VMixRepositoryImpl @Inject constructor(
    private val vMixApi: VMixApi
) : VMixRepository {
    
    override suspend fun getScenesList(connectionData: ConnectionData): List<Scene>? {
        withContext(Dispatchers.IO) {
            vMixApi.renewRetrofit(connectionData)
        }
        vMixApi.getData()?.inputs?.also { inputsList ->
            
            return inputsList.map { currentIn ->
                val overList = mutableListOf<Int>()
                inputsList.forEach { searchIn ->
                    if (searchIn.overlays.find { it.key == currentIn.key } != null ) {
                        overList.add(searchIn.number)
                    }
                }
                Scene(
                    currentIn.key,
                    currentIn.number,
                    currentIn.type,
                    currentIn.shortTitle,
                    overList
                )
            }
        }
        return listOf()
    }
    
    override suspend fun getWorkingScenes() =
        WorkingScenes( getActive(), getPreview(), getOverlays() )
    
    
    
    private suspend fun getPreview() =
        withContext(Dispatchers.IO) {
            vMixApi.getData()?.preview ?: -1
        }
    
    private suspend fun getActive() =
        withContext(Dispatchers.IO) {
            vMixApi.getData()?.active ?: -1
        }
    
    private suspend fun getOverlays() : List<MainOverlay> =
        withContext(Dispatchers.IO) {
            vMixApi.getData()?.overlay ?: listOf()
        }
    
}