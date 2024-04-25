package com.github.enteraname74.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Person
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import cafe.adriel.voyager.koin.getScreenModel
import cafe.adriel.voyager.navigator.tab.LocalTabNavigator
import cafe.adriel.voyager.navigator.tab.Tab
import cafe.adriel.voyager.navigator.tab.TabOptions
import com.github.enteraname74.Constants
import com.github.enteraname74.composable.PlaylistRow
import com.github.enteraname74.composable.StateView
import com.github.enteraname74.domain.model.ArtistPreview
import com.github.enteraname74.strings.appStrings
import com.github.enteraname74.type.FetchingState
import com.github.enteraname74.viewmodel.HomeScreenModel
import com.github.enteraname74.viewmodel.PlayerScreenModel
import kotlinx.coroutines.launch

object ArtistsTab : Tab {
    private fun readResolve(): Any = ArtistsTab

    override val options: TabOptions
        @Composable
        get() {
            val title = "Artist Tab"
            val icon = rememberVectorPainter(Icons.Rounded.Person)

            return remember {
                TabOptions(
                    index = 3u,
                    title = title,
                    icon = icon
                )
            }
        }

    @Composable
    override fun Content() {
        val coroutineScope = rememberCoroutineScope()
        val tabNavigator = LocalTabNavigator.current

        val screenModel = getScreenModel<HomeScreenModel>()

        val state by screenModel.state.collectAsState()

        Column(
            modifier = Modifier.fillMaxSize()
        ) {
            when (state.allArtistsState) {
                is FetchingState.Error -> StateView(message = (state.allArtistsState as FetchingState.Error).message)
                is FetchingState.Loading -> StateView(message = (state.allArtistsState as FetchingState.Loading).message)
                is FetchingState.Success -> AllArtistsView(
                    artists = (state.allArtistsState as FetchingState.Success<List<ArtistPreview>>).data,
                    onClick = { artist ->
                        coroutineScope.launch {
                            val selectedArtist = screenModel.getArtist(
                                artistName = artist.name
                            )
                            selectedArtist?.let { foundArtist ->
                                tabNavigator.current = ArtistTab(foundArtist)
                            }
                        }
                    }
                )
            }
        }
    }
}

@Composable
private fun AllArtistsView(
    artists: List<ArtistPreview>,
    onClick: (ArtistPreview) -> Unit
) {

    if (artists.isEmpty()) {
        StateView(
            message = appStrings.noArtistsFound
        )
    } else {
        LazyColumn(
            contentPadding = PaddingValues(Constants.Spacing.large),
            verticalArrangement = Arrangement.spacedBy(Constants.Spacing.large)
        ) {
            items(artists) { artist ->
                PlaylistRow(
                    title = artist.name,
                    total = artist.totalSongs,
                    artworkUrl = artist.artworkUrl,
                    onClick = {
                        onClick(artist)
                    }
                )
            }
        }
    }
}
