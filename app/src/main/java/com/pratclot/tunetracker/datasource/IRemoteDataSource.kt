package com.pratclot.tunetracker.datasource

import com.pratclot.tunetracker.domain.Tune
import com.pratclot.tunetracker.overview.ProgressReportingHttpClient
import okhttp3.ResponseBody

interface IRemoteDataSource {
    suspend fun downloadPdfFromRemote(tune: Tune, progressCallback: ProgressReportingHttpClient.DownloadProgressCallback): ResponseBody
}
