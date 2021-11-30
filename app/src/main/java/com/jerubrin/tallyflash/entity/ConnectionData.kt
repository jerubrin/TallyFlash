package com.jerubrin.tallyflash.entity

import com.jerubrin.tallyflash.entity.SharedConnectConst.DEFAULT_IP_ADDRESS
import com.jerubrin.tallyflash.entity.SharedConnectConst.DEFAULT_PORT


data class ConnectionData(
    val ip: String = DEFAULT_IP_ADDRESS,
    val port: String = DEFAULT_PORT
)

object SharedConnectConst {
    const val SHARED_CONNECT = "com.jerubrin.tallyflash.shared.connect"
    
    const val SHARED_IP_ADDRESS = "com.jerubrin.tallyflash.shared.ipAddress"
    const val SHARED_PORT = "com.jerubrin.tallyflash.shared.port"
    
    const val DEFAULT_IP_ADDRESS = "192.168.0.101"
    const val DEFAULT_PORT = "8088"
}
