package pl.krystiankaniowski.performance.stats

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import dagger.multibindings.IntoSet
import pl.krystiankaniowski.performance.domain.settings.SettingsItemsProvider
import pl.krystiankaniowski.performance.stats.settings.DebugStatsSettingsProvider

@Module
@InstallIn(SingletonComponent::class)
interface DebugStatsModule {

    @Binds
    @IntoSet
    fun DebugStatsSettingsProvider.bindDebugStatsSettingsProvider(): SettingsItemsProvider
}