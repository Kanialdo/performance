package pl.krystiankaniowski.performance.domain.stats

import pl.krystiankaniowski.performance.model.Focus

interface SaveFocusUseCase {
    suspend operator fun invoke(focus: Focus)
}