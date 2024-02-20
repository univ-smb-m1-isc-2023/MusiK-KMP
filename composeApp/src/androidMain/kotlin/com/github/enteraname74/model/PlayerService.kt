package com.github.enteraname74.model

import android.app.Service
import android.content.Intent
import android.os.IBinder
import com.github.enteraname74.model.notification.MusikNotification

/**
 * Service used for the playback.
 * Holds the responsibility of updating the notification.
 */
class PlayerService : Service(){
    override fun onBind(p0: Intent?): IBinder? {
        return null
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {

        notification?.let {
            startForeground(MusikNotification.CHANNEL_ID, it.getNotification())
        }

        return START_STICKY
    }

    override fun onTaskRemoved(rootIntent: Intent?) {
        super.onTaskRemoved(rootIntent)
        val serviceIntent = Intent(this, PlayerService::class.java)
        stopService(serviceIntent)
    }

    override fun onDestroy() {
        super.onDestroy()
        stopForeground(STOP_FOREGROUND_REMOVE)
    }

    companion object {
        var notification: MusikNotification? = null
    }
}