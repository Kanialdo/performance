package pl.krystiankaniowski.performance.notification.usecase

import pl.krystiankaniowski.performance.domain.repository.AppSettingsRepository
import pl.krystiankaniowski.performance.notification.PreferencesKeys
import javax.inject.Inject

class SetShowTimeEnabledUseCase @Inject constructor(
    private val appSettingsRepository: AppSettingsRepository,
) {

    suspend operator fun invoke(value: Boolean) {
        appSettingsRepository.updateBoolean(PreferencesKeys.SHOW_TIME_ENABLED, value)
    }
}