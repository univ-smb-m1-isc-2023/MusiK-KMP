package com.github.enteraname74.state

import com.github.enteraname74.domain.model.AlbumPreview
import com.github.enteraname74.domain.model.ArtistPreview
import com.github.enteraname74.domain.model.Music
import com.github.enteraname74.domain.model.Playlist
import com.github.enteraname74.strings.appStrings
import com.github.enteraname74.type.FetchingState

/**
 * State for the UI of the main screen.
 */
data class MainScreenState(
    val allMusicsState: FetchingState<List<Music>> = FetchingState.Loading(
        message = appStrings.fetchingAllMusics
    ),
    val allPlaylistsState: FetchingState<List<Playlist>> = FetchingState.Loading(
        message = appStrings.fetchingAllPlaylists
    ),
    val allAlbumsState: FetchingState<List<AlbumPreview>> = FetchingState.Loading(
        message = appStrings.fetchingAllAlbums
    ),
    val allArtistsState: FetchingState<List<ArtistPreview>> = FetchingState.Loading(
        message = appStrings.fetchingAllArtists
    )
)
