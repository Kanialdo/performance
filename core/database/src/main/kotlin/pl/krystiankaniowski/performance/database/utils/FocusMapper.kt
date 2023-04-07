package pl.krystiankaniowski.performance.database.utils

import pl.krystiankaniowski.performance.database.model.FocusEntity
import pl.krystiankaniowski.performance.model.Focus

internal fun FocusEntity.toDomain() = Focus(
    id = this.uid.toLong(),
    startDate = this.dateStart,
    endDate = this.dateEnd,
)

internal fun List<FocusEntity>.toDomain() = this.map { it.toDomain() }