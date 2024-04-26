package com.github.enteraname74.model

import com.github.enteraname74.domain.model.Music

/**
 * Represent the player used by the application.
 */
interface MusikPlayer {
    /**
     * Set a song to the player.
     */
    fun setMusic(music: Music)

    /**
     * Launch the loaded music of the player
     */
    fun launchMusic()

    /**
     * Play the loaded music.
     */
    fun play()

    /**
     * Pause the current music.
     */
    fun pause()

    /**
     * Pause or play the player.
     */
    fun togglePlayPause()

    /**
     * Seek to a given position in the current played music.
     */
    fun seekToPosition(position: Int)

    /**
     * Retrieve the current position in the current played song.
     * Returns 0 if no song is being played.
     */
    fun getMusicPosition(): Int

    /**
     * Check if the player is playing.
     */
    fun isPlaying(): Boolean

    /**
     * Release the player.
     */
    fun release()

    /**
     * Retrieve the current music duration.
     */
    fun getMusicDuration(): Int
}