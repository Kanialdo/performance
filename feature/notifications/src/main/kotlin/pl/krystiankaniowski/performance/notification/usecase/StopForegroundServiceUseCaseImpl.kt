package pl.krystiankaniowski.performance.notification.usecase

import android.content.Context
import android.content.Intent
import dagger.hilt.android.qualifiers.ApplicationContext
import pl.krystiankaniowski.performance.notification.ForegroundService
import javax.inject.Inject

class StopForegroundServiceUseCaseImpl @Inject constructor(
    @ApplicationContext private val context: Context,
) : StopForegroundServiceUseCase {

    override operator fun invoke() {
        context.stopService(Intent(context, ForegroundService::class.java))
    }
}