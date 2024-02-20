package com.github.enteraname74.domain.datasource

import com.github.enteraname74.domain.model.Music

/**
 * Service for managing Musics.
 */
interface MusicInformationDataSource {

    /**
     * Retrieve all musics information.
     */
    fun getAll(): List<Music>
}