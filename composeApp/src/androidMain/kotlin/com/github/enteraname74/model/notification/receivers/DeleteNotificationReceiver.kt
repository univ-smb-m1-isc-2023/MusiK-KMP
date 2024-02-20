package com.github.enteraname74.model.notification.receivers

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import com.github.enteraname74.model.PlaybackControllerImpl
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

/**
 * Receiver for deleting the notification.
 */
class DeleteNotificationReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent?) {
        CoroutineScope(Dispatchers.IO).launch {
            val intentForNotification = Intent(PlaybackControllerImpl.BROADCAST_NOTIFICATION)
            intentForNotification.putExtra(PlaybackControllerImpl.STOP_RECEIVE, true)
            context.sendBroadcast(intentForNotification)
        }
    }
}