package com.github.enteraname74.remotedatasource.utils

/**
 * Routes used to communicate with the server.
 */
object ServerRoutes {
//    private const val SERVER_IP = "192.168.68.158" // "musik.oups.net"
//    private const val SERVER_PORT = "8080" // "443"
    private const val SERVER_IP = "musik.oups.net" // "musik.oups.net"
    private const val SERVER_PORT = "443" // "443"
    private const val SERVER_ADDRESS = "https://$SERVER_IP:$SERVER_PORT"

    /**
     * Routes concerning music information.
     */
    object MusicInformation {
        private const val MAIN_ROUTE = "$SERVER_ADDRESS/music/information"

        const val ALL = "$MAIN_ROUTE/all"

        /**
         * Build a route for retrieving a music from its id.
         *
         * @param id the id of the music to retrieve.
         */
        fun get(id: String) = "$MAIN_ROUTE/$id"

        /**
         * Build a route for deleting a music from its id.
         *
         * @param id the id of the music to delete.
         */
        fun delete(id: String) = "$MAIN_ROUTE/$id"
    }

    object Lyrics {
        private const val MAIN_ROUTE = "$SERVER_ADDRESS/lyrics"

        /**
         * Build a route for retrieving the lyrics of a song.
         *
         * @param id the id of the song.
         */
        fun get(id: String) = "${MAIN_ROUTE}/$id"
    }

    /**
     * Routes concerning music file.
     */
    object MusicFile {
        private const val MAIN_ROUTE = "$SERVER_ADDRESS/music/file"
        const val UPLOAD = "$MAIN_ROUTE/upload"

        /**
         * Build a route for retrieving a music from its id.
         *
         * @param id the id of the music to retrieve.
         */
        fun get(id: String) = "$MAIN_ROUTE/$id"
    }

    /**
     * Routes concerning users.
     */
    object User {
        private const val MAIN_ROUTE = "$SERVER_ADDRESS/user"
        const val connect = MAIN_ROUTE
    }

    /**
     * Routes concerning authentication.
     */
    object Auth {
        private const val MAIN_ROUTE = "$SERVER_ADDRESS/auth"
        const val auth = MAIN_ROUTE
    }

    object Playlist {
        const val MAIN_ROUTE = "$SERVER_ADDRESS/playlist"

        const val ALL = "$MAIN_ROUTE/all"

        /**
         * Builds a route for retrieving a playlist from its id.
         *
         * @param id the id of the playlist to retrieve.
         */
        fun get(id: String) = "$MAIN_ROUTE/$id"

        fun addMusic(playlistId: String, musicId: String) =
            "$MAIN_ROUTE/add/$playlistId/$musicId"

        fun removeMusic(playlistId: String, musicId: String) =
            "$MAIN_ROUTE/remove/$playlistId/$musicId"
    }

    object Album {
        private const val MAIN_ROUTE = "$SERVER_ADDRESS/album"

        const val ALL = "${MAIN_ROUTE}/all"

        /**
         * Builds a route for retrieving an Album from its name and artist.
         */
        fun get(name: String, artist: String) = "${MAIN_ROUTE}/$name/$artist"
    }

    object Artist {
        private const val MAIN_ROUTE = "$SERVER_ADDRESS/artist"

        const val ALL = "${MAIN_ROUTE}/all"

        /**
         * Builds a route for retrieving an Artist from its name.
         */
        fun get(name: String) = "${MAIN_ROUTE}/$name"
    }
}
