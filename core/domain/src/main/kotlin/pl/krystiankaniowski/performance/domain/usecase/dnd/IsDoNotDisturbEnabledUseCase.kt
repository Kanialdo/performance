package pl.krystiankaniowski.performance.domain.usecase.dnd

interface IsDoNotDisturbEnabledUseCase {
    suspend operator fun invoke(): Boolean
}