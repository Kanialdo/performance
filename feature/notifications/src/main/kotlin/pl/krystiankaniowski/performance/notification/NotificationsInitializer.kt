package pl.krystiankaniowski.performance.notification

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import androidx.core.content.getSystemService
import dagger.hilt.android.qualifiers.ApplicationContext
import pl.krystiankaniowski.performance.domain.Initializer
import pl.krystiankaniowski.performance.notifications.R
import javax.inject.Inject

class NotificationsInitializer @Inject constructor(
    @ApplicationContext private val context: Context,
) : Initializer {

    override fun init() {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                Constants.CHANNEL_TIMER,
                context.getString(R.string.channel_name),
                NotificationManager.IMPORTANCE_MIN,
            ).apply {
                description = context.getString(R.string.channel_description)
                setShowBadge(false)
            }

            val notificationManager = requireNotNull(context.getSystemService<NotificationManager>())
            notificationManager.createNotificationChannel(channel)
        }
    }
}