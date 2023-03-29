package pl.krystiankaniowski.performance.domain.timer

object TimerObserverPriority {

    private var autoPriority: Int = Int.MAX_VALUE
        get() {
            field -= 1
            return field
        }

    val STATISTICS = autoPriority
    val FOREGROUND_NOTIFICATION = autoPriority
    val DO_NOT_DISTURB = autoPriority
}