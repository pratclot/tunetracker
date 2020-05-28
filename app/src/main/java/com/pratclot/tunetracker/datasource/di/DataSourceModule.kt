package com.pratclot.tunetracker.datasource.di

import com.pratclot.tunetracker.database.TuneDatabaseDao
import com.pratclot.tunetracker.datasource.ILocalDataSource
import com.pratclot.tunetracker.datasource.IRemoteDataSource
import com.pratclot.tunetracker.datasource.LocalDataSource
import com.pratclot.tunetracker.datasource.RemoteDataSource
import com.pratclot.tunetracker.service.IRestService
import com.pratclot.tunetracker.service.MyRetrofit
import dagger.Module
import dagger.Provides
import javax.inject.Singleton
import kotlinx.coroutines.CoroutineDispatcher

@Module
class DataSourceModule {
    @Provides
    @Singleton
    fun getLocalDataSource(tunesDao: TuneDatabaseDao): ILocalDataSource {
        return LocalDataSource(tunesDao)
    }

    @Provides
    @Singleton
    fun getRemoteDataSource(
        ioDispatcher: CoroutineDispatcher,
        myRetrofitServiceFactory: MyRetrofit.Factory
    ): IRemoteDataSource {
        return RemoteDataSource(ioDispatcher, myRetrofitServiceFactory)
    }
}
