package pl.krystiankaniowski.performance.notification.usecase

import android.app.Activity
import android.content.Intent
import android.provider.Settings
import javax.inject.Inject

class OpenNotificationPolicyAccessSettingsUseCase @Inject constructor() {

    operator fun invoke(activity: Activity) {
        activity.startActivity(Intent(Settings.ACTION_NOTIFICATION_POLICY_ACCESS_SETTINGS))
    }
}