package pl.krystiankaniowski.performance.localization.time

import pl.krystiankaniowski.performance.domain.localization.time.TimerFormatter
import pl.krystiankaniowski.performance.model.Seconds
import javax.inject.Inject

class TimerFormatterImpl @Inject constructor() : TimerFormatter {

    override fun format(seconds: Seconds): String {
        val value = seconds.value
        return "${value / 60}:${(value % 60).toString().padStart(2, '0')}"
    }
}