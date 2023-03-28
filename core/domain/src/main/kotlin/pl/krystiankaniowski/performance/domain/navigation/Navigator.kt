package pl.krystiankaniowski.performance.domain.navigation

interface Navigator {

    fun open(destination: Destination)
}

sealed interface Destination {
    sealed interface Android : Destination {
        object AppNotificationsSettings : Android
        object NotificationPolicyAccessSettings : Android
    }
}