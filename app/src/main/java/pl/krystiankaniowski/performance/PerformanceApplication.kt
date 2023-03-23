package pl.krystiankaniowski.performance

import android.app.Application
import dagger.hilt.android.HiltAndroidApp
import pl.krystiankaniowski.performance.domain.Initializer
import javax.inject.Inject

@HiltAndroidApp
class PerformanceApplication : Application() {

    @Inject
    lateinit var initializers: Set<@JvmSuppressWildcards Initializer>

    override fun onCreate() {
        super.onCreate()
        initializers.forEach { it.init() }
    }
}