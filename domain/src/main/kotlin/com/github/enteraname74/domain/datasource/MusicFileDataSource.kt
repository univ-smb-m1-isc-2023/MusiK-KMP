package com.github.enteraname74.domain.datasource

import com.github.enteraname74.domain.model.File

/**
 * Data source for music file.
 */
interface MusicFileDataSource {

    /**
     * Upload a file, return true if the file was uploaded.
     */
    suspend fun uploadFile(file: File): Boolean
}