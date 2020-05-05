package com.pratclot.tunetracker.service

import kotlinx.coroutines.Deferred
import okhttp3.ResponseBody
import retrofit2.http.GET
import retrofit2.http.Streaming
import retrofit2.http.Url

interface IRestService {
    @Streaming
    @GET
    fun downloadTabInPdf(@Url url: String): Deferred<ResponseBody>
}
