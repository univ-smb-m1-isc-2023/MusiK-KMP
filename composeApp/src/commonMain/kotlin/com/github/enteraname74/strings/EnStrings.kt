package com.github.enteraname74.strings

/**
 * English implementation of the application strings.
 */
object EnStrings : ApplicationStrings {
    override val welcome = "Welcome to Musik!"

    /****** Fetching strings ****/
    override val fetchingAllMusics = "Fetching all music..."
    override val noMusicsFound = "No music found!"
    override val fetchingAllPlaylists = "Fetching all playlists..."
    override val noPlaylistsFound = "No playlists found!"
    override val fetchingAllAlbums = "Fetching all albums..."
    override val noAlbumsFound = "No albums found!"
    override val fetchingAllArtists = "Fetching all artists..."
    override val noArtistsFound = "No artist found!"
    override val fetchingLyrics = "Fetching lyrics..."
    override val noLyricsFound = "No lyrics found!"

    /**** Search screen strings ******/
    override val allMusicsPlaceholder = "Search for a song"

    /**** User screen strings ******/
    override val username = "Username"
    override val password = "Password"
    override val connect = "Connection"

    /**** Player screen ****/
    override val lyrics = "Lyrics"
    override val close = "Close"

    /**** Playlists strings *****/
    override val createPlaylist = "Create a playlist"
    override val playlistName = "Playlist name"
    override val create = "Create"
}
