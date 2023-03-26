package pl.krystiankaniowski.performance.domain.usecase.dnd

interface SetDoNotDisturbEnabledUseCase {
    suspend operator fun invoke(value: Boolean)
}