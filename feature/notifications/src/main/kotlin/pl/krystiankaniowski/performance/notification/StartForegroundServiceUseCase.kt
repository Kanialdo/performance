package pl.krystiankaniowski.performance.notification

import android.content.Context
import android.content.Intent
import dagger.hilt.android.qualifiers.ApplicationContext
import pl.krystiankaniowski.performance.domain.usecase.notification.StartForegroundServiceUseCase
import javax.inject.Inject

class StartForegroundServiceUseCaseImpl @Inject constructor(
    @ApplicationContext private val context: Context,
) : StartForegroundServiceUseCase {

    override operator fun invoke() {
        Intent(context, ForegroundService::class.java).also { intent ->
            context.startService(intent)
        }
    }
}