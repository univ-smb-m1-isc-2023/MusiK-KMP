package com.github.enteraname74.domain.model

data class Album(
    val name: String,
    val artist: String,
    val musics: List<Music>,
    val artworkUrl: String
)
