package com.pratclot.tunetracker.overview

import androidx.fragment.app.testing.FragmentScenario
import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.navigation.NavHostController
import androidx.navigation.Navigation
import androidx.navigation.testing.TestNavHostController
import androidx.recyclerview.widget.RecyclerView
import androidx.test.core.app.ApplicationProvider.getApplicationContext
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.Espresso.openActionBarOverflowOrOptionsMenu
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.ViewMatchers.hasChildCount
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.MediumTest
import com.pratclot.tunetracker.R
import com.pratclot.tunetracker.datasource.fakes.FakeLocalDatasource
import com.pratclot.tunetracker.domain.Tune
import com.pratclot.tunetracker.repository.fakes.FakeTuneRepository
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@MediumTest
@RunWith(AndroidJUnit4::class)
class OverviewFragmentTest {
    private lateinit var scenario: FragmentScenario<OverviewFragment>
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

        scenario = launchFragmentInContainer {
            OverviewFragment().also {
                it.overviewViewModelFactory = OverviewViewModelFactory(fakeTuneRepository)
            }
        }.apply {
            onFragment {
                Navigation.setViewNavController(it.view!!, navController)
            }
        }
    }

    @After
    fun clean() {
        tuneList.removeAll { true }
    }

    @Test
    fun openDialog() {
        openActionBarOverflowOrOptionsMenu(getApplicationContext())
    }

    @Test
    fun clickTuneListItem() {
        onView(withId(R.id.tune_view)).perform(click())
        assertEquals(navController.currentDestination?.id, R.id.detailsFragment)
    }

    @Test
    fun clearTunes() {
        openActionBarOverflowOrOptionsMenu(getApplicationContext())
        onView(withText(R.string.clear_database_text)).perform(click())
        onView(withId(R.id.tune_view)).perform(RecyclerViewActions.scrollToPosition<RecyclerView.ViewHolder>(0))
        onView(withId(R.id.tune_view)).check(matches(hasChildCount(0)))
    }
}
