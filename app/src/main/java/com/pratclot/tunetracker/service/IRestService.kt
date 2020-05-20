package com.pratclot.tunetracker.service

import okhttp3.ResponseBody
import retrofit2.http.GET
import retrofit2.http.Url

interface IRestService {
    @GET
    suspend fun downloadTabInPdf(@Url url: String): ResponseBody
}
