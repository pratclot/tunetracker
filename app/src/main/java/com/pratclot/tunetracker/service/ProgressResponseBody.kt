package com.pratclot.tunetracker.service

import com.pratclot.tunetracker.overview.ProgressReportingHttpClient.Companion.DOWNLOAD_IDENTIFIER_HEADER
import okhttp3.MediaType
import okhttp3.Response
import okhttp3.ResponseBody
import okio.Buffer
import okio.BufferedSource
import okio.ForwardingSource
import okio.Okio
import okio.Source

class ProgressResponseBody(
    private val originalResponse: Response,
    val progressListener: ProgressListener
) : ResponseBody() {
    override fun contentLength(): Long {
        return originalResponse.body()?.contentLength()!!
    }

    override fun contentType(): MediaType? {
        return originalResponse.body()?.contentType()
    }

    override fun source(): BufferedSource {
        return Okio.buffer(source(originalResponse.body()?.source()!!))
    }

    private fun source(source: BufferedSource): Source {
        return object : ForwardingSource(source) {
            var totalBytesRead = 0L

            override fun read(sink: Buffer, byteCount: Long): Long {
                val bytesRead = super.read(sink, byteCount)

                totalBytesRead += if (bytesRead != -1L) bytesRead else 0
                progressListener.update(
                    totalBytesRead,
                    originalResponse.body()?.contentLength()!!,
                    bytesRead == -1L,
                    originalResponse.request().header(DOWNLOAD_IDENTIFIER_HEADER)!!.toLong()
                )
                return bytesRead
            }
        }
    }

    interface ProgressListener {
        fun update(bytesRead: Long, contentLength: Long, done: Boolean, downloadIdentifier: Long): String
    }
}
