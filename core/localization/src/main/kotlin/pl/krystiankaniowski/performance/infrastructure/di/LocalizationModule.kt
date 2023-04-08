package pl.krystiankaniowski.performance.infrastructure.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import pl.krystiankaniowski.performance.domain.localization.DateTimeFormatter
import pl.krystiankaniowski.performance.domain.provider.StringsProvider
import pl.krystiankaniowski.performance.infrastructure.DateTimeFormatterImpl
import pl.krystiankaniowski.performance.infrastructure.StringsProviderImpl

@Module
@InstallIn(SingletonComponent::class)
interface LocalizationModule {

    @Binds
    fun StringsProviderImpl.bindStringsProviderImpl(): StringsProvider

    @Binds
    fun DateTimeFormatterImpl.bindDateTimeFormatterImpl(): DateTimeFormatter
}