package com.jerubrin.tallyflash.data


class TestResources : ResourcesInterface {
    
    override fun getString(id: Int): String =
        id.toString()
    
}