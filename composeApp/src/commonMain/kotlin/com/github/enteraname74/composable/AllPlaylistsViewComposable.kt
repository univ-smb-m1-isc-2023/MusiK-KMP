package com.github.enteraname74.composable

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import com.github.enteraname74.Constants
import com.github.enteraname74.domain.model.Playlist
import com.github.enteraname74.strings.appStrings

@Composable
fun AllPlaylistsView(
    playlists: List<Playlist>,
    onClick: (Playlist) -> Unit
) {
    if (playlists.isEmpty()) {
        StateView(
            message = appStrings.noPlaylistsFound
        )
    } else {
        LazyColumn(
            contentPadding = PaddingValues(Constants.Spacing.large),
            verticalArrangement = Arrangement.spacedBy(Constants.Spacing.large)
        ) {
            items(playlists, key = {
                it.id
            }) { playlist ->
                PlaylistRow(
                    title = playlist.title,
                    musics = playlist.musics,
                    onClick = { onClick(playlist) }
                )
            }
        }
    }
}
