package pl.krystiankaniowski.performance.domain.timer

interface TimerObserver {

    val priority: Int

    suspend fun onStart()

    suspend fun onStop()
}