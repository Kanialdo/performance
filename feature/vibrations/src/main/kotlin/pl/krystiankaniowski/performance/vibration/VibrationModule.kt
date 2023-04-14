package pl.krystiankaniowski.performance.vibration

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import dagger.multibindings.IntoSet
import pl.krystiankaniowski.performance.domain.settings.SettingsItemsProvider
import pl.krystiankaniowski.performance.domain.timer.TimerObserver
import pl.krystiankaniowski.performance.vibration.settings.VibrationSettingsProvider
import pl.krystiankaniowski.performance.vibration.timer.VibrationTimerObserver

@Module
@InstallIn(SingletonComponent::class)
interface VibrationModule {

    @Binds
    @IntoSet
    fun VibrationSettingsProvider.bindVibrationSettingsProvider(): SettingsItemsProvider

    @Binds
    @IntoSet
    fun VibrationTimerObserver.bindVibrationTimerObserver(): TimerObserver
}