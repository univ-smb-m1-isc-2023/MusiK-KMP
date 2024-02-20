package com.github.enteraname74.state

import com.github.enteraname74.domain.model.Music
import com.github.enteraname74.strings.appStrings
import com.github.enteraname74.type.FetchingState

/**
 * State for the UI of the main screen.
 */
data class MainScreenState(
    val allMusicsState: FetchingState<List<Music>> = FetchingState.Loading(
        message = appStrings.fetchingAllMusics
    )
)
