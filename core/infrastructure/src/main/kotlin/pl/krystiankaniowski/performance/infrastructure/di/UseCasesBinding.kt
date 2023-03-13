package pl.krystiankaniowski.performance.infrastructure.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import pl.krystiankaniowski.performance.domain.usecase.SaveFocusUseCase
import pl.krystiankaniowski.performance.infrastructure.usecase.SaveFocusUseCaseImpl

@Module
@InstallIn(SingletonComponent::class)
interface UseCasesBinding {

    @Binds
    fun bindsSaveFocusUseCase(saveFocusUseCase: SaveFocusUseCaseImpl): SaveFocusUseCase
}