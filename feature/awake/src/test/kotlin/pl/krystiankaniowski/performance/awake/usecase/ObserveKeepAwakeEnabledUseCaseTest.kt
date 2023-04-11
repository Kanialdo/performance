package pl.krystiankaniowski.performance.awake.usecase

import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.flow.emptyFlow
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Test
import pl.krystiankaniowski.performance.awake.PreferencesKeys
import pl.krystiankaniowski.performance.domain.repository.AppSettingsRepository

class ObserveKeepAwakeEnabledUseCaseTest {

    private val appSettingsRepository: AppSettingsRepository = mockk()

    @Test
    fun `WHEN use case is invoked THEN value is saved in app settings`() = runTest {
        coEvery { appSettingsRepository.getBoolean(any()) } returns emptyFlow()

        val sut = createSut()

        sut.invoke()

        coVerify { appSettingsRepository.getBoolean(PreferencesKeys.KEEP_AWAKE_ENABLED) }
    }

    private fun createSut() = ObserveKeepAwakeEnabledUseCase(appSettingsRepository)
}