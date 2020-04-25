package com.pratclot.tunetracker

import android.app.Application
import timber.log.Timber

open class TuneTracker : Application() {
    override fun onCreate() {
        Timber.plant(Timber.DebugTree())
        super.onCreate()
    }
}

