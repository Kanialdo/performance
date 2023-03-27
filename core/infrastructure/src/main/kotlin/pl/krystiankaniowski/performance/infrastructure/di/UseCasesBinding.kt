package pl.krystiankaniowski.performance.infrastructure.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import pl.krystiankaniowski.performance.domain.RemoveAllDataUseCase
import pl.krystiankaniowski.performance.domain.provider.StringsProvider
import pl.krystiankaniowski.performance.domain.usecase.GetFocusListUseCase
import pl.krystiankaniowski.performance.domain.usecase.SaveFocusUseCase
import pl.krystiankaniowski.performance.infrastructure.provider.StringsProviderImpl
import pl.krystiankaniowski.performance.infrastructure.usecase.GetFocusListUseCaseImpl
import pl.krystiankaniowski.performance.infrastructure.usecase.RemoveAllDataUseCaseImpl
import pl.krystiankaniowski.performance.infrastructure.usecase.SaveFocusUseCaseImpl

@Module
@InstallIn(SingletonComponent::class)
interface UseCasesBinding {

    @Binds
    fun bindSaveFocusUseCase(impl: SaveFocusUseCaseImpl): SaveFocusUseCase

    @Binds
    fun bindGetFocusListUseCase(impl: GetFocusListUseCaseImpl): GetFocusListUseCase

    @Binds
    fun bindRemoveAllDataUseCase(impl: RemoveAllDataUseCaseImpl): RemoveAllDataUseCase

    @Binds
    fun bindStringsProvider(impl: StringsProviderImpl): StringsProvider
}