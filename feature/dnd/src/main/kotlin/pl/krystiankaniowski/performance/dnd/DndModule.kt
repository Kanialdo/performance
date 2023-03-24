package pl.krystiankaniowski.performance.dnd

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import pl.krystiankaniowski.performance.dnd.usecase.IsDoNotDisturbEnabledUseCaseImpl
import pl.krystiankaniowski.performance.dnd.usecase.SetDoNotDisturbEnabledUseCaseImpl
import pl.krystiankaniowski.performance.dnd.usecase.TurnOffDoNotDisturbUseCaseImpl
import pl.krystiankaniowski.performance.dnd.usecase.TurnOnDoNotDisturbUseCaseImpl
import pl.krystiankaniowski.performance.domain.usecase.dnd.IsDoNotDisturbEnabledUseCase
import pl.krystiankaniowski.performance.domain.usecase.dnd.SetDoNotDisturbEnabledUseCase
import pl.krystiankaniowski.performance.domain.usecase.dnd.TurnOffDoNotDisturbUseCase
import pl.krystiankaniowski.performance.domain.usecase.dnd.TurnOnDoNotDisturbUseCase

@Module
@InstallIn(SingletonComponent::class)
interface DndModule {

    @Binds
    fun TurnOnDoNotDisturbUseCaseImpl.bindTurnOnDoNotDisturbUseCase(): TurnOnDoNotDisturbUseCase

    @Binds
    fun TurnOffDoNotDisturbUseCaseImpl.bindTurnOffDoNotDisturbUseCase(): TurnOffDoNotDisturbUseCase

    @Binds
    fun IsDoNotDisturbEnabledUseCaseImpl.bindIsDoNotDisturbEnabledUseCase(): IsDoNotDisturbEnabledUseCase

    @Binds
    fun SetDoNotDisturbEnabledUseCaseImpl.bindSetDoNotDisturbEnabledUseCase(): SetDoNotDisturbEnabledUseCase
}