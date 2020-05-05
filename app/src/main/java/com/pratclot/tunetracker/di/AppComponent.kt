package com.pratclot.tunetracker.di

import android.content.Context
import com.pratclot.tunetracker.database.di.DatabaseModule
import com.pratclot.tunetracker.datasource.di.DataSourceModule
import com.pratclot.tunetracker.details.di.DetailsComponent
import com.pratclot.tunetracker.overview.di.OverviewComponent
import com.pratclot.tunetracker.repository.di.RepositoryModule
import com.pratclot.tunetracker.service.di.RestModule
import com.pratclot.tunetracker.ui.MainActivity
import dagger.BindsInstance
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(
    modules = [
        DatabaseModule::class,
        AppSubcomponents::class,
        AssistedModule::class,
        CoroutineModule::class,
        DataSourceModule::class,
        RestModule::class,
        RepositoryModule::class
    ]
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
