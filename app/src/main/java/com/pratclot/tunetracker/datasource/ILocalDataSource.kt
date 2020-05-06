package com.pratclot.tunetracker.datasource

import androidx.lifecycle.LiveData
import com.pratclot.tunetracker.domain.Tune

interface ILocalDataSource {
    fun getAll(): LiveData<List<Tune>>
    fun insert(tune: Tune)
    fun clear()
    fun get(name: String): Tune?
    fun getById(id: Long): Tune?
}
