package pl.krystiankaniowski.performance.domain.usecase

import pl.krystiankaniowski.performance.model.Focus

interface GetFocusListUseCase {
    suspend operator fun invoke(): List<Focus>
}