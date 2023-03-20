package pl.krystiankaniowski.performance.timer

import kotlinx.datetime.Instant
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class TimerDatastore @Inject constructor() {
    var dateOfStart: Instant? = null
    var dateOfEnd: Instant? = null
}