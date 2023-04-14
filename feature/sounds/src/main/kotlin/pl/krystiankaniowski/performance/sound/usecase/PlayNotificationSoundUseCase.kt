package pl.krystiankaniowski.performance.sound.usecase

import android.content.Context
import android.media.MediaPlayer
import android.media.RingtoneManager
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class PlayNotificationSoundUseCase @Inject constructor(
    @ApplicationContext private val context: Context,
) {

    operator fun invoke() {
        val alarmSound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)
        val mediaPlayer = MediaPlayer.create(context, alarmSound)
        mediaPlayer.start()
    }
}