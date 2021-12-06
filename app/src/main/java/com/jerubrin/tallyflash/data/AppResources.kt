package com.jerubrin.tallyflash.data

import android.content.Context
import javax.inject.Inject

class AppResources @Inject constructor(
    context: Context
) : ResourcesInterface {
    private val resources = context.resources
    
    override fun getString(id: Int): String {
        return resources.getString(id)
    }
}

interface ResourcesInterface {
    fun getString(id: Int): String
}