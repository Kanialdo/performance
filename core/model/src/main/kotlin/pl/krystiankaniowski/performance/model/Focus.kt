package pl.krystiankaniowski.performance.model

import kotlinx.datetime.Instant

data class Focus(
    val id: Long = -1,
    val startDate: Instant,
    val endDate: Instant,
) {
    val duration: Seconds by lazy { (endDate - startDate).toSeconds() }
}