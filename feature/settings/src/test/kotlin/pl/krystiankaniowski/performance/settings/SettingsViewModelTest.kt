package pl.krystiankaniowski.performance.settings

import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import pl.krystiankaniowski.performance.domain.usecase.dnd.IsDoNotDisturbEnabledUseCase
import pl.krystiankaniowski.performance.domain.usecase.dnd.SetDoNotDisturbEnabledUseCase
import pl.krystiankaniowski.performance.testing.rule.InstantDispatcherExtension

@ExtendWith(InstantDispatcherExtension::class)
class SettingsViewModelTest {

    private val applicationVersion: String = ""
    private val isDoNotDisturbEnabledUseCase: IsDoNotDisturbEnabledUseCase = mockk()
    private val setDoNotDisturbEnabledUseCase: SetDoNotDisturbEnabledUseCase = mockk()

    @Test
    fun `WHEN view model is initialized THEN proper state is emitted`() = runTest {
        coEvery { isDoNotDisturbEnabledUseCase.invoke() }.returns(true)

        val viewModel = SettingsViewModel(
            applicationVersion = applicationVersion,
            isDoNotDisturbEnabledUseCase = isDoNotDisturbEnabledUseCase,
            setDoNotDisturbEnabledUseCase = setDoNotDisturbEnabledUseCase,
        )

        Assertions.assertEquals(
            viewModel.state.value,
            SettingsViewModel.State.Loaded(
                appVersion = applicationVersion,
                isDndEnabled = true,
            ),
        )
    }

    @Test
    fun `WHEN do not disturb option is changed THEN proper state is emitted`() = runTest {
        coEvery { isDoNotDisturbEnabledUseCase.invoke() }.returns(false)

        val viewModel = SettingsViewModel(
            applicationVersion = applicationVersion,
            isDoNotDisturbEnabledUseCase = isDoNotDisturbEnabledUseCase,
            setDoNotDisturbEnabledUseCase = setDoNotDisturbEnabledUseCase,
        )

        viewModel.onDndChanged(true)

        coVerify { isDoNotDisturbEnabledUseCase.invoke() }
        coVerify { setDoNotDisturbEnabledUseCase.invoke(true) }
    }
}