package com.github.enteraname74.model.notification.impl

import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.support.v4.media.session.MediaSessionCompat
import com.github.enteraname74.R
import com.github.enteraname74.model.notification.MusikNotification
import com.github.enteraname74.model.notification.receivers.NextMusicNotificationReceiver
import com.github.enteraname74.model.notification.receivers.PausePlayNotificationReceiver
import com.github.enteraname74.model.notification.receivers.PreviousMusicNotificationReceiver

/**
 * Specification of a MusikNotification for devices below Android 13.
 */
class MusikNotificationBelowAndroid13(
    context: Context,
    mediaSessionToken: MediaSessionCompat.Token
) : MusikNotification(
    context,
    mediaSessionToken
) {

    private val previousMusicIntent: PendingIntent = PendingIntent.getBroadcast(
        context,
        2,
        Intent(context, PreviousMusicNotificationReceiver::class.java),
        PendingIntent.FLAG_IMMUTABLE
    )

    private val pausePlayIntent: PendingIntent = PendingIntent.getBroadcast(
        context,
        3,
        Intent(context, PausePlayNotificationReceiver::class.java),
        PendingIntent.FLAG_IMMUTABLE
    )

    private val nextMusicIntent: PendingIntent = PendingIntent.getBroadcast(
        context,
        4,
        Intent(context, NextMusicNotificationReceiver::class.java),
        PendingIntent.FLAG_IMMUTABLE
    )

    override fun update(isPlaying: Boolean) {
        val pausePlayIcon = if (isPlaying) {
            R.drawable.ic_pause
        } else {
            R.drawable.ic_play_arrow
        }

        notificationBuilder
            .clearActions()
            .addAction(R.drawable.ic_skip_previous,"previous",previousMusicIntent)
            .addAction(pausePlayIcon,"pausePlay",pausePlayIntent)
            .addAction(R.drawable.ic_skip_next,"next",nextMusicIntent)
            .setStyle(
                androidx.media.app.NotificationCompat.MediaStyle()
                    .setShowActionsInCompactView(0,1,2)
                    .setMediaSession(mediaSessionToken)
            )
        notification = notificationBuilder.build()

        notificationManager.notify(CHANNEL_ID, notification)
    }
}