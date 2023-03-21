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
            // Create the NotificationChannel.
            val name = context.getString(R.string.channel_name)
            val descriptionText = context.getString(R.string.channel_description)
            val importance = NotificationManager.IMPORTANCE_HIGH
            val mChannel = NotificationChannel(Constants.CHANNEL_TIMER, name, importance)
            mChannel.description = descriptionText
            // Register the channel with the system. You can't change the importance
            // or other notification behaviors after this.
            val notificationManager = requireNotNull(context.getSystemService<NotificationManager>())
            notificationManager.createNotificationChannel(mChannel)
        }
    }
}