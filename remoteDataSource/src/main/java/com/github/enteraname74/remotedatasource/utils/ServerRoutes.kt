package com.github.enteraname74.remotedatasource.utils

/**
 * Routes used to communicate with the server.
 */
object ServerRoutes {
    private const val SERVER_IP = "192.168.144.141"
    private const val SERVER_PORT = "8080"
    private const val SERVER_ADDRESS = "http://$SERVER_IP:$SERVER_PORT"

    /**
     * Routes concerning musics information.
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

    /**
     * Routes concerning musics information.
     */
    object MusicFile {
        private const val MAIN_ROUTE = "$SERVER_ADDRESS/music/file"

        /**
         * Build a route for retrieving a music from its id.
         *
         * @param id the id of the music to retrieve.
         */
        fun get(id: String) = "$MAIN_ROUTE/$id"
    }
}