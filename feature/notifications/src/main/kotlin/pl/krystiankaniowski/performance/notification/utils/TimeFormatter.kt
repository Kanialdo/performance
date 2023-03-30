package pl.krystiankaniowski.performance.notification.utils

import pl.krystiankaniowski.performance.model.Seconds
import javax.inject.Inject

class TimeFormatter @Inject constructor() {

    fun format(seconds: Seconds): String = seconds.value.toString()
}