package com.github.enteraname74.model

import android.annotation.SuppressLint
import android.app.Service
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Build
import android.os.IBinder
import android.support.v4.media.session.MediaSessionCompat
import android.util.Log
import com.github.enteraname74.model.notification.MusikNotification
import com.github.enteraname74.model.notification.MusikNotificationBuilder

/**
 * Service used for the playback.
 * Holds the responsibility of updating the notification.
 */
@SuppressLint("UnspecifiedRegisterReceiverFlag")
class PlayerService : Service() {
    private var notification: MusikNotification? = null

    private val broadcastReceiver: BroadcastReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context, intent: Intent) {
            if (intent.extras?.getBoolean(UPDATE_WITH_PLAYING_STATE) != null) {
                val isPlaying = intent.extras!!.getBoolean(UPDATE_WITH_PLAYING_STATE)
                notification?.update(isPlaying)
            }
        }
    }

    override fun onBind(p0: Intent?): IBinder? {
        return null
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {

        val token: MediaSessionCompat.Token? = intent?.extras?.get(MEDIA_SESSION_TOKEN) as MediaSessionCompat.Token?

        if (Build.VERSION.SDK_INT >= 33) {
            applicationContext.registerReceiver(
                broadcastReceiver,
                IntentFilter(SERVICE_BROADCAST),
                Context.RECEIVER_NOT_EXPORTED
            )
        } else {
            applicationContext.registerReceiver(
                broadcastReceiver,
                IntentFilter(SERVICE_BROADCAST)
            )
        }

        token?.let {
            notification = MusikNotificationBuilder.buildNotification(
                context = this,
                mediaSessionToken = token
            )
            notification!!.init(null)
            startForeground(MusikNotification.CHANNEL_ID, notification!!.getPlaybackNotification())
        }

        return START_STICKY
    }

    override fun onTaskRemoved(rootIntent: Intent?) {
        super.onTaskRemoved(rootIntent)
        Log.d("SERVICE", "TASK REMOVED")
        notification?.release()

        val serviceIntent = Intent(this, PlayerService::class.java)
        this.unregisterReceiver(broadcastReceiver)
        stopService(serviceIntent)
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d("SERVICE", "DESTROY")
        stopForeground(STOP_FOREGROUND_REMOVE)
    }

    companion object {
        const val SERVICE_BROADCAST = "SERVICE_BROADCAST"
        const val MEDIA_SESSION_TOKEN = "TOKEN"
        const val UPDATE_WITH_PLAYING_STATE = "UPDATE"
    }
}