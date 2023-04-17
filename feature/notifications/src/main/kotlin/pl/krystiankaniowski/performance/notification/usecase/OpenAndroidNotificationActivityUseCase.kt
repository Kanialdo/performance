package pl.krystiankaniowski.performance.notification.usecase

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.provider.Settings
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class OpenAndroidNotificationActivityUseCase @Inject constructor(
    @ApplicationContext private val context: Context,
) {

    operator fun invoke(activity: Activity) {
        val intent = Intent()
        intent.action = Settings.ACTION_APP_NOTIFICATION_SETTINGS
        intent.putExtra(Settings.EXTRA_APP_PACKAGE, context.packageName)
        activity.startActivity(intent)
    }
}