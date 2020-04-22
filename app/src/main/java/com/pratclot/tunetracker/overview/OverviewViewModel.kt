package com.pratclot.tunetracker.overview

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.pratclot.tunetracker.database.Tune
import com.pratclot.tunetracker.database.TuneDatabaseDao
import kotlinx.coroutines.*

class OverviewViewModel(val database: TuneDatabaseDao, application: Application) :
    AndroidViewModel(application) {
    private val viewModelJob = Job()
    private val uiScope = CoroutineScope(Dispatchers.Main + viewModelJob)

    val tunes = database.getAll()

    var tuneNameInputText: String = ""
        set(value) {
            field = value
        }

    init {

    }

    private suspend fun insert(tune: Tune) {
        withContext(Dispatchers.IO) {
            database.insert(tune)
        }
    }

    private suspend fun clear() {
        withContext(Dispatchers.IO) {
            database.clear()
        }
    }

    fun onAddTune() {
        uiScope.launch {
            if (tuneNameInputText != null) {
                val newTune = Tune(tuneName = tuneNameInputText!!)
                insert(newTune)
            }
        }
    }

    fun onClear() {
        uiScope.launch {
            clear()
        }
    }

    override fun onCleared() {
        viewModelJob.cancel()
    }
}