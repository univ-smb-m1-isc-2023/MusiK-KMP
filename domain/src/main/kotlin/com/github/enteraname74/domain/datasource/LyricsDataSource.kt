package com.github.enteraname74.domain.datasource

/**
 * Data source for the lyrics.
 */
interface LyricsDataSource {

    /**
     * Fetch the lyrics of a song.
     */
    suspend fun fetchLyrics(musicId: String): String
}