package com.pratclot.tunetracker

import com.facebook.stetho.Stetho
import timber.log.Timber

open class TuneTrackerDebug : TuneTracker() {
    override fun onCreate() {
        super.onCreate()
        Timber.plant(Timber.DebugTree())
        Stetho.initializeWithDefaults(applicationContext)
    }
}
