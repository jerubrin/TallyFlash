package com.jerubrin.tallyflash.data

import com.jerubrin.tallyflash.entity.ConnectionData
import com.jerubrin.tallyflash.entity.Scene
import com.jerubrin.tallyflash.entity.WorkingScenes

interface VMixRepository {
    
    suspend fun getScenesList(connectionData: ConnectionData): List<Scene>?
    
    suspend fun getWorkingScenes(): WorkingScenes
}