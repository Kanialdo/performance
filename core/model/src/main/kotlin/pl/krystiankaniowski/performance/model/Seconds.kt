package pl.krystiankaniowski.performance.model

import kotlin.time.Duration
import kotlin.time.Duration.Companion.seconds

@JvmInline
value class Seconds(val value: Long) {

    val asDuration: Duration
        get() = value.seconds

    operator fun plus(other: Seconds) = Seconds(value + other.value)

    operator fun minus(other: Seconds) = Seconds(value - other.value)

    operator fun compareTo(other: Seconds) = value.compareTo(other.value)
}

fun Duration.toSeconds() = Seconds(this.inWholeSeconds)

fun Int.toSeconds() = Seconds(this.toLong())

fun Long.toSeconds() = Seconds(this)