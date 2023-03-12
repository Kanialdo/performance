package pl.krystiankaniowski.performance.model

import kotlinx.datetime.Instant

data class Focus(
    val startDate: Instant,
    val endDate: Instant,
)