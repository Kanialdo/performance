package pl.krystiankaniowski.performance.timer.usecase

import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import pl.krystiankaniowski.performance.model.toSeconds

class GetCancelThresholdUseCaseImplTest {

    @Test
    fun `WHEN use case is invoked THEN return 15 seconds`() {
        val sut = createSut()

        val result = sut()

        Assertions.assertEquals(15.toSeconds(), result)
    }

    private fun createSut() = GetCancelThresholdUseCaseImpl()
}