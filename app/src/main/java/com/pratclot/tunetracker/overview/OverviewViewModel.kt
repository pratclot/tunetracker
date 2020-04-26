package com.pratclot.tunetracker.overview

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.pratclot.tunetracker.domain.Tune
import com.pratclot.tunetracker.repository.ITuneRepository
import com.pratclot.tunetracker.repository.TuneRepository
import kotlinx.coroutines.*
import javax.inject.Inject

class OverviewViewModel @Inject constructor(
    private val tuneRepository: ITuneRepository
) :
    ViewModel() {
    private val viewModelJob = Job()
    private val uiScope = CoroutineScope(Dispatchers.Main + viewModelJob)

    val tunes = tuneRepository.tunes

    var tuneNameInputText = MutableLiveData<String>()

    private val _navigateToTuneDetails = MutableLiveData<Long>()
    val navigateToTuneDetails: LiveData<Long>
        get() = _navigateToTuneDetails

    fun onTuneClicked(it: Long?) {
        _navigateToTuneDetails.value = it
    }

    init {
    }

    fun onAddTune() {
        uiScope.launch {
            if (tuneNameInputText.value != null) {
                val newTune = Tune(
                    name = tuneNameInputText.value.toString()
                )
                tuneRepository.insert(newTune)
            }
            tuneNameInputText.value = null
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