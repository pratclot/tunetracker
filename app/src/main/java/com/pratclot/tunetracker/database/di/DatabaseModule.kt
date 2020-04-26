package com.pratclot.tunetracker.database.di

import android.content.Context
import com.pratclot.tunetracker.database.TuneDatabase
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class DatabaseModule {

    @Singleton
    @Provides
    fun provideDatabase(application: Context): TuneDatabase {
        return TuneDatabase.getInstance(application)
    }
}