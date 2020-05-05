package com.pratclot.tunetracker.repository

import androidx.lifecycle.LiveData
import com.pratclot.tunetracker.datasource.ILocalDataSource
import com.pratclot.tunetracker.domain.Tune
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import javax.inject.Inject


class TuneRepository @Inject constructor(
    private val datasource: ILocalDataSource,
    private val repositoryTool: IRepositoryTool,
    private var ioDispatcher: CoroutineDispatcher
) : ITuneRepository {

    override val tunes: LiveData<List<Tune>> = datasource.getAll()

    override suspend fun insert(tune: Tune) {
        val filename = repositoryTool.getLocalPathTo(tune)
        tune.tabLocalUrl = filename

        withContext(ioDispatcher) {
            datasource.insert(tune)
        }
    }

    override suspend fun clear() {
        withContext(ioDispatcher) {
            datasource.clear()
        }
    }

    override suspend fun getTuneByName(name: String): String? {
        return withContext(ioDispatcher) {
            return@withContext datasource.get(name)?.tabLocalUrl
        }
    }

    override suspend fun getTuneById(id: Long): Tune? {
        return withContext(ioDispatcher) {
            return@withContext datasource.getById(id)!!
        }
    }
}
