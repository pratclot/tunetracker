package com.pratclot.tunetracker.datasource.di

import com.pratclot.tunetracker.database.TuneDatabaseDao
import com.pratclot.tunetracker.datasource.ILocalDataSource
import com.pratclot.tunetracker.datasource.IRemoteDataSource
import com.pratclot.tunetracker.datasource.LocalDataSource
import com.pratclot.tunetracker.datasource.RemoteDataSource
import com.pratclot.tunetracker.service.IRestService
import dagger.Module
import dagger.Provides
import kotlinx.coroutines.CoroutineDispatcher
import javax.inject.Singleton

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
        retrofitService: IRestService
    ): IRemoteDataSource {
        return RemoteDataSource(ioDispatcher, retrofitService)
    }
}