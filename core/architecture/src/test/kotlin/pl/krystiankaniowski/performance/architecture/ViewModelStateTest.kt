package pl.krystiankaniowski.performance.architecture

import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.TestScope
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test

@OptIn(ExperimentalCoroutinesApi::class)
class ViewModelStateTest {

    @Test
    fun getValue() = runTest {
        val state = State.DataState(value = 1)
        val sut = createSut(scope = this, initState = state)

        Assertions.assertEquals(state, sut.value)
    }

    @Test
    fun update() = runTest {
        val state1 = State.DataState(value = 1)
        val state2 = State.DataState(value = 2)
        val sut = createSut(scope = this, initState = state1)

        sut.update(state2)

        Assertions.assertEquals(state2, sut.value)
    }

    @Test
    fun testUpdate() = runTest {
        val state1 = State.DataState(value = 1)
        val state2 = State.DataState(value = 2)
        val sut = createSut(scope = this, initState = state1)

        sut.update { state2 }
        advanceUntilIdle()

        Assertions.assertEquals(state2, sut.value)
    }

    @Test
    fun updateIf() = runTest {
        val state1 = State.InitState
        val state2 = State.DataState(value = 2)
        val sut = createSut(scope = this, initState = state1)

        sut.updateIf<State.DataState> { state2 }

        Assertions.assertNotEquals(state2, sut.value)
    }

    @Test
    fun run() = runTest {
        val action = Runnable { throw IllegalStateException() }

        val sut = createSut(scope = this)

        Assertions.assertThrows(IllegalStateException::class.java) {
            sut.run { action.run() }
            advanceUntilIdle()
        }
    }

    @Test
    fun runIf() = runTest {
        val action = Runnable { throw IllegalStateException() }

        val sut = createSut(scope = this, initState = State.InitState)

        Assertions.assertDoesNotThrow {
            sut.runIf<State.DataState> { action.run() }
            advanceUntilIdle()
        }
    }

    sealed interface State {
        object InitState : State
        data class DataState(val value: Int) : State
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    private fun createSut(scope: TestScope, initState: State = State.InitState) : ViewModelState<State> = ViewModelState<State>(scope = scope, initState = initState)
}