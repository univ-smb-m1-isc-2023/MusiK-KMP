package com.github.enteraname74.domain.datasource

import com.github.enteraname74.domain.model.Playlist

/**
 * Service for managing Playlists.
 */
interface PlaylistDataSource {

    /**
     * Retrieves all playlists information.
     */
    suspend fun getAll(): List<Playlist>

    /**
     * Retrieves a playlist.
     */
    suspend fun get(id: String): Playlist?

    /**
     * Creates a playlist.
     */
    suspend fun create(name: String): Playlist?

    /**
     * Adds a music to a playlist.
     */
    suspend fun addMusic(playlistId: String, musicId: String)

    /**
     * Removes a music from a playlist.
     */
    suspend fun removeMusic(playlistId: String, musicId: String)
}
