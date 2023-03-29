package pl.krystiankaniowski.performance.dnd.usecase

interface IsDoNotDisturbEnabledUseCase {
    suspend operator fun invoke(): Boolean
}