package pl.krystiankaniowski.performance.notification

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import dagger.hilt.android.qualifiers.ApplicationContext
import pl.krystiankaniowski.performance.domain.Initializer
import pl.krystiankaniowski.performance.notifications.R
import javax.inject.Inject

class NotificationsInitializer @Inject constructor(
    @ApplicationContext private val context: Context,
    private val notificationManager: NotificationManager,
) : Initializer {

    override fun init() {
        val channel = NotificationChannel(
            Constants.CHANNEL_TIMER,
            context.getString(R.string.channel_name),
            NotificationManager.IMPORTANCE_MIN,
        ).apply {
            description = context.getString(R.string.channel_description)
            setShowBadge(false)
        }
        notificationManager.createNotificationChannel(channel)
    }
}