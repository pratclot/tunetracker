package com.pratclot.tunetracker.overview

import com.pratclot.tunetracker.overview.OverviewViewModel.Companion.updateTuneProgresses
import com.pratclot.tunetracker.service.ProgressResponseBody
import okhttp3.OkHttpClient
import okhttp3.Response
import timber.log.Timber

class ProgressReportingHttpClient {
    companion object {
        const val DOWNLOAD_IDENTIFIER_HEADER = "download-identifier"

        fun provideClient(): OkHttpClient {
            val progressListener = object : ProgressResponseBody.ProgressListener {
                var firstUpdate = true

                override fun update(
                    bytesRead: Long,
                    contentLength: Long,
                    done: Boolean,
                    downloadIdentifier: Long
                ): String {
                    if (done) {
                        return "Completed"
                    } else {
                        if (firstUpdate) {
                            firstUpdate = false
                            if (contentLength == -1L) {
                                Timber.i("Could not determine content-length")
                            } else {
                                Timber.i("content-length: $contentLength")
                            }
                        }

                        Timber.i(bytesRead.toString())
                        val progress = ((100 * bytesRead) / contentLength).toInt()
                        updateTuneProgresses(downloadIdentifier, progress)

                        return ((100 * bytesRead) / contentLength).toString()
                    }
                }
            }

            return OkHttpClient.Builder()
                .addNetworkInterceptor() {
                    val originalResponse: Response = it.proceed(it.request())
                    return@addNetworkInterceptor originalResponse.newBuilder()
                        .body(ProgressResponseBody(originalResponse, progressListener))
                        .build()
                }
                .build()
        }
    }
}
