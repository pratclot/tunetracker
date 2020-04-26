package com.pratclot.tunetracker.repository

import android.net.Uri
import androidx.lifecycle.LiveData
import com.pratclot.tunetracker.domain.Tune

interface ITuneRepository {
    val tunes: LiveData<List<Tune>>

    suspend fun insert(tune: Tune)

    suspend fun clear()

    suspend fun downloadTabInPdf(tune: Tune): String
    fun getTuneByName(name: String): Uri

    suspend fun getTuneById(id: Long): Tune?
}