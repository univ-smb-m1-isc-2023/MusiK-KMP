package com.github.enteraname74.domain.datasource

import com.github.enteraname74.domain.model.AlbumPreview

/**
 * Service for managing Albums.
 */
interface AlbumDataSource {

    /**
     * Retrieve all albums previews.
     */
    suspend fun getAll(): List<AlbumPreview>
}
