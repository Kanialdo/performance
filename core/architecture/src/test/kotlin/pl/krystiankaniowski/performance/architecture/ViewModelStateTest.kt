package pl.krystiankaniowski.performance.architecture

import io.mockk.Runs
import io.mockk.every
import io.mockk.just
import io.mockk.mockk
import io.mockk.verify
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test

@OptIn(ExperimentalCoroutinesApi::class)
class ViewModelStateTest {

    @Test
    fun `Test if update works`() = runTest {
        val state1 = State.DataState(value = 1)
        val state2 = State.DataState(value = 2)
        val sut = createSut(initState = state1)

        sut.update(state2)

        Assertions.assertEquals(state2, sut.value)
    }

    @Test
    fun `Test if transform works`() = runTest {
        val state1 = State.DataState(value = 1)
        val state2 = State.DataState(value = 2)
        val sut = createSut(initState = state1)

        sut.transform { state2 }

        Assertions.assertEquals(state2, sut.value)
    }

    @Test
    fun `Test if transformCoroutine works`() = runTest {
        val state1 = State.DataState(value = 1)
        val state2 = State.DataState(value = 2)
        val sut = createSut(initState = state1)

        sut.transform(this) { state2 }
        advanceUntilIdle()

        Assertions.assertEquals(state2, sut.value)
    }

    @Test
    fun `Test if transformIf works`() = runTest {
        val state1 = State.InitState
        val state2 = State.DataState(value = 2)
        val sut = createSut(initState = state1)

        sut.transformIf<State.DataState> { state2 }

        Assertions.assertNotEquals(state2, sut.value)
    }

    @Test
    fun `Test if transformIfCoroutine works`() = runTest {
        val state1 = State.InitState
        val state2 = State.DataState(value = 2)
        val sut = createSut(initState = state1)

        sut.transformIf<State.DataState>(this) { state2 }
        advanceUntilIdle()

        Assertions.assertNotEquals(state2, sut.value)
    }

    @Test
    fun `Test if runCoroutine works`() = runTest {
        val action = mockk<Runnable>()
        every { action.run() } just Runs

        val sut = createSut()

        sut.run(this) { action.run() }
        advanceUntilIdle()

        verify { action.run() }
    }

    @Test
    fun `Test if runIf works`() = runTest {
        val action = mockk<Runnable>()

        val sut = createSut(initState = State.InitState)

        sut.runIf<State.DataState> { action.run() }

        verify(exactly = 0) { action.run() }
    }

    @Test
    fun `Test if runIfCoroutine works`() = runTest {
        val action = mockk<Runnable>()

        val sut = createSut(initState = State.InitState)

        sut.runIf<State.DataState>(this) { action.run() }
        advanceUntilIdle()

        verify(exactly = 0) { action.run() }
    }

    sealed interface State {
        object InitState : State
        data class DataState(val value: Int) : State
    }

    private fun createSut(initState: State = State.InitState): MutableStateFlow<State> = MutableStateFlow(initState)
}