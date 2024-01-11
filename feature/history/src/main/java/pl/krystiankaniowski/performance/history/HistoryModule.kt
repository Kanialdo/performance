package pl.krystiankaniowski.performance.history

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import dagger.multibindings.IntoSet
import pl.krystiankaniowski.performance.domain.timer.TimerObserver
import pl.krystiankaniowski.performance.history.timer.HistoryTimerObserver

@Module
@InstallIn(SingletonComponent::class)
interface HistoryModule {

    @Binds
    @IntoSet
    fun bindStatsTimerObserver(impl: HistoryTimerObserver): TimerObserver
}
