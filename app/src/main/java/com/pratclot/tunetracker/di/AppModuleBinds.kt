package com.pratclot.tunetracker.di

import com.pratclot.tunetracker.repository.ITuneRepository
import com.pratclot.tunetracker.repository.TuneRepository
import dagger.Binds
import dagger.Module
import javax.inject.Singleton

@Module
abstract class AppModuleBinds {

    @Singleton
    @Binds
    abstract fun bindRepo(repo: TuneRepository): ITuneRepository

}