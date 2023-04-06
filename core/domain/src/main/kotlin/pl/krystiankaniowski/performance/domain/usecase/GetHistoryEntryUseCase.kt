package pl.krystiankaniowski.performance.domain.usecase

import pl.krystiankaniowski.performance.model.Focus

interface GetHistoryEntryUseCase {

    suspend operator fun invoke(id: Long): Focus
}