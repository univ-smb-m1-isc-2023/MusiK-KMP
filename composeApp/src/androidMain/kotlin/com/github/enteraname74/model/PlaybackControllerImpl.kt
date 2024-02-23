package com.github.enteraname74.model

import android.annotation.SuppressLint
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Build
import android.util.Log
import com.github.enteraname74.domain.model.Music
import com.github.enteraname74.event.PlayerScreenEvent
import com.github.enteraname74.viewmodel.PlayerScreenViewModel

/**
 * Implementation of the PlaybackController.
 * It manages the player, service, media session and notification.
 */
class PlaybackControllerImpl(
    private val context: Context,
): PlaybackController() {
    private var shouldLaunchService: Boolean = true
    private var shouldInit: Boolean = true

    private val mediaSessionManager = MediaSessionManager(
        context = context,
        playbackController = this
    )

    override val player: RemoteMusikPlayer = RemoteMusikPlayer(
        context = context,
        playbackController =  this
    )

    override var playerViewModel: PlayerScreenViewModel? = null

    private val broadcastReceiver: BroadcastReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context, intent: Intent) {
            Log.d("RECEIVE", "RECEIVE")
            if (intent.extras?.getBoolean(STOP_RECEIVE) != null) {
                context.unregisterReceiver(this)
            } else if (intent.extras?.getBoolean(NEXT) != null) {
                next()
            } else if (intent.extras?.getBoolean(PREVIOUS) != null) {
                previous()
            } else if(intent.extras?.getBoolean(TOGGLE_PLAY_PAUSE) != null) {
                togglePlayPause()
            }
        }
    }

    init {
        init()
    }

    /**
     * Initialize the playback manager.
     */
    @SuppressLint("UnspecifiedRegisterReceiverFlag")
    private fun init() {
        Log.d("PLAYBACK CONTROLLER", "INIT")
        if (Build.VERSION.SDK_INT >= 33) {
            context.registerReceiver(
                broadcastReceiver,
                IntentFilter(BROADCAST_NOTIFICATION),
                Context.RECEIVER_NOT_EXPORTED
            )
        } else {
            context.registerReceiver(
                broadcastReceiver,
                IntentFilter(BROADCAST_NOTIFICATION)
            )
        }

        player.init()
        mediaSessionManager.init()
        shouldLaunchService = true
        shouldInit = false
    }

    override fun setAndPlayMusic(music: Music) {
        Log.d("CONTROLLER", "SET AND PLAY MUSIC")
        if (shouldInit) init()

        if (shouldLaunchService) {
            val serviceIntent = Intent(context, PlayerService::class.java)
            serviceIntent.putExtra(PlayerService.MEDIA_SESSION_TOKEN, mediaSessionManager.getToken())
            context.startForegroundService(serviceIntent)
            shouldLaunchService = false
        }

        _currentMusic = music
        player.setMusic(music)
        player.launchMusic()
        update()
    }

    override fun stopPlayback() {
        if (shouldInit) return

        releaseDurationJob()

        context.unregisterReceiver(broadcastReceiver)
        player.release()
        mediaSessionManager.release()

        val serviceIntent = Intent(context, PlayerService::class.java)
        context.stopService(serviceIntent)

        shouldInit = true
        _playedList = ArrayList()
        _initialList = ArrayList()
    }

    override fun update() {
        mediaSessionManager.updateMetadata()
        mediaSessionManager.updateState()

        if (durationJob == null) {
            launchDurationJob()
        }

        val intentForUpdatingNotification = Intent(PlayerService.SERVICE_BROADCAST)
        intentForUpdatingNotification.putExtra(PlayerService.UPDATE_WITH_PLAYING_STATE, isPlaying)
        context.sendBroadcast(intentForUpdatingNotification)

        playerViewModel?.handler?.onEvent(PlayerScreenEvent.UpdatePlayedMusic(
            music = _currentMusic,
            duration = player.getMusicDuration()
        ))
        playerViewModel?.handler?.onEvent(PlayerScreenEvent.UpdateIsPlaying(
            isPlaying = isPlaying
        ))
    }

    companion object {
        const val BROADCAST_NOTIFICATION = "MUSIK_BROADCAST_NOTIFICATION"

        const val STOP_RECEIVE = "STOP RECEIVE"
        const val NEXT = "NEXT"
        const val PREVIOUS = "NEXT"
        const val TOGGLE_PLAY_PAUSE = "TOGGLE PLAY PAUSE"
    }
}