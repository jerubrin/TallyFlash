package com.jerubrin.tallyflash.data.retrofit

import org.simpleframework.xml.Attribute
import org.simpleframework.xml.Element
import org.simpleframework.xml.ElementList
import org.simpleframework.xml.Root
import org.simpleframework.xml.Text

@Root(strict = false, name = "vmix")
data class VMix @JvmOverloads constructor(
    @field:ElementList(name = "inputs")
    var inputs: MutableList<Input> = mutableListOf(),
    
    @field:ElementList(name = "overlays")
    var overlay: MutableList<MainOverlay> = mutableListOf(),
    
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
    var shortTitle: String = "",

    @field:ElementList(inline = true, entry = "overlay", required = false)
    var overlays: MutableList<Overlay> = mutableListOf()
)

@Root(strict = false, name = "overlay")
data class MainOverlay(
    @field:Text(required = false)
    var value: Int = -1,
    
    @field:Attribute(required = false, name = "preview")
    var isPreview: Boolean = false
)

@Root(strict = false, name = "overlay")
data class Overlay(
    @field:Attribute(name = "index")
    var index: Int = -1,
    
    @field:Attribute(name = "key")
    var key: String = ""
)