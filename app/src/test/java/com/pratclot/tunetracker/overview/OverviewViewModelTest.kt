package com.pratclot.tunetracker.overview

import androidx.test.filters.SmallTest
import com.pratclot.tunetracker.datasource.fakes.FakeLocalDatasource
import com.pratclot.tunetracker.domain.Tune
import com.pratclot.tunetracker.repository.ITuneRepository
import com.pratclot.tunetracker.repository.fakes.FakeTuneRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.runBlockingTest
import kotlinx.coroutines.test.setMain
import org.hamcrest.CoreMatchers.`is`
import org.hamcrest.MatcherAssert.assertThat
import org.junit.Before
import org.junit.Test

@ExperimentalCoroutinesApi
@SmallTest
class OverviewViewModelTest {
    private lateinit var tuneRepository: ITuneRepository
    private lateinit var overviewViewModel: OverviewViewModel

    @Before
    fun setup() = runBlocking() {
        val tune = Tune(1, "testTune")
        val datasource =
            FakeLocalDatasource(
                mutableListOf(tune)
            )
        tuneRepository =
            FakeTuneRepository(
                datasource
            )

        overviewViewModel = OverviewViewModel(tuneRepository)

        Dispatchers.setMain(Dispatchers.Unconfined)
    }

    @Test
    fun onAddTuneThruDialog_insertOneTune() = runBlockingTest {
        val name = "newTune"
        val url = "https://tommyemmanuel.files.wordpress.com/2008/12/chet-atkins-walk-dont-run2.pdf"

        overviewViewModel.onAddTuneThruDialog(name, url)
        assertThat(tuneRepository.getTuneByName(name), `is`(url))
    }

    @Test
    fun onClear() = runBlockingTest {
        overviewViewModel.onClear()
        assertThat(tuneRepository.tunes.value?.size, `is`(0))
    }
}
