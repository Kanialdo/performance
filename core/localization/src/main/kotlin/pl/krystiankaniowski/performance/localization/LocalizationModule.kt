package pl.krystiankaniowski.performance.localization

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import pl.krystiankaniowski.performance.domain.localization.strings.StringsProvider
import pl.krystiankaniowski.performance.domain.localization.time.DateTimeFormatter
import pl.krystiankaniowski.performance.domain.localization.time.DurationFormatter
import pl.krystiankaniowski.performance.domain.localization.time.TimerFormatter
import pl.krystiankaniowski.performance.localization.strings.StringsProviderImpl
import pl.krystiankaniowski.performance.localization.time.DateTimeFormatterImpl
import pl.krystiankaniowski.performance.localization.time.DurationFormatterImpl
import pl.krystiankaniowski.performance.localization.time.TimerFormatterImpl

@Module
@InstallIn(SingletonComponent::class)
interface LocalizationModule {

    @Binds
    fun StringsProviderImpl.bindStringsProviderImpl(): StringsProvider

    @Binds
    fun DateTimeFormatterImpl.bindDateTimeFormatterImpl(): DateTimeFormatter

    @Binds
    fun DurationFormatterImpl.bindDurationFormatterImpl(): DurationFormatter

    @Binds
    fun TimerFormatterImpl.bindTimerFormatterImpl(): TimerFormatter
}