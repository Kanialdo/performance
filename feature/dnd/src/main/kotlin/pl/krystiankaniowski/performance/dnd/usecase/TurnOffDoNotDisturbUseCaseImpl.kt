package pl.krystiankaniowski.performance.dnd.usecase

import android.app.NotificationManager
import android.content.Context
import androidx.core.app.ActivityCompat.getSystemService
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class TurnOffDoNotDisturbUseCaseImpl @Inject constructor(
    @ApplicationContext private val context: Context,
) : TurnOffDoNotDisturbUseCase {

    override operator fun invoke() {
        val notificationManager = requireNotNull(getSystemService(context, NotificationManager::class.java))
        val isGranted = notificationManager.isNotificationPolicyAccessGranted
        if (isGranted) {
            notificationManager.setInterruptionFilter(NotificationManager.INTERRUPTION_FILTER_ALL)
        }
    }
}