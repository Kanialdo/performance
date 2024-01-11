package pl.krystiankaniowski.performance.vibration

import android.content.Context
import android.os.Vibrator
import androidx.core.content.ContextCompat
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import dagger.multibindings.IntoSet
import pl.krystiankaniowski.performance.domain.settings.SettingsItemsProvider
import pl.krystiankaniowski.performance.domain.timer.TimerObserver
import pl.krystiankaniowski.performance.vibration.settings.VibrationSettingsProvider
import pl.krystiankaniowski.performance.vibration.timer.VibrationTimerObserver

@Module(includes = [VibrationModule.Bindings::class])
@InstallIn(SingletonComponent::class)
object VibrationModule {

    @Provides
    fun provideVibrator(@ApplicationContext context: Context): Vibrator? = ContextCompat.getSystemService(context, Vibrator::class.java)

    @Module
    @InstallIn(SingletonComponent::class)
    internal interface Bindings {

        @Binds
        @IntoSet
        fun bindVibrationSettingsProvider(impl: VibrationSettingsProvider): SettingsItemsProvider

        @Binds
        @IntoSet
        fun bindVibrationTimerObserver(impl: VibrationTimerObserver): TimerObserver
    }
}
