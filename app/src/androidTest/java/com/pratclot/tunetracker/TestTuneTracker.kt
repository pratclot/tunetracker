package com.pratclot.tunetracker

import com.pratclot.tunetracker.di.AppComponent
import com.pratclot.tunetracker.di.DaggerTestAppComponent

class TestTuneTracker : TuneTracker() {
    override fun initializeComponent(): AppComponent {
        return DaggerTestAppComponent.create()
    }
}
