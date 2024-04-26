package com.github.enteraname74.event

import com.github.enteraname74.domain.model.Music

/**
 * Events for the player screen.
 */
sealed interface PlayerScreenEvent {

    /**
     * Used for updating the view of the currently played song.
     */
    data class UpdatePlayedMusic(val music: Music?): PlayerScreenEvent

    /**
     * Used for showing is a music is being played.
     */
    data class UpdateIsPlaying(val isPlaying: Boolean): PlayerScreenEvent

    /**
     * Used to fetch the lyrics of the current music.
     */
    data object FetchLyrics: PlayerScreenEvent
}