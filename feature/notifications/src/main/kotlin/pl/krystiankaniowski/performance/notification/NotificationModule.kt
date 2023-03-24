package pl.krystiankaniowski.performance.notification

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import dagger.multibindings.IntoSet
import pl.krystiankaniowski.performance.domain.Initializer
import pl.krystiankaniowski.performance.domain.usecase.dnd.TurnOffDoNotDisturbUseCase
import pl.krystiankaniowski.performance.domain.usecase.dnd.TurnOnDoNotDisturbUseCase
import pl.krystiankaniowski.performance.domain.usecase.notification.StartForegroundServiceUseCase
import pl.krystiankaniowski.performance.domain.usecase.notification.StopForegroundServiceUseCase

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
    fun TurnOnDoNotDisturbUseCaseImpl.bindTurnOnDoNotDisturbUseCase(): TurnOnDoNotDisturbUseCase

    @Binds
    fun TurnOffDoNotDisturbUseCaseImpl.bindTurnOffDoNotDisturbUseCase(): TurnOffDoNotDisturbUseCase
}