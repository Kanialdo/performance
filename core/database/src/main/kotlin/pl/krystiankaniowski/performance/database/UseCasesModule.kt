package pl.krystiankaniowski.performance.database

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import pl.krystiankaniowski.performance.database.usecase.GetFocusListUseCaseImpl
import pl.krystiankaniowski.performance.database.usecase.GetHistoryEntryUseCaseImpl
import pl.krystiankaniowski.performance.database.usecase.RemoveAllDataUseCaseImpl
import pl.krystiankaniowski.performance.database.usecase.SaveFocusUseCaseImpl
import pl.krystiankaniowski.performance.domain.RemoveAllDataUseCase
import pl.krystiankaniowski.performance.domain.usecase.GetFocusListUseCase
import pl.krystiankaniowski.performance.domain.usecase.GetHistoryEntryUseCase
import pl.krystiankaniowski.performance.domain.usecase.SaveFocusUseCase

@Module
@InstallIn(SingletonComponent::class)
internal interface UseCasesModule {

    @Binds
    fun bindSaveFocusUseCase(impl: SaveFocusUseCaseImpl): SaveFocusUseCase

    @Binds
    fun bindGetFocusListUseCase(impl: GetFocusListUseCaseImpl): GetFocusListUseCase

    @Binds
    fun bindRemoveAllDataUseCase(impl: RemoveAllDataUseCaseImpl): RemoveAllDataUseCase

    @Binds
    fun bindGetHistoryEntryUseCaseImpl(impl: GetHistoryEntryUseCaseImpl): GetHistoryEntryUseCase
}