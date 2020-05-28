package com.pratclot.tunetracker.repository

import com.pratclot.tunetracker.domain.Tune
import com.pratclot.tunetracker.overview.ProgressReportingHttpClient

interface IRepositoryTool {
    suspend fun getLocalPathTo(tune: Tune, withDelete: Boolean = false, progressCallback: ProgressReportingHttpClient.DownloadProgressCallback): String
}
