package com.pratclot.tunetracker.repository

import androidx.lifecycle.LiveData
import com.pratclot.tunetracker.domain.Tune

interface ITuneRepository {
    val tunes: LiveData<List<Tune>>

    suspend fun insert(tune: Tune)

    suspend fun clear()

    suspend fun getTuneByName(name: String): String?

    suspend fun getTuneById(id: Long): Tune?
}
