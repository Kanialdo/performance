package pl.krystiankaniowski.performance.sound.usecase

import kotlinx.coroutines.flow.first
import pl.krystiankaniowski.performance.domain.repository.AppSettingsRepository
import pl.krystiankaniowski.performance.sound.PreferencesKeys
import javax.inject.Inject

class IsSoundEnabledUseCase @Inject constructor(
    private val appSettingsRepository: AppSettingsRepository,
) {

    suspend operator fun invoke(): Boolean = appSettingsRepository.getBoolean(PreferencesKeys.IS_SOUND_ENABLED).first()
}