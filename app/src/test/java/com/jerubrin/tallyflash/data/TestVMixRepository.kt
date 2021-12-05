package com.jerubrin.tallyflash.data

import com.jerubrin.tallyflash.entity.ConnectionData
import com.jerubrin.tallyflash.entity.Scene
import com.jerubrin.tallyflash.entity.WorkingScenes

class TestVMixRepository : VMixRepository {
    
    override suspend fun getScenesList(connectionData: ConnectionData): List<Scene>? {
        return SCENES_LIST
    }
    
    override suspend fun getWorkingScenes() =
        WorkingScenes(active = ACTIVE_SCENE, preview = PREVIEW_SCENE)
    
    companion object {
        const val ACTIVE_SCENE = 1
        const val PREVIEW_SCENE = 2
        
        private const val KEY = "fake_key_"
        private const val TYPE = "fake_type_"
        private const val TITLE = "fake_title_1"
        
        val SCENES_LIST: List<Scene> = listOf(
            Scene("${KEY}1", 1, "${TYPE}1", "${TITLE}1"),
            Scene("${KEY}2", 2, "${TYPE}1", "${TITLE}2"),
            Scene("${KEY}3", 3, "${TYPE}2", "${TITLE}3"),
            Scene("${KEY}4", 4, "${TYPE}2", "${TITLE}4"),
            Scene("${KEY}5", 5, "${TYPE}3", "${TITLE}5"),
        )
    }
}