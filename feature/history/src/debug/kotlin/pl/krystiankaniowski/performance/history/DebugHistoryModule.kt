package pl.krystiankaniowski.performance.history

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import dagger.multibindings.IntoSet
import pl.krystiankaniowski.performance.domain.settings.SettingsItemsProvider
import pl.krystiankaniowski.performance.history.settings.DebugHistorySettingsProvider

@Module
@InstallIn(SingletonComponent::class)
interface DebugHistoryModule {

    @Binds
    @IntoSet
    fun bindDebugStatsSettingsProvider(impl: DebugHistorySettingsProvider): SettingsItemsProvider
}
