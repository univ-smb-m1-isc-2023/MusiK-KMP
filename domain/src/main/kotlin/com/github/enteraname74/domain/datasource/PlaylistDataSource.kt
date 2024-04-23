package com.github.enteraname74.domain.datasource

import com.github.enteraname74.domain.model.Playlist

/**
 * Service for managing Playlists.
 */
interface PlaylistDataSource {

    /**
     * Retrieve all playlists information.
     */
    suspend fun getAll(): List<Playlist>
}
