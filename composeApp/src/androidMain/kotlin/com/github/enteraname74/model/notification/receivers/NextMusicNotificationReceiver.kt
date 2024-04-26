package com.github.enteraname74.model.notification.receivers

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import com.github.enteraname74.model.PlaybackControllerImpl

/**
 * Receiver for playing the next song in the queue.
 */
class NextMusicNotificationReceiver: BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent?) {
        val intentForNotification = Intent(PlaybackControllerImpl.BROADCAST_NOTIFICATION)
        intentForNotification.putExtra(PlaybackControllerImpl.NEXT, true)
        context.sendBroadcast(intentForNotification)
    }
}