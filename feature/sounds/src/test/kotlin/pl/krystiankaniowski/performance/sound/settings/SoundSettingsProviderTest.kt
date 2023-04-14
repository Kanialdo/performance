package pl.krystiankaniowski.performance.sound.settings

import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import pl.krystiankaniowski.performance.domain.localization.strings.StringsProvider
import pl.krystiankaniowski.performance.domain.settings.SettingsItem
import pl.krystiankaniowski.performance.sound.usecase.IsSoundEnabledUseCase
import pl.krystiankaniowski.performance.sound.usecase.SetSoundEnabledUseCase
import pl.krystiankaniowski.performance.testing.rule.InstantDispatcherExtension

@ExtendWith(InstantDispatcherExtension::class)
class SoundSettingsProviderTest {

    private val stringsProvider: StringsProvider = mockk()
    private val isSoundEnabledUseCase: IsSoundEnabledUseCase = mockk()
    private val setSoundEnabledUseCase: SetSoundEnabledUseCase = mockk()

    @Test
    fun `WHEN sut is initialized THEN proper list of options is emitted`() = runTest {
        coEvery { stringsProvider.getString(any()) }.returns("")
        coEvery { isSoundEnabledUseCase.invoke() }.returns(true)

        val sut = SoundSettingsProvider(
            stringsProvider = stringsProvider,
            isSoundEnabledUseCase = isSoundEnabledUseCase,
            setSoundEnabledUseCase = setSoundEnabledUseCase,
        )

        with(sut.items.value.first() as SettingsItem.Switch) {
            Assertions.assertEquals(value, isSoundEnabledUseCase())
        }
    }
}
