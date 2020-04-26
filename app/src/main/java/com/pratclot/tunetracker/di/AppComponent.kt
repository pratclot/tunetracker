package com.pratclot.tunetracker.di

import android.app.Application
import android.content.Context
import com.pratclot.tunetracker.database.di.DatabaseModule
import com.pratclot.tunetracker.details.DetailsFragment
import com.pratclot.tunetracker.details.di.DetailsComponent
import com.pratclot.tunetracker.details.di.DetailsModule
import com.pratclot.tunetracker.overview.OverviewFragment
import com.pratclot.tunetracker.overview.di.OverviewComponent
import com.pratclot.tunetracker.repository.ITuneRepository
import com.pratclot.tunetracker.repository.TuneRepository
import com.pratclot.tunetracker.ui.MainActivity
import dagger.BindsInstance
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [DatabaseModule::class, AppSubcomponents::class,
    DetailsModule::class,
    AssistedModule::class]
)
interface AppComponent {
    @Component.Factory
    interface Factory {
        fun create(@BindsInstance context: Context): AppComponent
    }

    fun overviewComponent(): OverviewComponent.Factory
    fun detailsComponent(): DetailsComponent.Factory

    fun inject(activity: MainActivity)
}
