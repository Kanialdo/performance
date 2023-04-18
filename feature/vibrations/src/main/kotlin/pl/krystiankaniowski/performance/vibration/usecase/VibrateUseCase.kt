package pl.krystiankaniowski.performance.vibration.usecase

import android.os.VibrationEffect
import android.os.Vibrator
import javax.inject.Inject

class VibrateUseCase @Inject constructor(
    private val vibrator: Vibrator?,
) {

    operator fun invoke() {
        vibrator?.vibrate(VibrationEffect.createOneShot(500, VibrationEffect.DEFAULT_AMPLITUDE))
    }
}