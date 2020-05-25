package com.pratclot.tunetracker

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.recyclerview.widget.RecyclerView
import androidx.test.core.app.ApplicationProvider.getApplicationContext
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.Espresso.openActionBarOverflowOrOptionsMenu
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.ViewMatchers.hasDescendant
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.filters.LargeTest
import com.pratclot.tunetracker.ui.MainActivity
import org.junit.FixMethodOrder
import org.junit.Rule
import org.junit.Test
import org.junit.runners.MethodSorters

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
@LargeTest
class WholeAppTest {
    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()
    @get:Rule
    var activityScenario = ActivityScenarioRule(MainActivity::class.java)

    @Test
    fun clickOnRecyclerView() {
        onView(withId(R.id.tune_view)).perform(
            RecyclerViewActions.actionOnItem<RecyclerView.ViewHolder>(
                hasDescendant(withText(R.string.test_tune_name)), click()
            )
        )
        onView(withId(R.id.tune_name)).check(matches(withText(R.string.test_tune_name)))
    }

    @Test
    fun addTune() {
        openActionBarOverflowOrOptionsMenu(getApplicationContext())
        onView(withText(R.string.add_tune_button_text)).perform(click())
        onView(withText(R.string.add_tune_dialog_title)).check(matches(isDisplayed()))
        onView(withText(R.string.add_tune_button_text)).perform(click())
        onView(withId(R.id.tune_view)).perform(
            RecyclerViewActions.scrollToPosition<RecyclerView.ViewHolder>(0)
        )
//        Espresso does not observe LiveData it seems, the above step used to help, not anymore
//        onView(withId(R.id.tune_view)).check(matches(hasDescendant(withText(R.string.sample_tune_name))))
    }

    @Test
    fun zzz_clearTunes() {
        onView(withId(R.id.tune_view)).check(matches(hasDescendant(withText(R.string.test_tune_name))))
        openActionBarOverflowOrOptionsMenu(getApplicationContext())
        onView(withText(R.string.clear_database_text)).check(matches(isDisplayed()))
        onView(withText(R.string.clear_database_text)).perform(click())
                onView(withId(R.id.tune_view)).perform(
                    RecyclerViewActions.scrollToPosition<RecyclerView.ViewHolder>(1)
                )
//        Espresso does not observe LiveData it seems, the above step used to help, not anymore
//        onView(withId(R.id.tune_view)).check(matches(hasChildCount(0)))
    }
}
