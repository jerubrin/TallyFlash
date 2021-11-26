package com.jerubrin.tallyflash.retrofit

import org.simpleframework.xml.Attribute
import org.simpleframework.xml.Element
import org.simpleframework.xml.ElementList
import org.simpleframework.xml.Root

@Root(strict = false, name = "vmix")
data class VMix @JvmOverloads constructor(
    @field:ElementList(name = "inputs")
    var inputs: MutableList<Input> = mutableListOf(),
    
    @field:Element(name = "preview")
    var preview: Int = -1,

    @field:Element(name = "active")
    var active: Int = -1
)

@Root(strict = false, name = "input")
data class Input(
    @field:Attribute(name = "key")
    var key: String = "",

    @field:Attribute(name = "number")
    var number: Int = -1,

    @field:Attribute(name = "type")
    var type: String = "",

    @field:Attribute(name = "shortTitle")
    var shortTitle: String = ""
)