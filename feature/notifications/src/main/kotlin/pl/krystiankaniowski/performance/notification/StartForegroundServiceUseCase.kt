package pl.krystiankaniowski.performance.notification

import android.content.Context
import android.content.Intent
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class StartForegroundServiceUseCase @Inject constructor(
    @ApplicationContext private val context: Context,
) {

    operator fun invoke() {
        Intent(context, ForegroundService::class.java).also { intent ->
            context.startService(intent)
        }
    }
}