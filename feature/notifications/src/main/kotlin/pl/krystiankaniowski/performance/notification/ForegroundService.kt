package pl.krystiankaniowski.performance.notification

import android.app.Notification
import android.app.PendingIntent
import android.app.Service
import android.content.Intent
import androidx.core.app.NotificationCompat
import dagger.hilt.android.AndroidEntryPoint
import pl.krystiankaniowski.performance.notifications.R

@AndroidEntryPoint
class ForegroundService : Service() {

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {

        val pendingIntent: PendingIntent =
            Intent(this, Class.forName("pl.krystiankaniowski.performance.MainActivity")).let { notificationIntent ->
                PendingIntent.getActivity(
                    this, 0, notificationIntent,
                    PendingIntent.FLAG_IMMUTABLE,
                )
            }

        val notification: Notification = NotificationCompat.Builder(this, Constants.CHANNEL_TIMER)
            .setContentTitle("Notification")
            .setContentText("Timer")
            .setSmallIcon(R.drawable.ic_stat_name)
            .setContentIntent(pendingIntent)
            .setTicker("TypedArrayUtils.getText(R.string.ticker_text)")
            .setForegroundServiceBehavior(NotificationCompat.FOREGROUND_SERVICE_IMMEDIATE)
            .build()

        // Notification ID cannot be 0.
        startForeground(Constants.ONGOING_NOTIFICATION_ID, notification)

        return START_STICKY;
    }

    override fun onBind(p0: Intent?) = null
}