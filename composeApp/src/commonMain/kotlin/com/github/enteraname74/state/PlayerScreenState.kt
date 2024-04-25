package com.github.enteraname74.state

import com.github.enteraname74.domain.model.Music
import com.github.enteraname74.strings.appStrings
import com.github.enteraname74.type.FetchingState

/**
 * State for the UI of the player screen.
 */
data class PlayerScreenState(
    val currentMusic: Music? = null,
    val isPlaying: Boolean = false,
    val lyrics: FetchingState<String> = FetchingState.Loading(message = appStrings.fetchingLyrics)
)
