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
import com.github.enteraname74.model.notification.MusikNotificationBuilder
import com.github.enteraname74.viewmodel.PlayerScreenViewModelImpl

/**
 * Implementation of the PlaybackController.
 * It manages the player, service, media session and notification.
 */
class PlaybackControllerImpl(
    private val context: Context,
): PlaybackController {
    override var initialList: ArrayList<Music> = ArrayList()
    override var playedList: ArrayList<Music> = ArrayList()
    override var currentMusic: Music? = null

    private var shouldLaunchService: Boolean = true
    private var shouldInit: Boolean = true

    private val mediaSessionManager = MediaSessionManager(
        context = context,
        playbackController = this
    )

    private val player: RemoteMusikPlayer = RemoteMusikPlayer(
        context = context,
        playbackController =  this
    )

    var playerViewModel: PlayerScreenViewModelImpl? = null

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

        val notification = MusikNotificationBuilder.buildNotification(
            context = context,
            mediaSessionToken = mediaSessionManager.getToken(),
        )
        notification.init(currentMusic)
        PlayerService.notification = notification
        shouldLaunchService = true
        shouldInit = false
    }

    /**
     * Retrieve the index of the current played music.
     * Return -1 if the current music is null or if it is not found in the current playlist
     */
    private fun getIndexOfCurrentMusic(): Int {
        return if (currentMusic == null) {
            -1
        } else {
            playedList.indexOf(playedList.find { it.id == currentMusic!!.id })
        }
    }

    /**
     * Retrieve the next music in the current playlist.
     * Return null if nothing is found.
     * Return the first music if we are at the end of the playlist.
     */
    private fun getNextMusic(currentIndex: Int): Music? {
        return if (playedList.isNotEmpty()) playedList[(currentIndex + 1) % playedList.size] else null
    }

    /**
     * Retrieve the previous music in the current playlist.
     * Return null if nothing is found.
     * Return the last music if we are at the start of the playlist.
     */
    private fun getPreviousMusic(currentIndex: Int): Music? {
        return if (playedList.isNotEmpty()) {
            if (currentIndex == 0) {
                playedList.last()
            } else {
                playedList[currentIndex - 1]
            }
        } else {
            null
        }
    }

    override val isPlaying: Boolean
        get() = player.isPlaying()

    override fun setPlayerLists(musics: List<Music>) {
        initialList = ArrayList(musics)
        playedList = ArrayList(musics)
    }

    override fun setAndPlayMusic(music: Music) {
        if (shouldInit) init()

        if (shouldLaunchService) {
            val serviceIntent = Intent(context, PlayerService::class.java)
            context.startForegroundService(serviceIntent)
            shouldLaunchService = false
        }

        currentMusic = music
        player.setMusic(music)
        player.launchMusic()
        update()
    }

    override fun togglePlayPause() {
        player.togglePlayPause()
        update()
    }

    override fun play() {
        player.play()
        update()
    }

    override fun pause() {
        player.pause()
        update()
    }

    override fun next() {
        val currentIndex = getIndexOfCurrentMusic()
        val nextMusic = getNextMusic(currentIndex)

        nextMusic?.let {
            player.pause()
            setAndPlayMusic(it)
        }

    }

    override fun previous() {
        val currentIndex = getIndexOfCurrentMusic()
        val previousMusic = getPreviousMusic(currentIndex)

        previousMusic?.let {
            player.pause()
            setAndPlayMusic(it)
        }
    }

    override fun seekToPosition(position: Int) {
        player.seekToPosition(position)
        update()
    }

    override fun getMusicDuration(): Int {
        return if (currentMusic == null) 0 else player.getMusicDuration()
    }

    override fun getCurrentMusicPosition(): Int = player.getMusicPosition()

    override fun stopPlayback() {
        if (shouldInit) return
        Log.d("PLAYBACK CONTROLLER", "STOP")

        context.unregisterReceiver(broadcastReceiver)
        player.release()
//        mediaSessionManager.release()

        PlayerService.notification?.release()
        val serviceIntent = Intent(context, PlayerService::class.java)
        context.stopService(serviceIntent)

        shouldInit = true
        playedList = ArrayList()
        initialList = ArrayList()
    }

    override fun update() {
        mediaSessionManager.updateMetadata()
        mediaSessionManager.updateState()
        PlayerService.notification?.update(isPlaying)
        playerViewModel?.handler?.onEvent(PlayerScreenEvent.UpdatePlayedMusic(
            music = currentMusic
        ))
        playerViewModel?.handler?.onEvent(PlayerScreenEvent.UpdateIsPlaying(
            isPlaying = player.isPlaying()
        ))
    }

    override fun skipAndRemoveCurrentSong() {
        if (currentMusic == null) return

        val currentIndex = getIndexOfCurrentMusic()
        val nextMusic = getNextMusic(currentIndex)

        playedList.removeIf{ it.id == currentMusic!!.id }
        initialList.removeIf { it.id == currentMusic!!.id }

        if (playedList.isEmpty()) stopPlayback()
        else {
            nextMusic?.let {
                setAndPlayMusic(it)
            }
        }
    }

    companion object {
        const val BROADCAST_NOTIFICATION = "MUSIK_BROADCAST_NOTIFICATION"

        const val STOP_RECEIVE = "STOP RECEIVE"
        const val NEXT = "NEXT"
        const val PREVIOUS = "NEXT"
        const val TOGGLE_PLAY_PAUSE = "TOGGLE PLAY PAUSE"
    }
}