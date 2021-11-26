package com.jerubrin.tallyflash.data

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Scene (
    val key: String,
    val number: Int,
    val type: String,
    val shortTitle: String
) : Parcelable