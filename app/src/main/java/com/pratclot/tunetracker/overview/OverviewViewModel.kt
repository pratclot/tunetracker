package com.pratclot.tunetracker.overview

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.pratclot.tunetracker.domain.Tune
import com.pratclot.tunetracker.repository.ITuneRepository
import kotlinx.coroutines.*
import javax.inject.Inject

class OverviewViewModel @Inject constructor(
    private val tuneRepository: ITuneRepository
) :
    ViewModel() {
    private val viewModelJob = Job()
    private val uiScope = CoroutineScope(Dispatchers.Main + viewModelJob)

    val tunes = tuneRepository.tunes

    private val _navigateToTuneDetails = MutableLiveData<Long>()
    val navigateToTuneDetails: LiveData<Long>
        get() = _navigateToTuneDetails

    fun onTuneClicked(it: Long?) {
        _navigateToTuneDetails.value = it
    }

    init {}

    fun onAddTuneThruDialog(name: String, url: String) {
        uiScope.launch {
            val newTune = Tune(
                name = name,
                tabWebUrl = url
            )
            tuneRepository.insert(newTune)
        }
    }

    fun onClear() {
        uiScope.launch {
            tuneRepository.clear()
        }
    }

    override fun onCleared() {
        viewModelJob.cancel()
        super.onCleared()
    }

    fun onDetailsNavigationEnded() {
        _navigateToTuneDetails.value = null
    }
}