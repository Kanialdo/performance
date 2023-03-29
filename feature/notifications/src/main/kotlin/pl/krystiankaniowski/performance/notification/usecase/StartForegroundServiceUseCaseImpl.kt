package pl.krystiankaniowski.performance.notification.usecase

import android.content.Context
import android.content.Intent
import androidx.core.content.ContextCompat
import dagger.hilt.android.qualifiers.ApplicationContext
import pl.krystiankaniowski.performance.notification.ForegroundService
import javax.inject.Inject

class StartForegroundServiceUseCaseImpl @Inject constructor(
    @ApplicationContext private val context: Context,
) : StartForegroundServiceUseCase {

    override operator fun invoke() {
        ContextCompat.startForegroundService(context, Intent(context, ForegroundService::class.java))
    }
}