package com.github.enteraname74.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.List
import androidx.compose.material3.FabPosition
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import cafe.adriel.voyager.koin.getScreenModel
import cafe.adriel.voyager.navigator.tab.LocalTabNavigator
import cafe.adriel.voyager.navigator.tab.Tab
import cafe.adriel.voyager.navigator.tab.TabOptions
import com.github.enteraname74.Constants
import com.github.enteraname74.composable.CreatePlaylistDialogComposable
import com.github.enteraname74.composable.CreatePlaylistFabComposable
import com.github.enteraname74.composable.Playlist
import com.github.enteraname74.composable.StateView
import com.github.enteraname74.domain.model.Playlist
import com.github.enteraname74.event.MainScreenEvent
import com.github.enteraname74.strings.appStrings
import com.github.enteraname74.theme.MusikColorTheme
import com.github.enteraname74.type.FetchingState
import com.github.enteraname74.viewmodel.HomeScreenModel
import com.github.enteraname74.viewmodel.PlayerScreenModel
import kotlinx.coroutines.launch


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

    @OptIn(ExperimentalMaterialApi::class)
    @Composable
    override fun Content() {
        val tabNavigator = LocalTabNavigator.current

        val coroutineScope = rememberCoroutineScope()

        val screenModel = getScreenModel<HomeScreenModel>()

        val playerScreenModel = getScreenModel<PlayerScreenModel>()

        val state by screenModel.state.collectAsState()

        val openDialog = remember { mutableStateOf(false) }

        Scaffold(
            modifier = Modifier
                .fillMaxSize(),
            containerColor = MusikColorTheme.colorScheme.primary,
            floatingActionButton = {
                CreatePlaylistFabComposable(
                    playerScreenSwipeableState = playerScreenModel.playerScreenSwipeableState,
                    onClick = {
                        openDialog.value = true
                    }
                )
            },
            floatingActionButtonPosition = FabPosition.End
        ) { paddingValues ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
            ) {
                if (openDialog.value) {
                    CreatePlaylistDialogComposable(onDismissRequest = {
                        openDialog.value = false
                    }, onConfirm = { playlistName ->
                        // TODO: Request playlist creation.
                        screenModel.onEvent(MainScreenEvent.CreatePlaylist(playlistName))

                        openDialog.value = false
                    })
                }

                when (state.allPlaylistsState) {
                    is FetchingState.Error -> StateView(message = (state.allPlaylistsState as FetchingState.Error).message)
                    is FetchingState.Loading -> StateView(message = (state.allPlaylistsState as FetchingState.Loading).message)
                    is FetchingState.Success -> AllPlaylistsView(
                        playlists = (state.allPlaylistsState as FetchingState.Success<List<Playlist>>).data,
                        onClick = { playlist ->
                            coroutineScope.launch {
                                println(playlist.title)

                                tabNavigator.current = PlaylistTab(playlist)
                            }
                        }
                    )
                }
            }
        }
    }

    @Composable
    private fun AllPlaylistsView(
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
                    Playlist(
                        playlist = playlist,
                        onClick = { onClick(playlist) }
                    )
                }
            }
        }
    }
}
