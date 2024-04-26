package com.github.enteraname74.model.notification.impl

import android.content.Context
import android.support.v4.media.session.MediaSessionCompat
import com.github.enteraname74.model.notification.MusikNotification

/**
 * Specification of a MusikNotification for Android 13 and above.
 */
class MusikNotificationAndroid13(
    context: Context,
    mediaSessionToken: MediaSessionCompat.Token
) : MusikNotification(
    context,
    mediaSessionToken,
) {

    override fun update(isPlaying: Boolean) {
        notificationBuilder
            .setStyle(
                androidx.media.app.NotificationCompat.MediaStyle()
                    .setMediaSession(mediaSessionToken)
            )
        notification = notificationBuilder.build()

        notificationManager.notify(CHANNEL_ID, notification)
    }
}