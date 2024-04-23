package com.github.enteraname74.domain.datasource

import com.github.enteraname74.domain.model.ArtistPreview

/**
 * Service for managing Artists.
 */
interface ArtistDataSource {

    /**
     * Retrieves all artists previews.
     */
    suspend fun getAll(): List<ArtistPreview>
}
