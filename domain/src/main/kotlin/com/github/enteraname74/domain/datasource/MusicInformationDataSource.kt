package com.github.enteraname74.domain.datasource

import com.github.enteraname74.domain.model.Music

/**
 * Data source for music information.
 */
interface MusicInformationDataSource {

    /**
     * Retrieve all musics information.
     */
    suspend fun getAll(): List<Music>
}