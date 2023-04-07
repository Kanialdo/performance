package pl.krystiankaniowski.performance.domain.stats

import pl.krystiankaniowski.performance.model.Focus

interface GetFocusListUseCase {
    suspend operator fun invoke(): List<Focus>
}