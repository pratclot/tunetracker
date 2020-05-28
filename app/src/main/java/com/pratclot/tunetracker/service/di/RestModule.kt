package com.pratclot.tunetracker.service.di

import com.pratclot.tunetracker.service.MyRetrofit
import dagger.Module
import dagger.Provides

private const val BASE_URL = "https://drive.google.com"

@Module
class RestModule {
//    @Inject
//    lateinit var retrofitFactory: MyRetrofit.Factory
//    @Provides
//    @Singleton
//    fun provideOkHttpClient(progressCallback: ProgressReportingHttpClient.DownloadProgressCallback): OkHttpClient {
//        return ProgressReportingHttpClient(progressCallback).provideClient()
//    }

//    @Provides
//    @Singleton
//    fun provideRestService(retrofit: Retrofit): IRestService {
////        val retrofit = Retrofit.Builder()
////            .client(client)
////            .baseUrl(BASE_URL)
////            .build()
////        val retrofit = retrofitFactory.create()
//        return retrofit.create(IRestService::class.java)
//    }

//    @Provides
//    fun provideRetrofitFactory(): MyRetrofit.Factory
}
