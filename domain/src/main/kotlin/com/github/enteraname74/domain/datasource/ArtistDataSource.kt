package com.github.enteraname74.domain.datasource

import com.github.enteraname74.domain.model.Artist
import com.github.enteraname74.domain.model.ArtistPreview

/**
 * Service for managing Artists.
 */
interface ArtistDataSource {

    /**
     * Retrieves all artists previews.
     */
    suspend fun getAll(): List<ArtistPreview>

    /**
     * Get an artist by its name.
     */
    suspend fun getByName(name: String): Artist?
}
