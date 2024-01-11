package pl.krystiankaniowski.performance.timer

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import pl.krystiankaniowski.performance.domain.timer.GetCancelThresholdUseCase
import pl.krystiankaniowski.performance.domain.timer.PerformanceTimer
import pl.krystiankaniowski.performance.timer.usecase.GetCancelThresholdUseCaseImpl

@Module
@InstallIn(SingletonComponent::class)
interface TimerModule {

    @Binds
    fun bindGetCancelThresholdUseCaseImpl(impl: GetCancelThresholdUseCaseImpl): GetCancelThresholdUseCase

    @Binds
    fun bindPerformanceTimerImpl(impl: PerformanceTimerImpl): PerformanceTimer
}
