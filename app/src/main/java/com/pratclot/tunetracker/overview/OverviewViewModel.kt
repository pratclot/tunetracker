package com.pratclot.tunetracker.overview

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.pratclot.tunetracker.domain.Tune
import com.pratclot.tunetracker.repository.ITuneRepository
import javax.inject.Inject
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch

class OverviewViewModel @Inject constructor(
    private val tuneRepository: ITuneRepository
) :
    ViewModel() {

    val tunes = tuneRepository.tunes

    private val _navigateToTuneDetails = MutableLiveData<Long>()
    val navigateToTuneDetails: LiveData<Long>
        get() = _navigateToTuneDetails

    fun onTuneClicked(it: Long?) {
        _navigateToTuneDetails.value = it
    }

    private val _addTuneDialogOpened = MutableLiveData<Boolean>()
    val addTuneDialogOpened: LiveData<Boolean>
        get() = _addTuneDialogOpened

    fun markAddTuneDialogAsOpened() {
        _addTuneDialogOpened.value = true
    }

    fun markAddTuneDialogAsClosed() {
        _addTuneDialogOpened.value = false
    }

    init {
    }

    fun onAddTuneThruDialog(name: String, url: String) {
        viewModelScope.launch {
            val newTune = Tune(
                name = name,
                tabWebUrl = url
            )
            tuneRepository.insert(newTune)
        }
    }

    fun onClear() {
        viewModelScope.launch {
            tuneRepository.clear()
        }
    }

    override fun onCleared() {
        viewModelScope.cancel()
        super.onCleared()
    }

    fun onDetailsNavigationEnded() {
        _navigateToTuneDetails.value = null
    }
}
