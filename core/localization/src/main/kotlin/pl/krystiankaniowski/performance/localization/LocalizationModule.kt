package pl.krystiankaniowski.performance.localization

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import pl.krystiankaniowski.performance.domain.localization.DateTimeFormatter
import pl.krystiankaniowski.performance.domain.localization.StringsProvider
import pl.krystiankaniowski.performance.localization.strings.StringsProviderImpl
import pl.krystiankaniowski.performance.localization.time.DateTimeFormatterImpl

@Module
@InstallIn(SingletonComponent::class)
interface LocalizationModule {

    @Binds
    fun StringsProviderImpl.bindStringsProviderImpl(): StringsProvider

    @Binds
    fun DateTimeFormatterImpl.bindDateTimeFormatterImpl(): DateTimeFormatter
}