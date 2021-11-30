package com.jerubrin.tallyflash.data.retrofit

import com.jerubrin.tallyflash.entity.ConnectionData
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.simplexml.SimpleXmlConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton


@Singleton
class VMixApi  {
    
    private var retrofit : Retrofit? =
        newRetrofit(null)
    
    private var retrofitService: VMixLoader? =
        null
    
    suspend fun getData(): VMix? =
        retrofitService?.getVMix()?.body()
    
    fun renewRetrofit(connectionData: ConnectionData) {
        retrofit = newRetrofit(connectionData)
        retrofitService = setRetrofitService()
    }
    
    private fun newRetrofit(connectionData: ConnectionData?): Retrofit? =
        if (connectionData != null) {
            Retrofit.Builder()
                .addConverterFactory(SimpleXmlConverterFactory.create())
                .baseUrl(
                    "http://${connectionData.ip}:${connectionData.port}"
                )
                .client(setTimeoutOkHttpClient())
                .build()
        } else {
            null
        }
    
    private fun setRetrofitService(): VMixLoader? =
        retrofit?.create(VMixLoader::class.java)
    
    
    private fun setTimeoutOkHttpClient() = OkHttpClient()
        .newBuilder()
        .connectTimeout(DEFAULT_TIMEOUT, TimeUnit.MILLISECONDS)
        .readTimeout(DEFAULT_TIMEOUT, TimeUnit.MILLISECONDS)
        .writeTimeout(DEFAULT_TIMEOUT, TimeUnit.MILLISECONDS)
        .build()
    
    companion object {
        const val DEFAULT_TIMEOUT = 500L
    }
}