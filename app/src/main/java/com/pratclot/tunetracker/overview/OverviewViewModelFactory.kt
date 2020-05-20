package com.pratclot.tunetracker.overview

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.pratclot.tunetracker.repository.ITuneRepository
import javax.inject.Inject

class OverviewViewModelFactory @Inject constructor(
    private val tuneRepository: ITuneRepository
) : ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(OverviewViewModel::class.java)) {
            return OverviewViewModel(tuneRepository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
