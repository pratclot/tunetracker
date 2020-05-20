package com.pratclot.tunetracker.details

import androidx.fragment.app.testing.FragmentScenario
import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.navigation.NavHostController
import androidx.navigation.testing.TestNavHostController
import androidx.test.core.app.ApplicationProvider.getApplicationContext
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.pratclot.tunetracker.R
import com.pratclot.tunetracker.datasource.fakes.FakeLocalDatasource
import com.pratclot.tunetracker.domain.Tune
import com.pratclot.tunetracker.repository.fakes.FakeTuneRepository
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class DetailsFragmentTest {
    private lateinit var scenario: FragmentScenario<DetailsFragment>
    private lateinit var navController: NavHostController

    private lateinit var fakeLocalDatasource: FakeLocalDatasource
    private lateinit var fakeTuneRepository: FakeTuneRepository

    private val testTuneId = 1L
    private val testTuneName = "testTune"
    private val testTuneTabLocalUrl = "file:///"
    private val testTune = Tune(testTuneId, testTuneName, testTuneTabLocalUrl)
    private val tuneList = mutableListOf<Tune>()

    @Before
    fun init() {
        tuneList.add(testTune)
        fakeLocalDatasource = FakeLocalDatasource(tuneList)
        fakeTuneRepository = FakeTuneRepository(fakeLocalDatasource)

        navController = TestNavHostController(getApplicationContext())
        navController.setGraph(R.navigation.nav_graph)

        val bundle = DetailsFragmentArgs.Builder(testTuneId).build().toBundle()
        scenario = launchFragmentInContainer(bundle) {
            DetailsFragment().also {
                it.factory = object : DetailsViewModel.Factory {
                    override fun create(tuneId: Long): DetailsViewModel {
                        return DetailsViewModel(tuneId, fakeTuneRepository)
                    }
                }
            }
        }
    }

    @Test
    fun ensureTuneName() {
        onView(withId(R.id.tune_name)).check(matches(withText(testTuneName)))
    }
}
