package com.pratclot.tunetracker.datasource.di

import com.pratclot.tunetracker.datasource.ILocalDataSource
import com.pratclot.tunetracker.datasource.IRemoteDataSource
import com.pratclot.tunetracker.datasource.fakes.FakeLocalDatasource
import com.pratclot.tunetracker.datasource.fakes.FakeRemoteDatasource
import com.pratclot.tunetracker.domain.Tune
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class TestDataSourceModule {
    @Provides
    @Singleton
    fun getRemoteDataSource(): IRemoteDataSource {
        return FakeRemoteDatasource()
    }

    @Provides
    @Singleton
    fun getLocalDataSource(tunes: MutableList<Tune>): ILocalDataSource {
        return FakeLocalDatasource(
            tunes
        )
    }

    @Provides
    fun getFakeTunesList(): List<Tune> {
        val testTuneId = 1L
        val testTuneName = "testTune"
        val testTuneTabLocalUrl = "file:///"
        val testTune = Tune(testTuneId, testTuneName, testTuneTabLocalUrl)
        val tuneList = mutableListOf<Tune>()
        tuneList.add(testTune)
        return tuneList
    }
}
