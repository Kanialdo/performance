package pl.krystiankaniowski.performance.notification

import android.app.NotificationManager
import android.content.Context
import androidx.core.content.getSystemService
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import dagger.multibindings.IntoSet
import pl.krystiankaniowski.performance.domain.Initializer
import pl.krystiankaniowski.performance.domain.settings.SettingsItemsProvider
import pl.krystiankaniowski.performance.domain.timer.TimerObserver
import pl.krystiankaniowski.performance.notification.settings.NotificationSettingsProvider
import pl.krystiankaniowski.performance.notification.timer.NotificationTimerObserver

@Module
@InstallIn(SingletonComponent::class)
object NotificationModule {

    @Provides
    fun provideNotificationManager(@ApplicationContext context: Context): NotificationManager = requireNotNull(context.getSystemService())

    @Module
    @InstallIn(SingletonComponent::class)
    interface BindingModule {

        @Binds
        @IntoSet
        fun bindNotificationsInitializer(impl: NotificationsInitializer): Initializer

        @Binds
        @IntoSet
        fun bindNotificationSettingsProvider(impl: NotificationSettingsProvider): SettingsItemsProvider

        @Binds
        @IntoSet
        fun bindNotificationTimerObserver(impl: NotificationTimerObserver): TimerObserver
    }
}
