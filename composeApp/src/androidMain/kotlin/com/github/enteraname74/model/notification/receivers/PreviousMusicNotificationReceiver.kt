package com.github.enteraname74.model.notification.receivers

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import com.github.enteraname74.model.PlaybackControllerImpl

/**
 * Receiver for playing the previous song in the queue.
 */
class PreviousMusicNotificationReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent?) {
        val intentForNotification = Intent(PlaybackControllerImpl.BROADCAST_NOTIFICATION)
        intentForNotification.putExtra(PlaybackControllerImpl.PREVIOUS, true)
        context.sendBroadcast(intentForNotification)
    }
}