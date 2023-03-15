package pl.krystiankaniowski.performance.domain.usecase

interface SetDoNotDisturbEnabledUseCase {
    suspend operator fun invoke(value: Boolean)
}