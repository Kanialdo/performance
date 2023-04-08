package pl.krystiankaniowski.performance.domain.localization.time

import kotlinx.datetime.Instant
import pl.krystiankaniowski.performance.model.Seconds
import pl.krystiankaniowski.performance.model.toSeconds
import kotlin.time.Duration

interface DurationFormatter {

    fun format(from: Instant, to: Instant): String = format(to - from)

    fun format(duration: Duration): String = format(duration.toSeconds())

    fun format(duration: Seconds): String
}