package pl.krystiankaniowski.performance.infrastructure.usecase

import kotlinx.coroutines.flow.first
import pl.krystiankaniowski.performance.domain.repository.AppSettingsRepository
import pl.krystiankaniowski.performance.domain.usecase.dnd.IsDoNotDisturbEnabledUseCase
import javax.inject.Inject

class IsDoNotDisturbEnabledUseCaseImpl @Inject constructor(
    private val appSettingsRepository: AppSettingsRepository,
) : IsDoNotDisturbEnabledUseCase {

    override suspend fun invoke(): Boolean = appSettingsRepository.isDndEnabled.first()
}