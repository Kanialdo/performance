package pl.krystiankaniowski.performance.notification

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import dagger.multibindings.IntoSet
import pl.krystiankaniowski.performance.domain.Initializer

@Module
@InstallIn(SingletonComponent::class)
interface NotificationModule {

    @Binds
    @IntoSet
    fun NotificationsInitializer.bindNotificationsInitializer(): Initializer
}