package com.pratclot.tunetracker.datasource

import com.pratclot.tunetracker.domain.Tune
import com.pratclot.tunetracker.overview.ProgressReportingHttpClient
import com.pratclot.tunetracker.service.IRestService
import com.pratclot.tunetracker.service.MyRetrofit
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import okhttp3.ResponseBody
import javax.inject.Inject

class RemoteDataSource @Inject constructor(
    private val ioDispatcher: CoroutineDispatcher,
    var myRetrofitServiceFactory: MyRetrofit.Factory
) : IRemoteDataSource {

    private lateinit var retrofitService: IRestService

    override suspend fun downloadPdfFromRemote(tune: Tune, progressCallback: ProgressReportingHttpClient.DownloadProgressCallback): ResponseBody {
        retrofitService = myRetrofitServiceFactory.create(progressCallback).create().create(IRestService::class.java)
        return withContext(ioDispatcher) {
            return@withContext retrofitService.downloadTabInPdf(tune.tabWebUrl, tune.id!!)
        }
    }
}
