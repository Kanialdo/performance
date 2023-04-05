package pl.krystiankaniowski.performance.testing

import kotlinx.datetime.Clock
import kotlinx.datetime.Instant

class VirtualClock(var fixedInstant: Instant) : Clock {

    override fun now(): Instant = fixedInstant
}

@Suppress("FunctionNaming", "FunctionName")
fun Clock.Companion.Virtual(fixedInstant: Instant): VirtualClock = VirtualClock(fixedInstant)