package com.github.enteraname74.event

import com.github.enteraname74.domain.model.File

/**
 * Events for the main screen.
 */
sealed interface MainScreenEvent {

    /**
     * Used to fetch all music information.
     */
    data object FetchMusics : MainScreenEvent

    data object FetchPlaylists : MainScreenEvent

    data object FetchAlbums : MainScreenEvent

    data object FetchArtists : MainScreenEvent

    data class UploadMusic(val file: File) : MainScreenEvent

    data class CreatePlaylist(val name: String) : MainScreenEvent
}
