package com.github.enteraname74.screens

import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.FabPosition
import androidx.compose.material.Scaffold
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.MusicNote
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import cafe.adriel.voyager.koin.getScreenModel
import cafe.adriel.voyager.navigator.tab.Tab
import cafe.adriel.voyager.navigator.tab.TabOptions
import com.github.enteraname74.Constants
import com.github.enteraname74.composable.MusicRow
import com.github.enteraname74.composable.StateView
import com.github.enteraname74.composable.UploadFabComposable
import com.github.enteraname74.di.injectElement
import com.github.enteraname74.domain.model.Music
import com.github.enteraname74.event.MainScreenEvent
import com.github.enteraname74.model.PlaybackController
import com.github.enteraname74.strings.appStrings
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

    @OptIn(ExperimentalMaterialApi::class)
    @Composable
    override fun Content() {
        val coroutineScope = rememberCoroutineScope()

        val screenModel = getScreenModel<HomeScreenModel>()

        val playerScreenModel = getScreenModel<PlayerScreenModel>()
        val playbackController = injectElement<PlaybackController>()

        val state by screenModel.state.collectAsState()

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
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
            ) {
                when (state.allMusicsState) {
                    is FetchingState.Error -> StateView(message = (state.allMusicsState as FetchingState.Error).message)
                    is FetchingState.Loading -> StateView(message = (state.allMusicsState as FetchingState.Loading).message)
                    is FetchingState.Success -> AllMusicsView(
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
                        }
                    )
                }
            }
        }
    }
}

@Composable
private fun AllMusicsView(
    musics: List<Music>,
    onClick: (Music) -> Unit
) {

    if (musics.isEmpty()) {
        StateView(
            message = appStrings.noMusicsFound
        )
    } else {
        LazyColumn {
            items(musics, key = {
                it.id
            }) { music ->
                MusicRow(
                    music = music,
                    onClick = {
                        onClick(it)
                    }
                )
            }
        }
    }
}
