package com.pratclot.tunetracker.overview.di

import com.pratclot.tunetracker.overview.OverviewFragment
import dagger.Subcomponent

@Subcomponent
interface OverviewComponent {

    @Subcomponent.Factory
    interface Factory {
        fun create(): OverviewComponent
    }

    fun inject(fragment: OverviewFragment)
}