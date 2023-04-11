package pl.krystiankaniowski.performance.navigation

import pl.krystiankaniowski.performance.MainActivity
import pl.krystiankaniowski.performance.domain.navigation.Destination
import pl.krystiankaniowski.performance.domain.navigation.Navigator
import pl.krystiankaniowski.performance.notification.usecase.OpenAndroidNotificationActivityUseCase
import pl.krystiankaniowski.performance.notification.usecase.OpenNotificationPolicyAccessSettingsUseCase
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AndroidNavigator @Inject constructor(
    private val openNotificationPolicyAccessSettingsUseCase: OpenNotificationPolicyAccessSettingsUseCase,
    private val openAndroidNotificationActivityUseCase: OpenAndroidNotificationActivityUseCase,
) : Navigator {

    internal var activity: MainActivity? = null

    override fun open(destination: Destination) {
        activity?.let { activity ->
            when (destination) {
                Destination.Android.NotificationPolicyAccessSettings -> openNotificationPolicyAccessSettingsUseCase(activity)
                Destination.Android.AppNotificationsSettings -> openAndroidNotificationActivityUseCase(activity)
            }
        }
    }
}