package pl.krystiankaniowski.performance.domain.localization.time

import kotlinx.datetime.Instant
import kotlin.time.Duration

interface DurationFormatter {

    fun format(from: Instant, to: Instant): String = format(to - from)

    fun format(duration: Duration): String
}