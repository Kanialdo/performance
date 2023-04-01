package pl.krystiankaniowski.performance.awake.usecase

import kotlinx.coroutines.flow.Flow
import pl.krystiankaniowski.performance.awake.PreferencesKeys
import pl.krystiankaniowski.performance.domain.repository.AppSettingsRepository
import javax.inject.Inject

class ObserveKeepAwakeEnabledUseCase @Inject constructor(
    private val appSettingsRepository: AppSettingsRepository,
) {

    operator fun invoke(): Flow<Boolean> = appSettingsRepository.getBoolean(PreferencesKeys.KEEP_AWAKE_ENABLED)
}