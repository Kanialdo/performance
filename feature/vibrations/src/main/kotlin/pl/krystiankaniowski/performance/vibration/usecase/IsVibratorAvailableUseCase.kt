package pl.krystiankaniowski.performance.vibration.usecase

import android.os.Vibrator
import javax.inject.Inject

class IsVibratorAvailableUseCase @Inject constructor(
    private val vibrator: Vibrator?,
) {

    operator fun invoke(): Boolean = vibrator != null
}