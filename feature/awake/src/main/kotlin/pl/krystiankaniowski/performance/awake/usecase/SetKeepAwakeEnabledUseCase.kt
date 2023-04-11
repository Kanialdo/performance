package pl.krystiankaniowski.performance.awake.usecase

import pl.krystiankaniowski.performance.awake.PreferencesKeys
import pl.krystiankaniowski.performance.domain.repository.AppSettingsRepository
import javax.inject.Inject

class SetKeepAwakeEnabledUseCase @Inject constructor(
    private val appSettingsRepository: AppSettingsRepository,
) {

    suspend operator fun invoke(value: Boolean) {
        appSettingsRepository.updateBoolean(PreferencesKeys.KEEP_AWAKE_ENABLED, value)
    }
}