package com.pratclot.tunetracker.repository.di

import com.pratclot.tunetracker.datasource.ILocalDataSource
import com.pratclot.tunetracker.repository.IRepositoryTool
import com.pratclot.tunetracker.repository.ITuneRepository
import com.pratclot.tunetracker.repository.fakes.FakeRepositoryTool
import com.pratclot.tunetracker.repository.fakes.FakeTuneRepository
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class TestRepositoryModule {
    @Provides
    @Singleton
    fun provideRepositoryTool(): IRepositoryTool {
        return FakeRepositoryTool()
    }

    @Provides
    @Singleton
    fun provideTuneRepository(fakeLocalDatasource: ILocalDataSource): ITuneRepository {
        return FakeTuneRepository(
            fakeLocalDatasource
        )
    }
}
