package com.pratclot.tunetracker.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import com.pratclot.tunetracker.database.TuneDatabase
import com.pratclot.tunetracker.database.asDomainModel
import com.pratclot.tunetracker.domain.Tune
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class TuneRepository(private val database: TuneDatabase) {
    val tunes: LiveData<List<Tune>> =
        Transformations.map(database.tuneDatabaseDao.getAll()) {
            it.asDomainModel()
        }

    suspend fun insert(tune: Tune) {
        withContext(Dispatchers.IO) {
            database.tuneDatabaseDao.insert(tune.asDatabaseModel())
        }
    }

    suspend fun clear() {
        withContext(Dispatchers.IO) {
            database.tuneDatabaseDao.clear()
        }
    }
}
