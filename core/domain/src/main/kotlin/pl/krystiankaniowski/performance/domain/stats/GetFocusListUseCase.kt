package pl.krystiankaniowski.performance.domain.stats

import kotlinx.coroutines.flow.Flow
import pl.krystiankaniowski.performance.model.Focus

interface GetFocusListUseCase {
    suspend operator fun invoke(): Flow<List<Focus>>
}