package com.pratclot.tunetracker.datasource

import androidx.lifecycle.LiveData
import com.pratclot.tunetracker.domain.Tune

interface ILocalDataSource {
    fun getAll(): LiveData<List<Tune>>
    fun insert(tune: Tune): Tune
    fun clear()
    fun get(name: String): Tune?
    fun getById(id: Long): Tune?
    fun update(tune: Tune)
    fun updateFilePath(tune: Tune)
}
