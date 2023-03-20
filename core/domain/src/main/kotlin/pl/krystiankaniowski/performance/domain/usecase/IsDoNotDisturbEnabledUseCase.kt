package pl.krystiankaniowski.performance.domain.usecase

interface IsDoNotDisturbEnabledUseCase {
    suspend operator fun invoke(): Boolean
}