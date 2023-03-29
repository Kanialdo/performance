package pl.krystiankaniowski.performance.dnd.usecase

import pl.krystiankaniowski.performance.dnd.PreferencesKeys
import pl.krystiankaniowski.performance.domain.repository.AppSettingsRepository
import javax.inject.Inject

class SetDoNotDisturbEnabledUseCaseImpl @Inject constructor(
    private val appSettingsRepository: AppSettingsRepository,
) : SetDoNotDisturbEnabledUseCase {

    override suspend fun invoke(value: Boolean) {
        appSettingsRepository.updateBoolean(PreferencesKeys.IS_DND_ENABLED, value)
    }
}