package pl.krystiankaniowski.performance.domain.localization.time

import pl.krystiankaniowski.performance.model.Seconds

interface TimerFormatter {

    fun format(seconds: Seconds): String = format(seconds.value)

    fun format(seconds: Long): String
}