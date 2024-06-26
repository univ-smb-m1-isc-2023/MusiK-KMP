package com.github.enteraname74.model

import android.content.Context
import android.media.AudioManager
import android.media.MediaPlayer
import android.net.Uri
import android.util.Log
import com.github.enteraname74.domain.model.Music
import com.github.enteraname74.remotedatasource.utils.ServerRoutes
import com.github.enteraname74.remotedatasource.utils.Token
import kotlinx.coroutines.runBlocking

class RemoteMusikPlayer(
    private val context: Context,
    private val playbackController: PlaybackController
) : MusikPlayer,
    MediaPlayer.OnCompletionListener,
    MediaPlayer.OnPreparedListener,
    MediaPlayer.OnErrorListener {

    private var player: MediaPlayer = MediaPlayer()
    private val audioManager: PlayerAudioManager = PlayerAudioManager(context, this)

    /**
     * Initialize the player (add listeners and set audio attributes).
     */
    fun init() {
        audioManager.init()
        player = MediaPlayer()
        player.apply {
            setAudioAttributes(audioManager.audioAttributes)
            setOnPreparedListener(this@RemoteMusikPlayer)
            setOnCompletionListener(this@RemoteMusikPlayer)
            setOnErrorListener(this@RemoteMusikPlayer)
        }
    }

    override fun setMusic(music: Music) {
        runBlocking {
            player.apply {
                stop()
                reset()
                val uri = Uri.parse(ServerRoutes.MusicFile.get(music.id))
                setDataSource(context, uri, mapOf("Authorization" to Token.value))
            }
        }
    }

    override fun launchMusic() {
        try {
            player.prepareAsync()
        } catch (_: IllegalStateException) {

        }
    }

    override fun play() {
        when (audioManager.requestAudioFocus()) {
            AudioManager.AUDIOFOCUS_GAIN -> {
                try {
                    player.start()
                    playbackController.update()
                } catch (_: IllegalStateException) {
                }
            }
            else -> {
                Log.d("PLAYER", "MISSING AUDIO MANAGER")
            }
        }
    }

    override fun pause() {
        try {
            audioManager.abandonAudioFocusRequest()
            player.pause()
            playbackController.update()
        } catch (_: IllegalStateException) {
        }
    }

    override fun togglePlayPause() {
        if (player.isPlaying) pause()
        else play()
    }

    override fun seekToPosition(position: Int) {
        try {
            player.seekTo(position)
            playbackController.update()
        } catch (_: IllegalStateException) {

        }
    }

    override fun isPlaying(): Boolean = player.isPlaying

    override fun release() {
        player.release()
        audioManager.release()
    }

    override fun getMusicPosition(): Int {
        return try {
            player.currentPosition
        } catch (e: IllegalStateException) {
            0
        }
    }

    override fun onCompletion(p0: MediaPlayer?) {
        playbackController.next()
    }

    override fun onPrepared(p0: MediaPlayer?) {
        when(audioManager.requestAudioFocus()) {
            AudioManager.AUDIOFOCUS_REQUEST_GRANTED -> {
                player.start()
                playbackController.update()
            }
        }
    }

    override fun onError(mp: MediaPlayer?, what: Int, extra: Int): Boolean {
        when(what) {
            MediaPlayer.MEDIA_ERROR_UNKNOWN -> playbackController.skipAndRemoveCurrentSong()
        }
        return true
    }

    override fun getMusicDuration(): Int = player.duration
}