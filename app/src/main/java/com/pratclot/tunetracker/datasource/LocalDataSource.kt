package com.pratclot.tunetracker.datasource

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import com.pratclot.tunetracker.database.TuneDatabaseDao
import com.pratclot.tunetracker.database.asDomainModel
import com.pratclot.tunetracker.domain.Tune
import javax.inject.Inject

class LocalDataSource @Inject constructor(
    private val tunesDao: TuneDatabaseDao
) : ILocalDataSource {

    override fun getAll(): LiveData<List<Tune>> {
        return Transformations.map(tunesDao.getAll()) {
            it.asDomainModel()
        }
    }

    override fun insert(tune: Tune): Tune {
        tunesDao.insert(tune.asDatabaseModel())
        return get(tune.name)!!
    }

    override fun clear() {
        tunesDao.clear()
    }

    override fun get(name: String): Tune? {
        return tunesDao.get(name)?.asDomainModel()
    }

    override fun getById(id: Long): Tune? {
        return tunesDao.getById(id).asDomainModel()
    }

    override fun update(tune: Tune) {
        tunesDao.update(tune.asDatabaseModel())
    }

    override fun updateFilePath(tune: Tune) {
        tunesDao.updateFilePath(tune.asDatabaseFilePath())
    }
}
