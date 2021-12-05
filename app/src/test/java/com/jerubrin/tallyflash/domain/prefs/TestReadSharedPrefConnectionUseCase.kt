package com.jerubrin.tallyflash

import com.jerubrin.tallyflash.domain.usecase.prefs.BasePrefsUseCase
import com.jerubrin.tallyflash.entity.ConnectionData


class TestReadSharedPrefConnectionUseCase : BasePrefsUseCase<ConnectionData, Unit>() {

    override fun execute(params: Unit): ConnectionData =
        ConnectionData(ip = DEFAULT_IP, port = DEFAULT_PORT)

    companion object {
        const val DEFAULT_IP = "192.168.0.107"
        const val DEFAULT_PORT = "8088"
    }
}
