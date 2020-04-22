package com.pratclot.tunetracker.overview

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.pratclot.tunetracker.database.TuneDatabaseDao
import java.lang.IllegalArgumentException

class OverviewViewModelFactory(
    private val database: TuneDatabaseDao,
    private val application: Application
) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(OverviewViewModel::class.java)) {
            return OverviewViewModel(database, application) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}