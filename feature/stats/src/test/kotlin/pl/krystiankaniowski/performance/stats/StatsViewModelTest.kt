package pl.krystiankaniowski.performance.stats

import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import pl.krystiankaniowski.performance.testing.rule.InstantDispatcherExtension

@ExtendWith(InstantDispatcherExtension::class)
class StatsViewModelTest {

    @Test
    fun `WHEN view model is initialized THEN proper state is emitted`() = runTest {
        val sut = createSut()

        Assertions.assertEquals(
            sut.state.value,
            StatsViewModel.State.UnderDevelopment,
        )
    }

    private fun createSut() = StatsViewModel()
}