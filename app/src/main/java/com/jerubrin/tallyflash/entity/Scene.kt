package com.jerubrin.tallyflash.entity

import android.os.Parcelable
import kotlinx.parcelize.Parcelize


@Parcelize
data class Scene (
    val key: String = "",
    val number: Int = -1,
    val type: String = "",
    val shortTitle: String = "",
    val overlayList: List<Int> = listOf()
) : Parcelable