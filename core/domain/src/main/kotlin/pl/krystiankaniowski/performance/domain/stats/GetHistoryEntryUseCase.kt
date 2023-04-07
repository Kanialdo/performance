package pl.krystiankaniowski.performance.domain.stats

import pl.krystiankaniowski.performance.model.Focus

interface GetHistoryEntryUseCase {

    suspend operator fun invoke(id: Long): Focus
}