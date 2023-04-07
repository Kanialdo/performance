package pl.krystiankaniowski.performance.domain.stats

interface RemoveAllDataUseCase {
    suspend operator fun invoke()
}