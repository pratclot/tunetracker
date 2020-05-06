package com.pratclot.tunetracker.datasource

import com.pratclot.tunetracker.domain.Tune
import com.pratclot.tunetracker.service.IRestService
import javax.inject.Inject
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import okhttp3.ResponseBody

class RemoteDataSource @Inject constructor(
    private val ioDispatcher: CoroutineDispatcher,
    private var retrofitService: IRestService
) : IRemoteDataSource {
    override suspend fun downloadPdfFromRemote(tune: Tune): ResponseBody {
        return withContext(ioDispatcher) {
            return@withContext retrofitService.downloadTabInPdf(tune.tabWebUrl).await()
        }
    }
}
