package com.github.enteraname74.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Album
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import cafe.adriel.voyager.koin.getScreenModel
import cafe.adriel.voyager.navigator.tab.Tab
import cafe.adriel.voyager.navigator.tab.TabOptions
import com.github.enteraname74.Constants
import com.github.enteraname74.composable.StateView
import com.github.enteraname74.domain.model.AlbumPreview
import com.github.enteraname74.strings.appStrings
import com.github.enteraname74.type.FetchingState
import com.github.enteraname74.viewmodel.HomeScreenModel
import com.github.enteraname74.viewmodel.PlayerScreenModel
import kotlinx.coroutines.launch

object AlbumsTab : Tab {
    private fun readResolve(): Any = AlbumsTab

    override val options: TabOptions
        @Composable
        get() {
            val title = "Albums Tab"
            val icon = rememberVectorPainter(Icons.Rounded.Album)

            return remember {
                TabOptions(
                    index = 2u,
                    title = title,
                    icon = icon
                )
            }
        }

    @Composable
    override fun Content() {
        val coroutineScope = rememberCoroutineScope()

        val screenModel = getScreenModel<HomeScreenModel>()

        val playerScreenModel = getScreenModel<PlayerScreenModel>()

        val state by screenModel.state.collectAsState()

        Column(
            modifier = Modifier.fillMaxSize()
        ) {
            when (state.allAlbumsState) {
                is FetchingState.Error -> StateView(message = (state.allAlbumsState as FetchingState.Error).message)
                is FetchingState.Loading -> StateView(message = (state.allAlbumsState as FetchingState.Loading).message)
                is FetchingState.Success -> AllAlbumsView(
                    albums = (state.allAlbumsState as FetchingState.Success<List<AlbumPreview>>).data,
                    onClick = {
                        coroutineScope.launch {
                            // TODO
                        }
                    }
                )
            }
        }
    }
}

@Composable
private fun AllAlbumsView(
    albums: List<AlbumPreview>,
    onClick: (AlbumPreview) -> Unit
) {

    if (albums.isEmpty()) {
        StateView(
            message = appStrings.noAlbumsFound
        )
    } else {
        LazyColumn(
            contentPadding = PaddingValues(Constants.Spacing.large),
            verticalArrangement = Arrangement.spacedBy(Constants.Spacing.large)
        ) {
            items(albums) { album ->
                Text(album.albumName)
            }
        }
    }
}
