package pl.krystiankaniowski.performance.dnd

import android.app.NotificationManager
import android.content.Context
import androidx.core.app.ActivityCompat.getSystemService
import dagger.hilt.android.qualifiers.ApplicationContext
import pl.krystiankaniowski.performance.domain.usecase.dnd.TurnOnDoNotDisturbUseCase
import javax.inject.Inject

class TurnOnDoNotDisturbUseCaseImpl @Inject constructor(
    @ApplicationContext private val context: Context,
) : TurnOnDoNotDisturbUseCase {

    override operator fun invoke() {

        val notificationManager = requireNotNull(getSystemService(context, NotificationManager::class.java))
        val isGranted = notificationManager.isNotificationPolicyAccessGranted
        if (isGranted) {
            notificationManager.setInterruptionFilter(NotificationManager.INTERRUPTION_FILTER_NONE)
        }
    }
}