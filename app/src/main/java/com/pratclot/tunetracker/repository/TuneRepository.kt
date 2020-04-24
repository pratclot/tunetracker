package com.pratclot.tunetracker.repository

import android.app.Application
import android.content.Context
import android.net.Uri
import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import com.pratclot.tunetracker.database.TuneDatabase
import com.pratclot.tunetracker.database.asDomainModel
import com.pratclot.tunetracker.domain.Tune
import com.pratclot.tunetracker.service.TuneRest
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import timber.log.Timber
import java.io.File

private const val PDF_FILE_EXTENSION = ".pdf"

class TuneRepository(
    private val database: TuneDatabase,
    private val application: Application
) {
    val tunes: LiveData<List<Tune>> =
        Transformations.map(database.tuneDatabaseDao.getAll()) {
            it.asDomainModel()
        }

    suspend fun insert(tune: Tune) {
        val filename = downloadTabInPdf(tune)
        tune.tabLocalUrl = filename

        withContext(Dispatchers.IO) {
            database.tuneDatabaseDao.insert(tune.asDatabaseModel())
        }
    }

    suspend fun clear() {
        withContext(Dispatchers.IO) {
            database.tuneDatabaseDao.clear()
        }
    }

    suspend fun downloadTabInPdf(tune: Tune): String {
        val filename = tune.name + PDF_FILE_EXTENSION
        val file = File(application.filesDir, filename)
        if (file.exists()) {
            Timber.i("Did not download the file as it exists! $filename")
        } else {
            withContext(Dispatchers.IO) {
                val pdfContent = TuneRest.retrofitService.downloadTabInPdf(tune.tabWebUrl).await()

                pdfContent.byteStream()?.let {
                    application.openFileOutput(filename, Context.MODE_PRIVATE).use {
                        it.write(pdfContent.bytes())
                    }
                }
            }
        }
        return file.absolutePath
    }

    fun getTuneByName(name: String): Uri {
        val retrievedDatabaseTune = database.tuneDatabaseDao.get(name)?.asDomainModel()
        var uri = Uri.EMPTY
        if (retrievedDatabaseTune != null) {
            uri = Uri.fromFile(File(retrievedDatabaseTune.tabLocalUrl))
        }
        return uri
    }

    suspend fun getTuneById(id: Long): Tune? {
        var tune = Tune(name = "123")
        withContext(Dispatchers.IO) {
            tune = database.tuneDatabaseDao.getById(id).asDomainModel()!!
        }

        return tune
    }
}
