package com.pratclot.tunetracker.datasource.fakes

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.pratclot.tunetracker.datasource.ILocalDataSource
import com.pratclot.tunetracker.domain.Tune

class FakeLocalDatasource(var tunes: MutableList<Tune> = mutableListOf()) :
    ILocalDataSource {
    override fun getAll(): LiveData<List<Tune>> {
        return MutableLiveData(tunes)
    }

    override fun insert(tune: Tune): Tune {
        val nextId = tunes.size.toLong()
        tunes.add(Tune.from(tune, nextId))
        return get(tune.name)!!
    }

    override fun updateFilePath(tune: Tune) {
        tunes.map {
            if (it.id == tune.id) {
                it.tabLocalUrl = tune.tabLocalUrl
            }
        }
    }

    override fun clear() {
        tunes.clear()
    }

    override fun get(name: String): Tune? {
        tunes.forEach {
            if (it.name == name) {
                return it
            }
        }
        return null
    }

    override fun getById(id: Long): Tune? {
        tunes.forEach {
            if (it.id == id) {
                return it
            }
        }
        return null
    }

    override fun update(tune: Tune) {
        tunes.map {
            if (it.id == tune.id) {
                it.downloadComplete = tune.downloadComplete
                it.progress = tune.progress
                it.name = tune.name
                it.tabWebUrl = tune.tabWebUrl
                it.tabLocalUrl = tune.tabLocalUrl
            }
        }
    }
}
