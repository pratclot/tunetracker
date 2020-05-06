package com.pratclot.tunetracker.datasource

import com.pratclot.tunetracker.domain.Tune
import okhttp3.ResponseBody

interface IRemoteDataSource {
    suspend fun downloadPdfFromRemote(tune: Tune): ResponseBody
}
