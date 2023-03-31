package pl.krystiankaniowski.performance.awake.usecase

import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import pl.krystiankaniowski.performance.awake.PreferencesKeys
import pl.krystiankaniowski.performance.domain.repository.AppSettingsRepository

internal class IsKeepAwakeEnabledUseCaseTest {

    private val appSettingsRepository: AppSettingsRepository = mockk()

    @Test
    fun `WHEN use case is invoked THEN value from app settings is returned`() = runTest {
        val value = true
        coEvery { appSettingsRepository.getBoolean(PreferencesKeys.KEEP_AWAKE_ENABLED) } returns flowOf(value)
        val sut = createSut()

        val result = sut.invoke()

        coVerify { appSettingsRepository.getBoolean(PreferencesKeys.KEEP_AWAKE_ENABLED) }
        Assertions.assertEquals(value, result)
    }

    private fun createSut() = IsKeepAwakeEnabledUseCase(appSettingsRepository)
}