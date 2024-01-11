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
    fun bindStringsProviderImpl(impl: StringsProviderImpl): StringsProvider

    @Binds
    fun bindDateTimeFormatterImpl(impl: DateTimeFormatterImpl): DateTimeFormatter

    @Binds
    fun bindDurationFormatterImpl(impl: DurationFormatterImpl): DurationFormatter

    @Binds
    fun bindTimerFormatterImpl(impl: TimerFormatterImpl): TimerFormatter
}
