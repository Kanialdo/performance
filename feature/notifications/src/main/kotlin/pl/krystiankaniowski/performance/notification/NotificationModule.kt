package pl.krystiankaniowski.performance.notification

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import dagger.multibindings.IntoSet
import pl.krystiankaniowski.performance.domain.Initializer
import pl.krystiankaniowski.performance.domain.settings.SettingsItemsProvider
import pl.krystiankaniowski.performance.domain.timer.TimerObserver
import pl.krystiankaniowski.performance.notification.settings.NotificationSettingsProvider
import pl.krystiankaniowski.performance.notification.timer.NotificationTimerObserver
import pl.krystiankaniowski.performance.notification.usecase.StartForegroundServiceUseCase
import pl.krystiankaniowski.performance.notification.usecase.StartForegroundServiceUseCaseImpl
import pl.krystiankaniowski.performance.notification.usecase.StopForegroundServiceUseCase
import pl.krystiankaniowski.performance.notification.usecase.StopForegroundServiceUseCaseImpl

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
    fun NotificationSettingsProvider.bindNotificationSettingsProvider(): SettingsItemsProvider

    @Binds
    @IntoSet
    fun NotificationTimerObserver.bindNotificationTimerObserver(): TimerObserver
}