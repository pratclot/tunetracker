package com.pratclot.tunetracker.repository.fakes

import androidx.lifecycle.MutableLiveData
import com.pratclot.tunetracker.datasource.ILocalDataSource
import com.pratclot.tunetracker.domain.Tune
import com.pratclot.tunetracker.repository.ITuneRepository
import javax.inject.Inject

class FakeTuneRepository @Inject constructor(
    private var fakeLocalDatasource: ILocalDataSource
) : ITuneRepository {
    override val tunes: MutableLiveData<List<Tune>>
        get() = fakeLocalDatasource.getAll() as MutableLiveData<List<Tune>>

    override suspend fun insert(tune: Tune) {
        fakeLocalDatasource.insert(tune)
    }

    override suspend fun clear() {
        fakeLocalDatasource.clear()
    }

    override suspend fun getTuneByName(name: String): String? {
        return fakeLocalDatasource.get(name)?.tabWebUrl
    }

    override suspend fun getTuneById(id: Long): Tune? {
        return fakeLocalDatasource.getById(id)
    }
}
