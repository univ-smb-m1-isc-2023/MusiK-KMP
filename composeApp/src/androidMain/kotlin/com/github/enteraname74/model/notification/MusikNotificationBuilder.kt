package com.github.enteraname74.model.notification

import android.content.Context
import android.os.Build
import android.support.v4.media.session.MediaSessionCompat
import com.github.enteraname74.model.notification.impl.MusikNotificationAndroid13
import com.github.enteraname74.model.notification.impl.MusikNotificationBelowAndroid13

/**
 * Utils for building the playback notification.
 */
object MusikNotificationBuilder {

    /**
     * Build a MusikNotification depending on the device's SDK.
     */
    fun buildNotification(
        context: Context,
        mediaSessionToken: MediaSessionCompat.Token
    ) : MusikNotification {
        return if (Build.VERSION.SDK_INT >= 33) {
            MusikNotificationAndroid13(
                context = context,
                mediaSessionToken = mediaSessionToken,
            )
        } else {
            MusikNotificationBelowAndroid13(
                context = context,
                mediaSessionToken = mediaSessionToken,
            )
        }
    }
}