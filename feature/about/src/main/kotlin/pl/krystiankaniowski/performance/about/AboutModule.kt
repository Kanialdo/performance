package pl.krystiankaniowski.performance.about

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import dagger.multibindings.IntoSet
import pl.krystiankaniowski.performance.about.settings.AboutSettingsProvider
import pl.krystiankaniowski.performance.domain.settings.SettingsItemsProvider

@Module
@InstallIn(SingletonComponent::class)
interface AboutModule {

    @Binds
    @IntoSet
    fun bindDndSettingsProvider(impl: AboutSettingsProvider): SettingsItemsProvider
}
