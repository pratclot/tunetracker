package com.pratclot.tunetracker.repository

import android.content.Context
import com.pratclot.tunetracker.R
import com.pratclot.tunetracker.datasource.IRemoteDataSource
import com.pratclot.tunetracker.domain.Tune
import java.io.File
import javax.inject.Inject
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import okhttp3.ResponseBody
import timber.log.Timber

class RepositoryTool @Inject constructor(
    private var remoteDataSource: IRemoteDataSource,
    private var application: Context,
    private var ioDispatcher: CoroutineDispatcher
) : IRepositoryTool {
    override suspend fun getLocalPathTo(tune: Tune): String {
        val filename = tune.name + application.resources.getString(R.string.pdf_file_extension)
        val file = File(application.filesDir, filename)
        if (file.exists()) {
            Timber.i("Did not download the file as it exists! $filename")
        } else {
            savePdfToFile(
                remoteDataSource.downloadPdfFromRemote(tune),
                filename
            )
        }
        return file.absolutePath
    }

    private suspend fun savePdfToFile(pdfContent: ResponseBody, filename: String) {
        withContext(ioDispatcher) {
            pdfContent.byteStream()?.let {
                application.openFileOutput(filename, Context.MODE_PRIVATE).use {
                    it.write(pdfContent.bytes())
                }
            }
        }
    }
}
