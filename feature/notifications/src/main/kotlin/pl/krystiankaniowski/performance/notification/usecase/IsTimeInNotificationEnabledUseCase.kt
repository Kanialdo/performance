package pl.krystiankaniowski.performance.notification.usecase

import kotlinx.coroutines.flow.first
import pl.krystiankaniowski.performance.domain.repository.AppSettingsRepository
import pl.krystiankaniowski.performance.notification.PreferencesKeys
import javax.inject.Inject

class IsTimeInNotificationEnabledUseCase @Inject constructor(
    private val appSettingsRepository: AppSettingsRepository,
) {

    suspend operator fun invoke(): Boolean = appSettingsRepository.getBoolean(PreferencesKeys.IS_TIME_IN_NOTIFICATION_ENABLED).first()
}