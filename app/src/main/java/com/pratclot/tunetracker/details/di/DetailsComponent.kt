package com.pratclot.tunetracker.details.di

import com.pratclot.tunetracker.details.DetailsFragment
import com.pratclot.tunetracker.details.DetailsViewModel
import dagger.Subcomponent

@Subcomponent
interface DetailsComponent {

    @Subcomponent.Factory
    interface Factory {
        fun create(): DetailsComponent
    }

    fun inject(fragment: DetailsFragment)

    val detailsViewModelFactory: DetailsViewModel.Factory
}
