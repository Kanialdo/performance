package pl.krystiankaniowski.performance.infrastructure.usecase

import pl.krystiankaniowski.performance.domain.usecase.SetDoNotDisturbEnabledUseCase
import javax.inject.Inject

class SetDoNotDisturbEnabledUseCaseImpl @Inject constructor() : SetDoNotDisturbEnabledUseCase {
    override suspend fun invoke(value: Boolean) {
        // TODO("Not yet implemented")
    }
}