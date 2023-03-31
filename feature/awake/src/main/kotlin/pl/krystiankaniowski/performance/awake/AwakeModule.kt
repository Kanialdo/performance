package pl.krystiankaniowski.performance.awake

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import dagger.multibindings.IntoSet
import pl.krystiankaniowski.performance.awake.settings.AwakeSettingsProvider
import pl.krystiankaniowski.performance.domain.settings.SettingsItemsProvider

@Module
@InstallIn(SingletonComponent::class)
interface AwakeModule {

    @Binds
    @IntoSet
    fun AwakeSettingsProvider.bindAwakeSettingsProvider(): SettingsItemsProvider
}