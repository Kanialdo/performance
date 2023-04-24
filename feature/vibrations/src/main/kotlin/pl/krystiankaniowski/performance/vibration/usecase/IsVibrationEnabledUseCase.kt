package pl.krystiankaniowski.performance.vibration.usecase

import kotlinx.coroutines.flow.first
import pl.krystiankaniowski.performance.domain.repository.AppSettingsRepository
import pl.krystiankaniowski.performance.vibration.PreferencesKeys
import javax.inject.Inject

class IsVibrationEnabledUseCase @Inject constructor(
    private val appSettingsRepository: AppSettingsRepository,
) {

    suspend operator fun invoke(): Boolean = appSettingsRepository.getBoolean(PreferencesKeys.IS_VIBRATION_ENABLED).first()
}