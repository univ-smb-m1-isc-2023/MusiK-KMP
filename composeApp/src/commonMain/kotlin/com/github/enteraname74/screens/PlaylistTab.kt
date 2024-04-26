package com.github.enteraname74.screens

import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import cafe.adriel.voyager.koin.getScreenModel
import cafe.adriel.voyager.navigator.tab.LocalTabNavigator
import cafe.adriel.voyager.navigator.tab.Tab
import cafe.adriel.voyager.navigator.tab.TabOptions
<<<<<<< HEAD
import com.github.enteraname74.composable.PlaylistView
import com.github.enteraname74.domain.model.Playlist
=======
import com.github.enteraname74.Constants
import com.github.enteraname74.composable.AllMusicView
import com.github.enteraname74.composable.StateView
import com.github.enteraname74.di.injectElement
import com.github.enteraname74.domain.model.Music
import com.github.enteraname74.domain.model.Playlist
import com.github.enteraname74.event.MainScreenEvent
import com.github.enteraname74.model.PlaybackController
import com.github.enteraname74.theme.MusikColorTheme
import com.github.enteraname74.type.FetchingState
import com.github.enteraname74.type.PlayerScreenSheetStates
import com.github.enteraname74.viewmodel.HomeScreenModel
>>>>>>> feaf232 (Can now add and remove music from playlist)
import com.github.enteraname74.viewmodel.PlayerScreenModel

class PlaylistTab(
    private val playlist: Playlist
) : Tab {
    override val options: TabOptions
        @Composable
        get() {
            val title = "Playlist Tab"

            return remember {
                TabOptions(
                    index = 4u,
                    title = title,
                    icon = null
                )
            }
        }

    @OptIn(ExperimentalMaterialApi::class)
    @Composable
    override fun Content() {
        val playerScreenModel = getScreenModel<PlayerScreenModel>()
        val navigator = LocalTabNavigator.current

<<<<<<< HEAD
        PlaylistView(
            title = playlist.title,
            musics = playlist.musics,
            playerSwipeableState = playerScreenModel.playerScreenSwipeableState,
            onBack = { navigator.current = PlaylistsTab }
        )
=======
        val state by screenModel.state.collectAsState()

        val playbackController = injectElement<PlaybackController>()

        val myId = "inlineContent"

        val musicCountText = when (state.allPlaylistsState) {
            is FetchingState.Success ->
                buildAnnotatedString {
                    append((((state.allPlaylistsState as FetchingState.Success<List<Playlist>>).data).find { it.id == playlist.id }
                        ?: Playlist("", "", emptyList())).musics.size.toString())

                    appendInlineContent(myId, "[icon]")
                }

            else -> buildAnnotatedString {
                append("?")

                appendInlineContent(myId, "[icon]")
            }
        }

        val inlineContent = mapOf(
            Pair(
                myId,
                InlineTextContent(
                    Placeholder(
                        width = 20.sp,
                        height = 20.sp,
                        placeholderVerticalAlign = PlaceholderVerticalAlign.TextCenter
                    )
                ) {
                    Icon(Icons.Rounded.MusicNote, "", tint = MusikColorTheme.colorScheme.onPrimary)
                }
            )
        )

        Column(
            modifier = Modifier.fillMaxSize()
        ) {
            Text(
                modifier = Modifier.fillMaxWidth(),
                fontWeight = FontWeight.Bold,
                fontSize = 25.sp,
                textAlign = TextAlign.Center,
                color = MusikColorTheme.colorScheme.onPrimary,
                text = playlist.title
            )

            Text(
                modifier = Modifier.fillMaxWidth(),
                fontWeight = FontWeight.Bold,
                fontSize = 20.sp,
                textAlign = TextAlign.Center,
                color = MusikColorTheme.colorScheme.onPrimary,
                text = musicCountText,
                inlineContent = inlineContent,
            )

            when (state.allPlaylistsState) {
                is FetchingState.Error -> StateView(message = (state.allPlaylistsState as FetchingState.Error).message)
                is FetchingState.Loading -> StateView(message = (state.allPlaylistsState as FetchingState.Loading).message)
                is FetchingState.Success -> AllMusicView(
                    music = (((state.allPlaylistsState as FetchingState.Success<List<Playlist>>).data).find { it.id == playlist.id }
                        ?: Playlist("", "", emptyList())).musics,
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
                        screenModel.onEvent(
                            MainScreenEvent.RemoveMusicFromPlaylist(
                                playlist.id,
                                music.id
                            )
                        )
                    }
                )
            }
        }
>>>>>>> feaf232 (Can now add and remove music from playlist)
    }
}
