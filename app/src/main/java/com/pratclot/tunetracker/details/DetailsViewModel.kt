package com.pratclot.tunetracker.details

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.pratclot.tunetracker.domain.Tune
import com.pratclot.tunetracker.repository.TuneRepository
import com.squareup.inject.assisted.Assisted
import com.squareup.inject.assisted.AssistedInject
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import timber.log.Timber

class DetailsViewModel @AssistedInject constructor(
    @Assisted val tuneId: Long,
    val tuneRepository: TuneRepository
) :
    ViewModel() {

    @AssistedInject.Factory
    interface Factory {
        fun create(tuneId: Long): DetailsViewModel
    }

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

    override fun onCleared() {
        viewModelJob.cancel()
        super.onCleared()
    }
}
