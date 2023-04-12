package pl.krystiankaniowski.performance.sound

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import dagger.multibindings.IntoSet
import pl.krystiankaniowski.performance.domain.settings.SettingsItemsProvider
import pl.krystiankaniowski.performance.domain.timer.TimerObserver
import pl.krystiankaniowski.performance.sound.settings.SoundSettingsProvider
import pl.krystiankaniowski.performance.sound.timer.SoundTimerObserver

@Module
@InstallIn(SingletonComponent::class)
interface SoundModule {

    @Binds
    @IntoSet
    fun SoundSettingsProvider.bindSoundSettingsProvider(): SettingsItemsProvider

    @Binds
    @IntoSet
    fun SoundTimerObserver.bindSoundTimerObserver(): TimerObserver
}