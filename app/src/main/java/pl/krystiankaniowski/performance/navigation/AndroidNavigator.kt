package pl.krystiankaniowski.performance.navigation

import android.content.Intent
import android.provider.Settings
import pl.krystiankaniowski.performance.MainActivity
import pl.krystiankaniowski.performance.domain.navigation.Destination
import pl.krystiankaniowski.performance.domain.navigation.Navigator
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AndroidNavigator @Inject constructor() : Navigator {

    internal var activity: MainActivity? = null

    override fun open(destination: Destination) {
        when (destination) {
            is Destination.Android.NotificationPolicyAccessSettings -> {
                activity?.startActivity(Intent(Settings.ACTION_NOTIFICATION_POLICY_ACCESS_SETTINGS))
            }
        }
    }
}