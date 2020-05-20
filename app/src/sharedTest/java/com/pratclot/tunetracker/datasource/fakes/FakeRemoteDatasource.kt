package com.pratclot.tunetracker.datasource.fakes

import com.pratclot.tunetracker.datasource.IRemoteDataSource
import com.pratclot.tunetracker.domain.Tune
import okhttp3.ResponseBody

class FakeRemoteDatasource : IRemoteDataSource {
    override suspend fun downloadPdfFromRemote(tune: Tune): ResponseBody {
        return ResponseBody.create(null, "")
    }
}
