package com.github.enteraname74.screens

import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.FabPosition
import androidx.compose.material.Scaffold
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.MusicNote
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import cafe.adriel.voyager.koin.getScreenModel
import cafe.adriel.voyager.navigator.tab.Tab
import cafe.adriel.voyager.navigator.tab.TabOptions
import com.github.enteraname74.Constants
import com.github.enteraname74.composable.AllMusicView
import com.github.enteraname74.composable.AllPlaylistsView
import com.github.enteraname74.composable.StateView
import com.github.enteraname74.composable.UploadFabComposable
import com.github.enteraname74.di.injectElement
import com.github.enteraname74.domain.model.Music
import com.github.enteraname74.domain.model.Playlist
import com.github.enteraname74.event.MainScreenEvent
import com.github.enteraname74.model.PlaybackController
import com.github.enteraname74.theme.MusikColorTheme
import com.github.enteraname74.type.FetchingState
import com.github.enteraname74.type.PlayerScreenSheetStates
import com.github.enteraname74.viewmodel.HomeScreenModel
import com.github.enteraname74.viewmodel.PlayerScreenModel
import kotlinx.coroutines.launch

object MusicTab : Tab {
    private fun readResolve(): Any = MusicTab

    override val options: TabOptions
        @Composable
        get() {
            val title = "Music Tab"
            val icon = rememberVectorPainter(Icons.Rounded.MusicNote)

            return remember {
                TabOptions(
                    index = 0u,
                    title = title,
                    icon = icon
                )
            }
        }

    @OptIn(ExperimentalMaterialApi::class, ExperimentalMaterial3Api::class)
    @Composable
    override fun Content() {
        val coroutineScope = rememberCoroutineScope()

        val screenModel = getScreenModel<HomeScreenModel>()

        val playerScreenModel = getScreenModel<PlayerScreenModel>()
        val playbackController = injectElement<PlaybackController>()

        val state by screenModel.state.collectAsState()

        val openModal = remember { mutableStateOf(false) }

        val bottomModalSheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)

        val musicToAddToPlaylist: MutableState<Music?> = remember { mutableStateOf(null) }

        Scaffold(
            modifier = Modifier
                .fillMaxSize(),
            backgroundColor = MusikColorTheme.colorScheme.primary,
            floatingActionButton = {
                UploadFabComposable(
                    playerScreenSwipeableState = playerScreenModel.playerScreenSwipeableState,
                    uploadFile = { file ->
                        screenModel.onEvent(MainScreenEvent.UploadMusic(file))
                    }
                )
            },
            floatingActionButtonPosition = FabPosition.End
        ) { paddingValues ->
            if (openModal.value) {
                ModalBottomSheet(
                    containerColor = MusikColorTheme.colorScheme.secondary,
                    sheetState = bottomModalSheetState,
                    onDismissRequest = {
                        musicToAddToPlaylist.value = null

                        openModal.value = false
                    }) {
                    when (state.allPlaylistsState) {
                        is FetchingState.Error -> StateView(message = (state.allPlaylistsState as FetchingState.Error).message)
                        is FetchingState.Loading -> StateView(message = (state.allPlaylistsState as FetchingState.Loading).message)
                        is FetchingState.Success -> AllPlaylistsView(
                            playlists = (state.allPlaylistsState as FetchingState.Success<List<Playlist>>).data,
                            onClick = { playlist ->
                                coroutineScope.launch {
                                    musicToAddToPlaylist.value?.let { music ->
                                        println("MusicID: ${music.id}")

                                        screenModel.onEvent(
                                            MainScreenEvent.AddMusicToPlaylist(
                                                playlist.id,
                                                music.id
                                            )
                                        )
                                    }

                                    openModal.value = false
                                }
                            }
                        )
                    }
                }
            }

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
            ) {
                when (state.allMusicsState) {
                    is FetchingState.Error -> StateView(message = (state.allMusicsState as FetchingState.Error).message)
                    is FetchingState.Loading -> StateView(message = (state.allMusicsState as FetchingState.Loading).message)
                    is FetchingState.Success -> AllMusicView(
                        musics = (state.allMusicsState as FetchingState.Success<List<Music>>).data,
                        onClick = {
                            coroutineScope.launch {
                                playerScreenModel.playerScreenSwipeableState.animateTo(
                                    targetValue = PlayerScreenSheetStates.EXPANDED,
                                    anim = tween(Constants.AnimationDuration.normal)
                                )
                                playbackController.setPlayerLists((state.allMusicsState as FetchingState.Success<List<Music>>).data)
                                playbackController.setAndPlayMusic(it)
                            }
                        },
                        onLongClick = { music ->
                            musicToAddToPlaylist.value = music

                            openModal.value = true
                        }
                    )
                }
            }
        }
    }
}
