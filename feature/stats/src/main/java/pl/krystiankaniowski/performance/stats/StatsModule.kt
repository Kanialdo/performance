package pl.krystiankaniowski.performance.stats

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import dagger.multibindings.IntoSet
import kotlinx.datetime.Clock
import pl.krystiankaniowski.performance.domain.timer.TimerObserver
import pl.krystiankaniowski.performance.stats.timer.StatsTimerObserver

@Module
@InstallIn(SingletonComponent::class)
interface StatsModule {

    @Binds
    @IntoSet
    fun StatsTimerObserver.bindStatsTimerObserver(): TimerObserver

    @Binds
    fun Clock.System.bindClock(): Clock
}