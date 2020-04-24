package com.pratclot.tunetracker.details

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.pratclot.tunetracker.database.TuneDatabase
import com.pratclot.tunetracker.repository.TuneRepository

class DetailsViewModel(
    val tuneRepository: TuneRepository,
    application: Application
) :
    AndroidViewModel(application) {

//    private val database = TuneDatabase.getInstance(application)

//    private val tuneRepository = TuneRepository(database)

    val tunes = tuneRepository.tunes
}
