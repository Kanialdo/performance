package pl.krystiankaniowski.performance.localization.time

import pl.krystiankaniowski.performance.domain.localization.time.TimerFormatter
import javax.inject.Inject

class TimerFormatterImpl @Inject constructor() : TimerFormatter {

    override fun format(seconds: Long) = "${seconds / 60}:${(seconds % 60).toString().padStart(2, '0')}"
}