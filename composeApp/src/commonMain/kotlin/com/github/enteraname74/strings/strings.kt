package com.github.enteraname74.strings

import androidx.compose.ui.text.intl.Locale

/**
 * The application's strings, adapted from the current language used on the device.
 */
val appStrings = when (Locale.current.language) {
    "fr" -> FrStrings
    else -> EnStrings
}

/**
 * Strings used for the applications.
 */
interface ApplicationStrings {
    val appName: String get() = "MusiK"
    val welcome: String


    /***** Fetching strings ******/
    val fetchingAllMusics: String
    val noMusicsFound: String
    val fetchingAllPlaylists: String
    val noPlaylistsFound: String
    val fetchingAllAlbums: String
    val noAlbumsFound: String
    val fetchingAllArtists: String
    val noArtistsFound: String
    val fetchingLyrics: String
    val noLyricsFound: String


    /**** Search screen strings *****/
    val allMusicsPlaceholder: String

    /**** User screen strings *****/
    val username: String
    val password: String
    val connect: String

    /**** Player screen ****/
    val lyrics: String
    val close: String
}