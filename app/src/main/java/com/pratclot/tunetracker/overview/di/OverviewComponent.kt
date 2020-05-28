package com.pratclot.tunetracker.overview.di

import com.pratclot.tunetracker.overview.OverviewFragment
import com.pratclot.tunetracker.service.MyRetrofit
import dagger.Subcomponent

@Subcomponent
interface OverviewComponent {

    @Subcomponent.Factory
    interface Factory {
        fun create(): OverviewComponent
    }

    fun inject(fragment: OverviewFragment)

    val myRetrofitServiceFactory: MyRetrofit.Factory
}
