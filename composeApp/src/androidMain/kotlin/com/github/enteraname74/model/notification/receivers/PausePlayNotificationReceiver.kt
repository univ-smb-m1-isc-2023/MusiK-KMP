package com.github.enteraname74.model.notification.receivers

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import com.github.enteraname74.model.PlaybackControllerImpl

/**
 * Receiver for playing or pausing the current played song.
 */
class PausePlayNotificationReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent?) {
        val intentForNotification = Intent(PlaybackControllerImpl.BROADCAST_NOTIFICATION)
        intentForNotification.putExtra(PlaybackControllerImpl.TOGGLE_PLAY_PAUSE, true)
        context.sendBroadcast(intentForNotification)
    }
}