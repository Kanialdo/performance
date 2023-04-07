package pl.krystiankaniowski.performance.infrastructure.di

import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.datetime.Clock
import pl.krystiankaniowski.performance.domain.provider.StringsProvider
import pl.krystiankaniowski.performance.infrastructure.provider.StringsProviderImpl

@Module(includes = [InfrastructureModule.Bindings::class])
@InstallIn(SingletonComponent::class)
object InfrastructureModule {

    @Provides
    fun provideClock(): Clock = Clock.System

    @Module
    @InstallIn(SingletonComponent::class)
    interface Bindings {

        @Binds
        fun bindStringsProvider(impl: StringsProviderImpl): StringsProvider
    }
}