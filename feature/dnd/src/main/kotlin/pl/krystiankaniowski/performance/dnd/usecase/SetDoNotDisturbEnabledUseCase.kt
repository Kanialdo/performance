package pl.krystiankaniowski.performance.dnd.usecase

interface SetDoNotDisturbEnabledUseCase {
    suspend operator fun invoke(value: Boolean)
}