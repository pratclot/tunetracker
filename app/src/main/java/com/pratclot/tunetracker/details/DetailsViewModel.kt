package com.pratclot.tunetracker.details

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.pratclot.tunetracker.database.TuneDatabase
import com.pratclot.tunetracker.domain.Tune
import com.pratclot.tunetracker.repository.TuneRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import timber.log.Timber

class DetailsViewModel(
    val tuneId: Long,
    val tuneRepository: TuneRepository,
    application: Application
) :
    AndroidViewModel(application) {

    private val viewModelJob = Job()

    private val uiScope = CoroutineScope(Dispatchers.Main + viewModelJob)

    private val _tune = MutableLiveData<Tune>()
    val tune: LiveData<Tune>
        get() = _tune

    init {
        uiScope.launch {
            _tune.value = tuneRepository.getTuneById(tuneId)
        }
        Timber.i("123")
    }
}
