package com.pratclot.tunetracker.repository.di

import android.content.Context
import com.pratclot.tunetracker.datasource.IRemoteDataSource
import com.pratclot.tunetracker.repository.IRepositoryTool
import com.pratclot.tunetracker.repository.RepositoryTool
import dagger.Module
import dagger.Provides
import kotlinx.coroutines.CoroutineDispatcher
import javax.inject.Singleton

@Module
class RepositoryModule {
    @Provides
    @Singleton
    fun provideRepositoryTool(
        remoteDataSource: IRemoteDataSource,
        application: Context,
        ioDispatcher: CoroutineDispatcher
    ): IRepositoryTool {
        return RepositoryTool(remoteDataSource, application, ioDispatcher)
    }
}