package pl.krystiankaniowski.performance.account

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import dagger.multibindings.IntoSet
import pl.krystiankaniowski.performance.account.settings.AccountSettingsProvider
import pl.krystiankaniowski.performance.domain.settings.SettingsItemsProvider

@Module
@InstallIn(SingletonComponent::class)
interface AccountModule {

    @Binds
    @IntoSet
    fun AccountSettingsProvider.bindAccountSettingsProvider(): SettingsItemsProvider
}