package com.pratclot.tunetracker.service

import com.pratclot.tunetracker.overview.ProgressReportingHttpClient.Companion.DOWNLOAD_IDENTIFIER_HEADER
import okhttp3.ResponseBody
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Url

interface IRestService {
    @GET
    suspend fun downloadTabInPdf(@Url url: String, @Header(DOWNLOAD_IDENTIFIER_HEADER) downloadIdentifier: Long): ResponseBody
}
