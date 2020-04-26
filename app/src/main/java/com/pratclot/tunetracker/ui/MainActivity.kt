package com.pratclot.tunetracker.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.pratclot.tunetracker.R
import com.pratclot.tunetracker.TuneTracker
import com.pratclot.tunetracker.overview.di.OverviewComponent

class MainActivity : AppCompatActivity() {

//    Just injecting OverviewFragment into AppComponent failed with error:
//    MainActivity could not be cast to TuneTracker
//    The instance of MainActivity was returned by requireActivity() in fragment's onAttach().
//    In the codelab there was no such error. Anyway, it worked with a subcomponent.
    lateinit var overviewComponent: OverviewComponent

    override fun onCreate(savedInstanceState: Bundle?) {
        overviewComponent = (application as TuneTracker).appComponent.overviewComponent().create()
        (application as TuneTracker).appComponent.inject(this)
        super.onCreate(savedInstanceState)

//        setSupportActionBar(findViewById(R.id.toolbar))
        setContentView(R.layout.activity_main)

    }
}
