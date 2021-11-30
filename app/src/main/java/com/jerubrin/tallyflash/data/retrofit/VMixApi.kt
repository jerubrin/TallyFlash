package com.jerubrin.tallyflash.data.retrofit

import com.jerubrin.tallyflash.domain.usecase.ReadSharedPrefConnectionUseCase
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.simplexml.SimpleXmlConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Inject
import javax.inject.Singleton


@Singleton
class VMixApi @Inject constructor(
    private val readSharedPrefConnectionUseCase: ReadSharedPrefConnectionUseCase
) {
    private var retrofit : Retrofit =
        newRetrofit()
    
    private var retrofitService =
        setRetrofitService()
    
    suspend fun getData(): VMix? =
        retrofitService.getVMix().body()
    
    fun renewRetrofit() {
        retrofit = newRetrofit()
        retrofitService = setRetrofitService()
    }
    
    private fun newRetrofit(): Retrofit {
        val connectionData = readSharedPrefConnectionUseCase.execute()
        return Retrofit.Builder()
            .addConverterFactory(SimpleXmlConverterFactory.create())
            .baseUrl(
                "http://${connectionData.ip}:${connectionData.port}"
            )
            .client(setTimeoutOkHttpClient(500L))
            .build()
    }
    
    private fun setRetrofitService() =
        retrofit.create(VMixLoader::class.java)
    
    
    private fun setTimeoutOkHttpClient(millis: Long) = OkHttpClient()
        .newBuilder()
        .connectTimeout(millis, TimeUnit.MILLISECONDS)
        .readTimeout(millis, TimeUnit.MILLISECONDS)
        .writeTimeout(millis, TimeUnit.MILLISECONDS)
        .build()
}