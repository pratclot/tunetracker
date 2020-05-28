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
import timber.log.Timber

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

    //    companion object {
    //        This should have been a LiveData<Map>. But, as long as the Map there stays the same
//        object there will be no notifications for observers. Download counter helps to have
//        these notifications. This is ugly.
    var tuneProgresses = mutableMapOf<Long, Int>()
    val _downloadCounter = MutableLiveData<Int>()
    val downloadCounter: LiveData<Int>
        get() = _downloadCounter

    val _updateTuneOnDownloadFinish = MutableLiveData<Long>()
    val updateTuneOnDownloadFinish: LiveData<Long>
        get() = _updateTuneOnDownloadFinish

    fun updateTuneProgresses(downloadIdentifier: Long, progress: Int) {
        tuneProgresses[downloadIdentifier] = progress
        tuneProgresses.forEach {
            Timber.i("PROGRESSES: ${it.key} ${it.value}")
        }
        _downloadCounter.postValue(_downloadCounter.value?.plus(progress))
        if (progress == 100) {
            _updateTuneOnDownloadFinish.postValue(downloadIdentifier)
        }
    }
//    }

    fun updateTuneProgresses(tuneList: List<Tune>): List<Tune> {
        val currentTuneProgresses = tuneProgresses
        tuneProgresses = tuneList.let {
            var tuneProgresses = mutableMapOf<Long, Int>()
            it.forEach {
                tuneProgresses.put(it.id!!, it.progress)
                if (it.downloadComplete) {
                    tuneProgresses[it.id] = 100
                } else if (currentTuneProgresses.isNotEmpty()) {
                    currentTuneProgresses[it.id]?.let { it1 ->
                        tuneProgresses[it.id] = it1.toInt()
                    }
                }
            }
            return@let tuneProgresses
        }
        return tuneList.map {
            it.progress = tuneProgresses[it.id]!!
            it
        }
    }

    fun downloadFinishedFor(downloadIdentifier: Long) {
        viewModelScope.launch {
            tuneRepository.getTuneById(downloadIdentifier)!!.apply {
                downloadComplete = true
            }.let {
                tuneRepository.update(it)
            }
        }
    }

    init {
        _downloadCounter.value = 0
        _updateTuneOnDownloadFinish.value = -1L
    }

    fun onAddTuneThruDialog(name: String, url: String) {
        viewModelScope.launch {
            Tune(
                name = name,
                tabWebUrl = url
            ).let {
                tuneRepository.insert(
                    it,
                    object : ProgressReportingHttpClient.DownloadProgressCallback {
                        override fun onUpdate(downloadIdentifier: Long, progress: Int) {
                            updateTuneProgresses(downloadIdentifier, progress)
                        }

                        override fun onFinish(downloadIdentifier: Long) {
                            updateTuneProgresses(downloadIdentifier, 100)
                        }

                    })
            }
        }
    }

    fun reloadTune(id: Long) {
        viewModelScope.launch {
            tuneRepository.reload(
                id,
                object : ProgressReportingHttpClient.DownloadProgressCallback {
                    override fun onUpdate(downloadIdentifier: Long, progress: Int) {
                        updateTuneProgresses(downloadIdentifier, progress)
                    }

                    override fun onFinish(downloadIdentifier: Long) {
                        updateTuneProgresses(downloadIdentifier, 100)
                    }

                })
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
