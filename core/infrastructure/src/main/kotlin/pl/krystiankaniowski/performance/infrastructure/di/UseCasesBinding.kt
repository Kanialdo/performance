package pl.krystiankaniowski.performance.infrastructure.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import pl.krystiankaniowski.performance.domain.usecase.GetFocusListUseCase
import pl.krystiankaniowski.performance.domain.usecase.SaveFocusUseCase
import pl.krystiankaniowski.performance.domain.usecase.dnd.IsDoNotDisturbEnabledUseCase
import pl.krystiankaniowski.performance.domain.usecase.dnd.SetDoNotDisturbEnabledUseCase
import pl.krystiankaniowski.performance.infrastructure.usecase.GetFocusListUseCaseImpl
import pl.krystiankaniowski.performance.infrastructure.usecase.IsDoNotDisturbEnabledUseCaseImpl
import pl.krystiankaniowski.performance.infrastructure.usecase.SaveFocusUseCaseImpl
import pl.krystiankaniowski.performance.infrastructure.usecase.SetDoNotDisturbEnabledUseCaseImpl

@Module
@InstallIn(SingletonComponent::class)
interface UseCasesBinding {

    @Binds
    fun bindSaveFocusUseCase(impl: SaveFocusUseCaseImpl): SaveFocusUseCase

    @Binds
    fun bindGetFocusListUseCase(impl: GetFocusListUseCaseImpl): GetFocusListUseCase

    @Binds
    fun bindIsDoNotDisturbEnabledUseCase(impl: IsDoNotDisturbEnabledUseCaseImpl): IsDoNotDisturbEnabledUseCase

    @Binds
    fun bindSetDoNotDisturbEnabledUseCase(impl: SetDoNotDisturbEnabledUseCaseImpl): SetDoNotDisturbEnabledUseCase
}