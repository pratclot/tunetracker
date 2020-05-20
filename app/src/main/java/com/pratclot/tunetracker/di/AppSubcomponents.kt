package com.pratclot.tunetracker.di

import com.pratclot.tunetracker.details.di.DetailsComponent
import com.pratclot.tunetracker.overview.di.OverviewComponent
import dagger.Module

@Module(subcomponents = [
    OverviewComponent::class,
    DetailsComponent::class
])
abstract class AppSubcomponents
