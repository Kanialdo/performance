package pl.krystiankaniowski.performance.model

import kotlin.time.Duration.Companion.seconds

@JvmInline
value class Seconds(val value: Long) {
    val asDuration: kotlin.time.Duration
        get() = value.seconds
}