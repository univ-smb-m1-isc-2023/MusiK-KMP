package com.github.enteraname74.event

import com.github.enteraname74.domain.model.File

/**
 * Events for the main screen.
 */
sealed interface MainScreenEvent {

    /**
     * Used to fetch all musics information.
     */
    data object FetchMusics : MainScreenEvent

    data class UploadMusic(val file: File) : MainScreenEvent
}