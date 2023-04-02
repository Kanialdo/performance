package pl.krystiankaniowski.performance.stats.usecase

import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Test
import pl.krystiankaniowski.performance.domain.usecase.SaveFocusUseCase

class GenerateHistoryUseCaseTest {

    private val saveFocusUseCase: SaveFocusUseCase = mockk()

    @Test
    fun `WHEN use case is invoked THEN enough number of sessions are stored`() = runTest {
        coEvery { saveFocusUseCase.invoke(any()) } answers {}
        val sut = createSut()

        sut.invoke()

        coVerify(exactly = 180) { saveFocusUseCase.invoke(any()) }
    }

    private fun createSut() = GenerateHistoryUseCase(saveFocusUseCase)
}