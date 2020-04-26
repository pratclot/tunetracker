package com.pratclot.tunetracker.overview

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.pratclot.tunetracker.database.TuneDatabaseDao
import com.pratclot.tunetracker.repository.TuneRepository
import java.lang.IllegalArgumentException
import javax.inject.Inject

class OverviewViewModelFactory @Inject constructor(
//    private val application: Application,
    private val tuneRepository: TuneRepository
) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(OverviewViewModel::class.java)) {
//            return OverviewViewModel(application, tuneRepository) as T
            return OverviewViewModel(tuneRepository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}