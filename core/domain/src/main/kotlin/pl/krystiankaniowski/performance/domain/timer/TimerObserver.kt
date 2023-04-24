package pl.krystiankaniowski.performance.domain.timer

import pl.krystiankaniowski.performance.model.Tag

interface TimerObserver {

    val priority: Int

    suspend fun onStart(tag: Tag)

    suspend fun onStop(isInterrupted: Boolean)
}