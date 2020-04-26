package com.pratclot.tunetracker

import android.app.Application
import com.pratclot.tunetracker.di.AppComponent
import com.pratclot.tunetracker.di.DaggerAppComponent
import timber.log.Timber

open class TuneTracker : Application() {
    val appComponent: AppComponent by lazy {
        DaggerAppComponent.factory().create(applicationContext)
    }

    override fun onCreate() {
        Timber.plant(Timber.DebugTree())
        super.onCreate()
    }
}

