package pl.krystiankaniowski.performance.vibration.settings

import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import pl.krystiankaniowski.performance.domain.localization.strings.StringsProvider
import pl.krystiankaniowski.performance.domain.settings.SettingsItem
import pl.krystiankaniowski.performance.testing.rule.InstantDispatcherExtension
import pl.krystiankaniowski.performance.vibration.usecase.IsVibrationEnabledUseCase
import pl.krystiankaniowski.performance.vibration.usecase.IsVibratorAvailableUseCase
import pl.krystiankaniowski.performance.vibration.usecase.SetVibrationEnabledUseCase

@ExtendWith(InstantDispatcherExtension::class)
class VibrationSettingsProviderTest {

    private val stringsProvider: StringsProvider = mockk()
    private val isVibratorAvailableUseCase: IsVibratorAvailableUseCase = mockk()
    private val isVibrationEnabledUseCase: IsVibrationEnabledUseCase = mockk()
    private val setVibrationEnabledUseCase: SetVibrationEnabledUseCase = mockk()

    @Test
    fun `WHEN sut is initialized and vibrator is available THEN proper list of options is emitted`() = runTest {
        coEvery { stringsProvider.getString(any()) }.returns("")
        coEvery { isVibratorAvailableUseCase.invoke() }.returns(true)
        coEvery { isVibrationEnabledUseCase.invoke() }.returns(true)

        val sut = createSut()

        with(sut.items.value.first() as SettingsItem.Switch) {
            Assertions.assertEquals(value, isVibrationEnabledUseCase())
        }
    }

    @Test
    fun `WHEN sut is initialized and vibrator is not available THEN empty list is emitted`() = runTest {
        coEvery { stringsProvider.getString(any()) }.returns("")
        coEvery { isVibratorAvailableUseCase.invoke() }.returns(false)

        val sut = createSut()

        coVerify(exactly = 0) { isVibrationEnabledUseCase() }

        Assertions.assertTrue(sut.items.value.isEmpty())
    }

    private fun createSut() = VibrationSettingsProvider(
        stringsProvider = stringsProvider,
        isVibratorAvailableUseCase = isVibratorAvailableUseCase,
        isVibrationEnabledUseCase = isVibrationEnabledUseCase,
        setVibrationEnabledUseCase = setVibrationEnabledUseCase,
    )
}
