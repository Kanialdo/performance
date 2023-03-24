package pl.krystiankaniowski.performance.dnd

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import pl.krystiankaniowski.performance.domain.usecase.dnd.TurnOffDoNotDisturbUseCase
import pl.krystiankaniowski.performance.domain.usecase.dnd.TurnOnDoNotDisturbUseCase

@Module
@InstallIn(SingletonComponent::class)
interface DndModule {

    @Binds
    fun TurnOnDoNotDisturbUseCaseImpl.bindTurnOnDoNotDisturbUseCase(): TurnOnDoNotDisturbUseCase

    @Binds
    fun TurnOffDoNotDisturbUseCaseImpl.bindTurnOffDoNotDisturbUseCase(): TurnOffDoNotDisturbUseCase
}