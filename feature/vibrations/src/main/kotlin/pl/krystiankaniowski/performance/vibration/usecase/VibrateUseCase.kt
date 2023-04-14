package pl.krystiankaniowski.performance.vibration.usecase

import android.content.Context
import android.os.Build
import android.os.VibrationEffect
import android.os.Vibrator
import androidx.core.content.ContextCompat.getSystemService
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class VibrateUseCase @Inject constructor(
    @ApplicationContext private val context: Context,
) {

    operator fun invoke() {
        val vibrator = getSystemService(context, Vibrator::class.java)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            vibrator?.vibrate(VibrationEffect.createOneShot(500, VibrationEffect.DEFAULT_AMPLITUDE));
        } else {
            //deprecated in API 26
            vibrator?.vibrate(500);
        }
    }
}