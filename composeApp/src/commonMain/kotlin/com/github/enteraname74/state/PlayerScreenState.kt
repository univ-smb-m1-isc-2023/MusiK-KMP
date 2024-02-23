package com.github.enteraname74.state

import com.github.enteraname74.domain.model.Music

/**
 * State for the UI of the player screen.
 */
data class PlayerScreenState(
    val currentMusic: Music? = null,
    val currentMusicDuration: Int = 0,
    val isPlaying: Boolean = false,
    val currentPositionInMusic: Int = 0
)
