package pl.krystiankaniowski.performance.sound.usecase

import pl.krystiankaniowski.performance.domain.repository.AppSettingsRepository
import pl.krystiankaniowski.performance.sound.PreferencesKeys
import javax.inject.Inject

class SetSoundEnabledUseCase @Inject constructor(
    private val appSettingsRepository: AppSettingsRepository,
) {

    suspend operator fun invoke(value: Boolean) {
        appSettingsRepository.updateBoolean(PreferencesKeys.IS_SOUND_ENABLED, value)
    }
}