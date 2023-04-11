package pl.krystiankaniowski.performance.infrastructure.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.datetime.Clock

@Module
@InstallIn(SingletonComponent::class)
object InfrastructureModule {

    @Provides
    fun provideClock(): Clock = Clock.System
}