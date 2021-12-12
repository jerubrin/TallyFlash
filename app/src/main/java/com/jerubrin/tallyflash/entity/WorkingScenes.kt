package com.jerubrin.tallyflash.entity

import com.jerubrin.tallyflash.data.retrofit.MainOverlay

data class WorkingScenes(
    val active: Int,
    val preview: Int,
    val overlays: List<MainOverlay>
)
