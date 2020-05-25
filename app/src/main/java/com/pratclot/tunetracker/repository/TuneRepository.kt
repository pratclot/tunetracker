package com.pratclot.tunetracker.repository

import androidx.lifecycle.LiveData
import com.pratclot.tunetracker.datasource.ILocalDataSource
import com.pratclot.tunetracker.domain.Tune
import javax.inject.Inject
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext

class TuneRepository @Inject constructor(
    private val datasource: ILocalDataSource,
    private val repositoryTool: IRepositoryTool,
    private var ioDispatcher: CoroutineDispatcher
) : ITuneRepository {

    override val tunes: LiveData<List<Tune>> = datasource.getAll()

    override suspend fun insert(tune: Tune) {
        withContext(ioDispatcher) {
            datasource.insert(tune)
        }.also {
            repositoryTool.getLocalPathTo(it).let { filename ->
                it.tabLocalUrl = filename
            }
            updateFilePath(it)
        }
    }

    override suspend fun reload(id: Long) {
        getTuneById(id)?.let {
            it.downloadComplete = false
            update(it)
            repositoryTool.getLocalPathTo(it, true).let { filename ->
                it.tabLocalUrl = filename
            }
            updateFilePath(it)
        }
    }

    override suspend fun clear() {
        withContext(ioDispatcher) {
            datasource.clear()
        }
    }

    override suspend fun getTuneByName(name: String): Tune? {
        return withContext(ioDispatcher) {
            return@withContext datasource.get(name)
        }
    }

    override suspend fun getTuneById(id: Long): Tune? {
        return withContext(ioDispatcher) {
            return@withContext datasource.getById(id)!!
        }
    }

    override suspend fun update(tune: Tune) {
        return withContext(ioDispatcher) {
            return@withContext datasource.update(tune)
        }
    }

    override suspend fun updateFilePath(tune: Tune) {
        return withContext(ioDispatcher) {
            return@withContext datasource.updateFilePath(tune)
        }
    }
}
