package pl.krystiankaniowski.performance.infrastructure.usecase

import pl.krystiankaniowski.performance.domain.usecase.IsDoNotDisturbEnabledUseCase
import javax.inject.Inject
import kotlin.random.Random

class IsDoNotDisturbEnabledUseCaseImpl @Inject constructor() : IsDoNotDisturbEnabledUseCase {
    override suspend fun invoke(): Boolean {
        // TODO("Not yet implemented")
        return Random(System.currentTimeMillis()).nextBoolean()
    }
}