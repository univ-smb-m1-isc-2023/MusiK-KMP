package com.github.enteraname74.event

/**
 * Events for the main screen.
 */
sealed interface MainScreenEvent {

    /**
     * Used to fetch all musics information.
     */
    data object FetchMusics: MainScreenEvent
}