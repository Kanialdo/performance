package pl.krystiankaniowski.performance.domain.localization.time

import kotlinx.datetime.Instant
import kotlinx.datetime.LocalDate

interface DateTimeFormatter {

    fun formatDate(date: LocalDate): String
    fun formatDate(instant: Instant): String
    fun formatTime(instant: Instant): String
    fun formatDateTime(instant: Instant): String
}