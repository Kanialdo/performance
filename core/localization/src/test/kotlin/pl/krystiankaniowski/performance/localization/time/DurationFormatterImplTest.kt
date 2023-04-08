package pl.krystiankaniowski.performance.localization.time

import kotlinx.datetime.Clock
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import kotlin.time.Duration.Companion.hours
import kotlin.time.Duration.Companion.minutes
import kotlin.time.Duration.Companion.seconds

class DurationFormatterImplTest {

    @Test
    fun test() {

        val durationTimeFormatter = DurationFormatterImpl()

        val baseDate = Clock.System.now()

        Assertions.assertEquals("1s", durationTimeFormatter.format(baseDate.minus(1.seconds), baseDate))
        Assertions.assertEquals("59s", durationTimeFormatter.format(baseDate.minus(59.seconds), baseDate))
        Assertions.assertEquals("1m", durationTimeFormatter.format(baseDate.minus(1.minutes), baseDate))
        Assertions.assertEquals("4m 59s", durationTimeFormatter.format(baseDate.minus(59.seconds).minus(4.minutes), baseDate))
        Assertions.assertEquals("5m", durationTimeFormatter.format(baseDate.minus(5.minutes), baseDate))
        Assertions.assertEquals("59m", durationTimeFormatter.format(baseDate.minus(59.minutes), baseDate))
        Assertions.assertEquals("1h", durationTimeFormatter.format(baseDate.minus(1.hours), baseDate))
        Assertions.assertEquals("1h 1m", durationTimeFormatter.format(baseDate.minus(1.hours).minus(1.minutes), baseDate))
    }
}