package pl.krystiankaniowski.performance.notification

import android.app.Notification
import android.app.NotificationManager
import android.app.PendingIntent
import android.app.Service
import android.content.Intent
import androidx.core.app.NotificationCompat
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import pl.krystiankaniowski.performance.domain.localization.time.TimerFormatter
import pl.krystiankaniowski.performance.domain.timer.PerformanceTimer
import pl.krystiankaniowski.performance.notification.usecase.IsShowTimeEnabledUseCase
import pl.krystiankaniowski.performance.notifications.R
import javax.inject.Inject

@AndroidEntryPoint
class ForegroundService : Service() {

    private val serviceJob = Job()
    private val serviceScope = CoroutineScope(Dispatchers.Main + serviceJob)

    @Inject
    lateinit var timer: PerformanceTimer

    @Inject
    lateinit var notificationManager: NotificationManager

    @Inject
    lateinit var timerFormatter: TimerFormatter

    @Inject
    lateinit var isShowTimeEnabledUseCase: IsShowTimeEnabledUseCase

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {

        startForeground(Constants.ONGOING_NOTIFICATION_ID, buildNotification(contentMessage = getString(R.string.notification_foreground_description)))

        serviceScope.launch {
            if (isShowTimeEnabledUseCase()) {
                timer.state.collect { state ->
                    notificationManager.notify(
                        Constants.ONGOING_NOTIFICATION_ID,
                        buildNotification(
                            contentMessage = when (state) {
                                PerformanceTimer.State.NotStarted -> getString(R.string.notification_timer_description_not_started)
                                is PerformanceTimer.State.Pending -> getString(R.string.notification_timer_description_time_left, timerFormatter.format(state.leftSeconds))
                            },
                        ),
                    )
                }
            }
        }

        return START_STICKY
    }

    private fun buildNotification(contentMessage: String): Notification {
        val mainActivityName = "pl.krystiankaniowski.performance.MainActivity"
        val pendingIntent: PendingIntent = PendingIntent.getActivity(
            /* context = */ this,
            /* requestCode = */ 0,
            /* intent = */ Intent(this, Class.forName(mainActivityName)),
            /* flags = */ PendingIntent.FLAG_IMMUTABLE,
        )

        return NotificationCompat.Builder(this, Constants.CHANNEL_TIMER)
            .setContentTitle(getString(R.string.notification_foreground_title))
            .setContentText(contentMessage)
            .setSmallIcon(R.drawable.ic_notification)
            .setContentIntent(pendingIntent)
            .setForegroundServiceBehavior(NotificationCompat.FOREGROUND_SERVICE_IMMEDIATE)
            .setPriority(NotificationCompat.PRIORITY_MIN)
            .setOngoing(true)
            .build()
    }

    override fun onDestroy() {
        super.onDestroy()
        serviceJob.cancel()
    }

    override fun onBind(p0: Intent?) = null
}