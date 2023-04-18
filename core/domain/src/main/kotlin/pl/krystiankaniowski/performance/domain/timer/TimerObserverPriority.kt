package pl.krystiankaniowski.performance.domain.timer

object TimerObserverPriority {

    private var autoPriority: Int = Int.MAX_VALUE
        get() {
            field -= 1
            return field
        }

    val STATISTICS = autoPriority
    val FOREGROUND_NOTIFICATION = autoPriority
    val SOUND = autoPriority
    val VIBRATION = autoPriority
    val DO_NOT_DISTURB = autoPriority
}