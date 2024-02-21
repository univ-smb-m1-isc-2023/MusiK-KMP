package com.github.enteraname74.model

import com.github.enteraname74.domain.model.Music


/**
 * Used to manage the music playback of the application.
 */
interface PlaybackController{

    /**
     * The initial list, used by the playback manager.
     */
    var initialList: ArrayList<Music>

    /**
     * The played list, used by the player and modified by the user (adding song...).
     */
    var playedList: ArrayList<Music>

    /**
     * The currently played music.
     */
    var currentMusic: Music?

    /**
     * Check if the player is currently playing a song.
     * If the player is not defined yet, it will return false.
     */
    val isPlaying: Boolean

    /**
     * Set the lists used by the player (played and initial list).
     */
    fun setPlayerLists(musics: List<Music>)

    /**
     * Set and play a Music.
     */
    fun setAndPlayMusic(music: Music)

    /**
     * Play or pause the player, depending on its current state.
     */
    fun togglePlayPause()

    /**
     * Play the current song.
     */
    fun play()

    /**
     * Pause the current song.
     */
    fun pause()

    /**
     * Play the next song in queue.
     */
    fun next()

    /**
     * Play the previous song in queue.
     */
    fun previous()

    /**
     * Seek to a given position in the current played music.
     */
    fun seekToPosition(position: Int)

    /**
     * Retrieve the current played music duration.
     * Return 0 if no music is playing.
     */
    fun getMusicDuration(): Int

    /**
     * Retrieve the current position in the current played music.
     * Return 0 if nothing is being played.
     */
    fun getCurrentMusicPosition(): Int

    /**
     * Stop the playback.
     * Should stop the playback and release unnecessary data.
     */
    fun stopPlayback()

    /**
     * Use to update itself.
     */
    fun update()

    /**
     * Use to remove the current song from all the lists and play the next one.
     * Used if there is a problem with the playback of the current song.
     */
    fun skipAndRemoveCurrentSong()
}