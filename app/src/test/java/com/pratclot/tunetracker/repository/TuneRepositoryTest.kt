package com.pratclot.tunetracker.repository

import androidx.test.filters.SmallTest
import com.pratclot.tunetracker.datasource.ILocalDataSource
import com.pratclot.tunetracker.datasource.fakes.FakeLocalDatasource
import com.pratclot.tunetracker.domain.Tune
import com.pratclot.tunetracker.repository.fakes.FakeRepositoryTool
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

@ExperimentalCoroutinesApi
@SmallTest
class TuneRepositoryTest {
    private lateinit var datasource: ILocalDataSource
    private lateinit var repositoryTool: IRepositoryTool
    private lateinit var tuneRepository: TuneRepository

    private val testTuneId = 1L
    private val testTuneName = "testTune"
    private val testTuneTabLocalUrl = "file:///"
    private val newTuneId = 2L
    private val newTuneName = "newTune"
    private val testTune = Tune(testTuneId, testTuneName, testTuneTabLocalUrl)
    private val tuneList = mutableListOf<Tune>()

    @Before
    fun setup() {
        tuneList.add(testTune)
        datasource =
            FakeLocalDatasource(
                tuneList
            )
        repositoryTool =
            FakeRepositoryTool()
        tuneRepository = TuneRepository(
            datasource,
            repositoryTool,
            Dispatchers.Unconfined
        )
    }

    @Test
    fun getTunes() {
        assertEquals(tuneRepository.tunes.value, tuneList)
    }

    @Test
    fun insert() {
        val newTune = Tune(newTuneId, newTuneName)
        runBlockingTest {
            tuneRepository.insert(newTune)
        }
        assertEquals(datasource.get(newTuneName), newTune)
    }

    @Test
    fun clear() = runBlockingTest {
        tuneRepository.clear()
        assertEquals(datasource.getAll().value?.size, 0)
    }

    @Test
    fun getTuneByName() = runBlockingTest {
        assertEquals(
            tuneRepository.getTuneByName(testTuneName),
            testTuneTabLocalUrl
        )
    }

    @Test
    fun getTuneById() = runBlockingTest {
        assertEquals(tuneRepository.getTuneById(testTuneId), testTune)
    }
}
