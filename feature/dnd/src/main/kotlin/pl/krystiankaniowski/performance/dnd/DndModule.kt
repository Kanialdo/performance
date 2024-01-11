package pl.krystiankaniowski.performance.dnd

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import dagger.multibindings.IntoSet
import pl.krystiankaniowski.performance.dnd.settings.DndSettingsProvider
import pl.krystiankaniowski.performance.dnd.timer.DndTimerObserver
import pl.krystiankaniowski.performance.dnd.usecase.IsDoNotDisturbEnabledUseCase
import pl.krystiankaniowski.performance.dnd.usecase.IsDoNotDisturbEnabledUseCaseImpl
import pl.krystiankaniowski.performance.dnd.usecase.SetDoNotDisturbEnabledUseCase
import pl.krystiankaniowski.performance.dnd.usecase.SetDoNotDisturbEnabledUseCaseImpl
import pl.krystiankaniowski.performance.dnd.usecase.TurnOffDoNotDisturbUseCase
import pl.krystiankaniowski.performance.dnd.usecase.TurnOffDoNotDisturbUseCaseImpl
import pl.krystiankaniowski.performance.dnd.usecase.TurnOnDoNotDisturbUseCase
import pl.krystiankaniowski.performance.dnd.usecase.TurnOnDoNotDisturbUseCaseImpl
import pl.krystiankaniowski.performance.domain.settings.SettingsItemsProvider
import pl.krystiankaniowski.performance.domain.timer.TimerObserver

@Module
@InstallIn(SingletonComponent::class)
interface DndModule {

    @Binds
    fun bindTurnOnDoNotDisturbUseCase(impl: TurnOnDoNotDisturbUseCaseImpl): TurnOnDoNotDisturbUseCase

    @Binds
    fun bindTurnOffDoNotDisturbUseCase(impl: TurnOffDoNotDisturbUseCaseImpl): TurnOffDoNotDisturbUseCase

    @Binds
    fun bindIsDoNotDisturbEnabledUseCase(impl: IsDoNotDisturbEnabledUseCaseImpl): IsDoNotDisturbEnabledUseCase

    @Binds
    fun bindSetDoNotDisturbEnabledUseCase(impl: SetDoNotDisturbEnabledUseCaseImpl): SetDoNotDisturbEnabledUseCase

    @Binds
    @IntoSet
    fun bindDndSettingsProvider(impl: DndSettingsProvider): SettingsItemsProvider

    @Binds
    @IntoSet
    fun bindDndTimerObserver(impl: DndTimerObserver): TimerObserver
}
