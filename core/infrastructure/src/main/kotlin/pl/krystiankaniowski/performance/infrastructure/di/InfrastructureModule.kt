package pl.krystiankaniowski.performance.infrastructure.di

import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.datetime.Clock
import pl.krystiankaniowski.performance.domain.RemoveAllDataUseCase
import pl.krystiankaniowski.performance.domain.provider.StringsProvider
import pl.krystiankaniowski.performance.domain.usecase.GetFocusListUseCase
import pl.krystiankaniowski.performance.domain.usecase.GetHistoryEntryUseCase
import pl.krystiankaniowski.performance.domain.usecase.SaveFocusUseCase
import pl.krystiankaniowski.performance.infrastructure.provider.StringsProviderImpl
import pl.krystiankaniowski.performance.infrastructure.usecase.GetFocusListUseCaseImpl
import pl.krystiankaniowski.performance.infrastructure.usecase.GetHistoryEntryUseCaseImpl
import pl.krystiankaniowski.performance.infrastructure.usecase.RemoveAllDataUseCaseImpl
import pl.krystiankaniowski.performance.infrastructure.usecase.SaveFocusUseCaseImpl

@Module(includes = [InfrastructureModule.Bindings::class])
@InstallIn(SingletonComponent::class)
object InfrastructureModule {

    @Provides
    fun provideClock(): Clock = Clock.System

    @Module
    @InstallIn(SingletonComponent::class)
    interface Bindings {

        @Binds
        fun bindSaveFocusUseCase(impl: SaveFocusUseCaseImpl): SaveFocusUseCase

        @Binds
        fun bindGetFocusListUseCase(impl: GetFocusListUseCaseImpl): GetFocusListUseCase

        @Binds
        fun bindRemoveAllDataUseCase(impl: RemoveAllDataUseCaseImpl): RemoveAllDataUseCase

        @Binds
        fun bindGetHistoryEntryUseCaseImpl(impl: GetHistoryEntryUseCaseImpl): GetHistoryEntryUseCase

        @Binds
        fun bindStringsProvider(impl: StringsProviderImpl): StringsProvider
    }
}