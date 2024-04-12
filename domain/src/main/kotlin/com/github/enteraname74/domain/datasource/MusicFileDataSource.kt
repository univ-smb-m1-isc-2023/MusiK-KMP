package com.github.enteraname74.domain.datasource

import com.github.enteraname74.domain.model.File

interface MusicFileDataSource {
    suspend fun uploadFile(file: File)
}