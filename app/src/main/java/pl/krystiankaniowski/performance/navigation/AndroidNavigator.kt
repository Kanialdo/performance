package pl.krystiankaniowski.performance.navigation

import android.content.Context
import android.content.Intent
import android.os.Build
import android.provider.Settings
import dagger.hilt.android.qualifiers.ApplicationContext
import pl.krystiankaniowski.performance.MainActivity
import pl.krystiankaniowski.performance.domain.navigation.Destination
import pl.krystiankaniowski.performance.domain.navigation.Navigator
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AndroidNavigator @Inject constructor(@ApplicationContext private val context: Context) : Navigator {

    internal var activity: MainActivity? = null

    override fun open(destination: Destination) {
        when (destination) {
            Destination.Android.NotificationPolicyAccessSettings -> openNotificationPolicyAccessSettings()
            Destination.Android.AppNotificationsSettings -> openAppNotificationsSettings()
        }
    }

    private fun openAppNotificationsSettings() {
        val intent = Intent()
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            intent.action = Settings.ACTION_APP_NOTIFICATION_SETTINGS
            intent.putExtra(Settings.EXTRA_APP_PACKAGE, context.packageName)
        } else {
            intent.action = "android.settings.APP_NOTIFICATION_SETTINGS"
            intent.putExtra("app_package", context.packageName)
            intent.putExtra("app_uid", context.applicationInfo.uid)
        }
        activity?.startActivity(intent)
    }

    private fun openNotificationPolicyAccessSettings() {
        activity?.startActivity(Intent(Settings.ACTION_NOTIFICATION_POLICY_ACCESS_SETTINGS))
    }
}