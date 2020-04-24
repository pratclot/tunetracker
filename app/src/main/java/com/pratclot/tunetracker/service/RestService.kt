package com.pratclot.tunetracker.service

import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import kotlinx.coroutines.Deferred
import okhttp3.ResponseBody
import retrofit2.Retrofit
import retrofit2.http.GET
import retrofit2.http.Streaming
import retrofit2.http.Url

private const val BASE_URL = "https://drive.google.com"

interface TuneRestService {
    @Streaming
    @GET
    fun downloadTabInPdf(@Url url: String): Deferred<ResponseBody>
}

private val retrofit = Retrofit.Builder()
    .addCallAdapterFactory(CoroutineCallAdapterFactory())
    .baseUrl(BASE_URL)
    .build()

object TuneRest {
    val retrofitService: TuneRestService by lazy {
        retrofit.create(TuneRestService::class.java)
    }
}
