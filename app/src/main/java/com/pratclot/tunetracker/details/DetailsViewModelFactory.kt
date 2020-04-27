package com.pratclot.tunetracker.details

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.pratclot.tunetracker.repository.TuneRepository
import java.lang.IllegalArgumentException
import javax.inject.Inject

class DetailsViewModelFactory @Inject constructor(
    private val tuneId: Long,
    private val tuneRepository: TuneRepository
) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(DetailsViewModel::class.java)) {
            return DetailsViewModel(
                tuneId,
                tuneRepository
            ) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }

}
