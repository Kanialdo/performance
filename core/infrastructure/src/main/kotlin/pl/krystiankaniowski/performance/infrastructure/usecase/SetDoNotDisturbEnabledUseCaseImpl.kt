package pl.krystiankaniowski.performance.infrastructure.usecase

import pl.krystiankaniowski.performance.domain.repository.AppSettingsRepository
import pl.krystiankaniowski.performance.domain.usecase.dnd.SetDoNotDisturbEnabledUseCase
import javax.inject.Inject

class SetDoNotDisturbEnabledUseCaseImpl @Inject constructor(
    private val appSettingsRepository: AppSettingsRepository,
) : SetDoNotDisturbEnabledUseCase {

    override suspend fun invoke(value: Boolean) {
        appSettingsRepository.updateIsDndEnabled(value)
    }
}