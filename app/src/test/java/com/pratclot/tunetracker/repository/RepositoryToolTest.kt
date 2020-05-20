package com.pratclot.tunetracker.repository

import android.content.Context
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.MediumTest
import com.pratclot.tunetracker.R
import com.pratclot.tunetracker.datasource.fakes.FakeRemoteDatasource
import com.pratclot.tunetracker.domain.Tune
import java.io.File
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@ExperimentalCoroutinesApi
@MediumTest
@RunWith(AndroidJUnit4::class)
class RepositoryToolTest {
    private lateinit var repositoryTool: RepositoryTool

    private val application: Context = ApplicationProvider.getApplicationContext()

    private val testTuneId = 1L
    private val testTuneName = "testTune"
    private val testTune = Tune(testTuneId, testTuneName)

    @Before
    fun setup() {
        repositoryTool = RepositoryTool(
            FakeRemoteDatasource(),
            application,
            Dispatchers.Unconfined
        )
    }

    @Test
    fun getLocalPathTo() = runBlockingTest {
        assertEquals(
            repositoryTool.getLocalPathTo(testTune),
            File(
                application.filesDir,
                testTuneName + application.resources.getString(
                    R.string.pdf_file_extension
                )
            ).absolutePath
        )
    }
}
