package com.github.enteraname74.domain.datasource

import com.github.enteraname74.domain.model.Album
import com.github.enteraname74.domain.model.AlbumPreview

/**
 * Service for managing Albums.
 */
interface AlbumDataSource {

    /**
     * Retrieve all albums previews.
     */
    suspend fun getAll(): List<AlbumPreview>

    /**
     * Get an album by its name and artist.
     */
    suspend fun getByNameAndArtist(albumName: String, artist: String): Album?
}
