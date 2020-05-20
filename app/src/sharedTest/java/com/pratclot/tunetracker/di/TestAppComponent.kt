package com.pratclot.tunetracker.di

import com.pratclot.tunetracker.datasource.di.TestDataSourceModule
import com.pratclot.tunetracker.repository.di.TestRepositoryModule
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(
    modules = [
        AppSubcomponents::class,
        AssistedModule::class,
        TestCoroutineModule::class,
        TestDataSourceModule::class,
        TestRepositoryModule::class
    ]
)
interface TestAppComponent : AppComponent
