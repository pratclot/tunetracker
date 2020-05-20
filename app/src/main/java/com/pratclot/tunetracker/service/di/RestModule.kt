package com.pratclot.tunetracker.service.di

import com.pratclot.tunetracker.service.IRestService
import dagger.Module
import dagger.Provides
import javax.inject.Singleton
import okhttp3.OkHttpClient
import retrofit2.Retrofit

private const val BASE_URL = "https://drive.google.com"

@Module
class RestModule {
    @Provides
    @Singleton
    fun provideOkHttpClient(): OkHttpClient {
        return OkHttpClient.Builder()
            .build()
    }

    @Provides
    @Singleton
    fun provideRestService(client: OkHttpClient): IRestService {
        val retrofit = Retrofit.Builder()
            .client(client)
            .baseUrl(BASE_URL)
            .build()
        return retrofit.create(IRestService::class.java)
    }
}
