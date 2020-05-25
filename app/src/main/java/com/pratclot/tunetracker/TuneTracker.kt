package com.pratclot.tunetracker

import android.app.Application
import com.pratclot.tunetracker.di.AppComponent
import com.pratclot.tunetracker.di.DaggerAppComponent

open class TuneTracker : Application() {
    val appComponent: AppComponent by lazy {
        initializeComponent()
    }

    open fun initializeComponent(): AppComponent {
        return DaggerAppComponent.factory().create(applicationContext)
    }
}
