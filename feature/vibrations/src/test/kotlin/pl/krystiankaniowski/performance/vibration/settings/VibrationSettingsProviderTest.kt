package pl.krystiankaniowski.performance.vibration.settings

import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import pl.krystiankaniowski.performance.domain.localization.strings.StringsProvider
import pl.krystiankaniowski.performance.domain.settings.SettingsItem
import pl.krystiankaniowski.performance.testing.rule.InstantDispatcherExtension
import pl.krystiankaniowski.performance.vibration.usecase.IsVibrationEnabledUseCase
import pl.krystiankaniowski.performance.vibration.usecase.SetVibrationEnabledUseCase

@ExtendWith(InstantDispatcherExtension::class)
class VibrationSettingsProviderTest {

    private val stringsProvider: StringsProvider = mockk()
    private val isVibrationEnabledUseCase: IsVibrationEnabledUseCase = mockk()
    private val setVibrationEnabledUseCase: SetVibrationEnabledUseCase = mockk()

    @Test
    fun `WHEN sut is initialized THEN proper state is emitted`() = runTest {
        coEvery { stringsProvider.getString(any()) }.returns("")
        coEvery { isVibrationEnabledUseCase.invoke() }.returns(true)

        val sut = VibrationSettingsProvider(
            stringsProvider = stringsProvider,
            isVibrationEnabledUseCase = isVibrationEnabledUseCase,
            setVibrationEnabledUseCase = setVibrationEnabledUseCase,
        )

        with(sut.items.value.first() as SettingsItem.Switch) {
            Assertions.assertEquals(value, isVibrationEnabledUseCase())
        }
    }
}
