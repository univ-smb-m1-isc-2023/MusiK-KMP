package com.github.enteraname74.model

import android.content.Context
import android.content.Intent
import android.media.MediaMetadata
import android.media.session.PlaybackState
import android.support.v4.media.MediaMetadataCompat
import android.support.v4.media.session.MediaSessionCompat
import android.support.v4.media.session.PlaybackStateCompat
import android.view.KeyEvent

/**
 * Manage media session lnked to the playback.
 */
class MediaSessionManager(
    private val context: Context,
    private val playbackController: PlaybackController
) {
    private var mediaSession: MediaSessionCompat = MediaSessionCompat(context, context.packageName + "MusikMediaSession")

    /**
     * Initialize the media session used by the player.
     */
    @Suppress("DEPRECATION")
    fun init() {
        mediaSession = MediaSessionCompat(context, context.packageName + "MusikMediaSession")

        mediaSession.setCallback(object : MediaSessionCompat.Callback() {
            override fun onSeekTo(pos: Long) {
                playbackController.seekToPosition(pos.toInt())
            }

            override fun onMediaButtonEvent(mediaButtonIntent: Intent): Boolean {
                val keyEvent = mediaButtonIntent.extras?.get(Intent.EXTRA_KEY_EVENT) as KeyEvent
                if (keyEvent.action == KeyEvent.ACTION_DOWN) {
                    when (keyEvent.keyCode) {
                        KeyEvent.KEYCODE_MEDIA_PAUSE, KeyEvent.KEYCODE_MEDIA_PLAY -> playbackController.togglePlayPause()
                    }
                }
                return super.onMediaButtonEvent(mediaButtonIntent)
            }

            override fun onPlay() {
                super.onPlay()
                playbackController.play()
            }

            override fun onPause() {
                super.onPause()
                playbackController.pause()
            }

            override fun onSkipToNext() {
                super.onSkipToNext()
                playbackController.next()
            }

            override fun onSkipToPrevious() {
                super.onSkipToPrevious()
                playbackController.previous()
            }
        })
        updateState()

        mediaSession.isActive = true
    }

    /**
     * Release all elements related to the media session.
     */
    fun release() {
        mediaSession.release()
    }

    /**
     * Update media session data with information of the playback manager.
     *
     * TODO: Take artwork into account.
     */
    fun updateMetadata(){
        mediaSession.setMetadata(
            MediaMetadataCompat.Builder()
                .putLong(
                    MediaMetadataCompat.METADATA_KEY_DURATION,
                    playbackController.getMusicDuration().toLong()
                )
                .putString(
                    MediaMetadata.METADATA_KEY_DISPLAY_TITLE,
                    playbackController.currentMusic?.name
                )
                .putLong(
                    MediaMetadata.METADATA_KEY_TRACK_NUMBER,
                    playbackController.getCurrentMusicPosition().toLong()
                )
                .putLong(
                    MediaMetadata.METADATA_KEY_NUM_TRACKS,
                    playbackController.playedList.size.toLong()
                )
                // Pour les vieilles versions d'android
                .putString(
                    MediaMetadata.METADATA_KEY_TITLE,
                    playbackController.currentMusic?.name
                )
                .putString(
                    MediaMetadata.METADATA_KEY_ARTIST,
                    playbackController.currentMusic?.artist
                )
                .build()
        )
    }

    /**
     * Update the state of the player's media session.
     */
    fun updateState() {
        val musicState = if (playbackController.isPlaying) {
            PlaybackState.STATE_PLAYING
        } else {
            PlaybackState.STATE_PAUSED
        }

        mediaSession.setPlaybackState(
            PlaybackStateCompat.Builder()
                .setActions(
                    PlaybackStateCompat.ACTION_PLAY
                            or PlaybackStateCompat.ACTION_SEEK_TO
                            or PlaybackStateCompat.ACTION_PAUSE
                            or PlaybackStateCompat.ACTION_SKIP_TO_NEXT
                            or PlaybackStateCompat.ACTION_SKIP_TO_PREVIOUS
                            or PlaybackStateCompat.ACTION_PLAY_PAUSE
                )
                .setState(
                    musicState,
                    playbackController.getCurrentMusicPosition().toLong(),
                    1.0F
                )
                .build()
        )
    }

    /**
     * Retrieve the token of the media session.
     */
    fun getToken(): MediaSessionCompat.Token = mediaSession.sessionToken
}