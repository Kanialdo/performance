package pl.krystiankaniowski.performance.awake.usecase

import kotlinx.coroutines.flow.first
import pl.krystiankaniowski.performance.awake.PreferencesKeys
import pl.krystiankaniowski.performance.domain.repository.AppSettingsRepository
import javax.inject.Inject

class IsKeepAwakeEnabledUseCase @Inject constructor(
    private val appSettingsRepository: AppSettingsRepository,
) {

    suspend operator fun invoke(): Boolean = appSettingsRepository.getBoolean(PreferencesKeys.KEEP_AWAKE_ENABLED).first()
}