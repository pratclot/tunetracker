package com.pratclot.tunetracker.service

import com.pratclot.tunetracker.overview.ProgressReportingHttpClient
import com.squareup.inject.assisted.Assisted
import com.squareup.inject.assisted.AssistedInject
import retrofit2.Retrofit

private const val BASE_URL = "https://drive.google.com"

class MyRetrofit @AssistedInject constructor(
    @Assisted val progressCallback: ProgressReportingHttpClient.DownloadProgressCallback
) {
    @AssistedInject.Factory
    interface Factory {
        fun create(progressCallback: ProgressReportingHttpClient.DownloadProgressCallback): MyRetrofit
    }

    fun create(): Retrofit {
        val client = ProgressReportingHttpClient(progressCallback).provideClient()

        return Retrofit.Builder()
            .client(client)
            .baseUrl(BASE_URL)
            .build()
    }
}