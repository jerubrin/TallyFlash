package com.jerubrin.tallyflash.data.retrofit

import retrofit2.Response
import retrofit2.http.GET

interface VMixLoader {
    @GET("/api")
    suspend fun getVMix(): Response<VMix>
}