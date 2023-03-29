package pl.krystiankaniowski.performance.timer

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import pl.krystiankaniowski.performance.domain.timer.PerformanceTimer

@Module
@InstallIn(SingletonComponent::class)
interface TimerModule {

    @Binds
    fun PerformanceTimerImpl.bindPerformanceTimerImpl(): PerformanceTimer
}