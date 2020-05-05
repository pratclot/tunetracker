package com.pratclot.tunetracker.datasource

import android.content.Context
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import com.pratclot.tunetracker.R
import com.pratclot.tunetracker.domain.Tune
import com.pratclot.tunetracker.service.IRestService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import okio.Buffer
import org.junit.Assert.assertArrayEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import retrofit2.Retrofit

@RunWith(AndroidJUnit4::class)
class RemoteDataSourceTest {
    private lateinit var retrofitService: IRestService
    private lateinit var remoteDataSource: RemoteDataSource

    @get:Rule
    val server = MockWebServer()

    private val application: Context = ApplicationProvider.getApplicationContext()

    private val testTuneId = 1L
    private val testTuneName = "testTune"
    private val testTuneTabWebUrl = "/"
    private val testTune = Tune(testTuneId, testTuneName, testTuneTabWebUrl)

    private lateinit var responseBody: ByteArray

    @Before
    fun setup() {
        responseBody = application.resources.openRawResource(R.raw.dummy)
            .readBytes()

        val retrofit = Retrofit.Builder()
            .addCallAdapterFactory(CoroutineCallAdapterFactory())
            .baseUrl(server.url("/"))
            .build()

        retrofitService = retrofit.create(IRestService::class.java)
        remoteDataSource = RemoteDataSource(
            Dispatchers.Unconfined,
            retrofitService
        )
    }

    @Test
    fun downloadPdfFromRemote() {
        server.enqueue(
            MockResponse().setBody(
                Buffer().readFrom(
                    application.resources.openRawResource(
                        R.raw.dummy
                    )
                )
            )
        )

        val response = runBlocking {
            remoteDataSource.downloadPdfFromRemote(testTune)
        }

        assertArrayEquals(responseBody, response?.bytes())
    }
}