package pl.krystiankaniowski.performance.vibration.usecase

import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Test
import pl.krystiankaniowski.performance.domain.repository.AppSettingsRepository
import pl.krystiankaniowski.performance.vibration.PreferencesKeys

class SetVibrationEnabledUseCaseTest {

    private val appSettingsRepository: AppSettingsRepository = mockk()

    @Test
    fun `WHEN use case is invoked THEN value is saved in app settings`() = runTest {
        coEvery { appSettingsRepository.updateBoolean(any(), any()) } answers {}

        val value = true
        val sut = createSut()

        sut.invoke(value)

        coVerify { appSettingsRepository.updateBoolean(PreferencesKeys.IS_VIBRATION_ENABLED, value) }
    }

    private fun createSut() = SetVibrationEnabledUseCase(appSettingsRepository)
}