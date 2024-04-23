package com.github.enteraname74.screens

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.List
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import cafe.adriel.voyager.navigator.tab.Tab
import cafe.adriel.voyager.navigator.tab.TabOptions
import com.github.enteraname74.composable.Playlist


object PlaylistsTab : Tab {
    private fun readResolve(): Any = PlaylistsTab

    override val options: TabOptions
        @Composable
        get() {
            val title = "Playlists Tab"
            val icon = rememberVectorPainter(Icons.Rounded.List)

            return remember {
                TabOptions(
                    index = 1u,
                    title = title,
                    icon = icon
                )
            }
        }

    @Composable
    override fun Content() {
        LazyColumn {
            item {
                Playlist("Hoshi favourites songs", 12)
            }
            item {
                Playlist("Weird songs", 3)
            }
        }
    }
}
