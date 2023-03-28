package pl.krystiankaniowski.performance.notification

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import dagger.multibindings.IntoSet
import pl.krystiankaniowski.performance.domain.Initializer
import pl.krystiankaniowski.performance.domain.timer.TimerObserver
import pl.krystiankaniowski.performance.domain.usecase.notification.StartForegroundServiceUseCase
import pl.krystiankaniowski.performance.domain.usecase.notification.StopForegroundServiceUseCase
import pl.krystiankaniowski.performance.notification.timer.NotificationTimerObserver

@Module
@InstallIn(SingletonComponent::class)
interface NotificationModule {

    @Binds
    @IntoSet
    fun NotificationsInitializer.bindNotificationsInitializer(): Initializer

    @Binds
    fun StartForegroundServiceUseCaseImpl.bindStartForegroundServiceUseCase(): StartForegroundServiceUseCase

    @Binds
    fun StopForegroundServiceUseCaseImpl.bindStopForegroundServiceUseCase(): StopForegroundServiceUseCase

    @Binds
    @IntoSet
    fun NotificationTimerObserver.bindNotificationTimerObserver(): TimerObserver
}