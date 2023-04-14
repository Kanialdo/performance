package pl.krystiankaniowski.performance.vibration.usecase

import pl.krystiankaniowski.performance.domain.repository.AppSettingsRepository
import pl.krystiankaniowski.performance.vibration.PreferencesKeys
import javax.inject.Inject

class SetVibrationEnabledUseCase @Inject constructor(
    private val appSettingsRepository: AppSettingsRepository,
) {

    suspend operator fun invoke(value: Boolean) {
        appSettingsRepository.updateBoolean(PreferencesKeys.IS_VIBRATION_ENABLED, value)
    }
}