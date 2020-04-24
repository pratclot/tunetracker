package com.pratclot.tunetracker.details

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.pratclot.tunetracker.repository.TuneRepository
import java.lang.IllegalArgumentException

class DetailsViewModelFactory(
    private val tuneId: Long,
    private val tuneRepository: TuneRepository,
    private val application: Application
): ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(DetailsViewModel::class.java)) {
            return DetailsViewModel(tuneId, tuneRepository, application) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }

}
