package com.pratclot.tunetracker.repository

import androidx.lifecycle.LiveData
import com.pratclot.tunetracker.domain.Tune
import com.pratclot.tunetracker.overview.ProgressReportingHttpClient

interface ITuneRepository {
    val tunes: LiveData<List<Tune>>

    suspend fun insert(tune: Tune, progressCallback: ProgressReportingHttpClient.DownloadProgressCallback)

    suspend fun clear()

    suspend fun getTuneByName(name: String): Tune?

    suspend fun getTuneById(id: Long): Tune?

    suspend fun update(tune: Tune)
    suspend fun reload(id: Long, progressCallback: ProgressReportingHttpClient.DownloadProgressCallback)
    suspend fun updateFilePath(tune: Tune)
}
