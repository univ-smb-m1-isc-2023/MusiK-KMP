package com.github.enteraname74.screens

import androidx.compose.animation.core.tween
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.SwipeableState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import com.github.enteraname74.Constants
import com.github.enteraname74.composable.LoadingView
import com.github.enteraname74.composable.MusicRow
import com.github.enteraname74.domain.model.Music
import com.github.enteraname74.event.MainScreenEvent
import com.github.enteraname74.model.PlaybackController
import com.github.enteraname74.type.FetchingState
import com.github.enteraname74.type.PlayerScreenSheetStates
import com.github.enteraname74.viewmodel.MainScreenViewModel
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun MainScreen(
    viewModel: MainScreenViewModel,
    playbackController: PlaybackController,
    playerScreenSwipeableState: SwipeableState<PlayerScreenSheetStates>
) {
    val coroutineScope = rememberCoroutineScope()
    val state by viewModel.handler.state.collectAsState()

    LaunchedEffect(key1 = null) {
        viewModel.handler.onEvent(MainScreenEvent.FetchMusics)
    }

    when(state.allMusicsState) {
        is FetchingState.Error -> LoadingView(message = (state.allMusicsState as FetchingState.Error).message)
        is FetchingState.Loading -> LoadingView(message = (state.allMusicsState as FetchingState.Loading).message)
        is FetchingState.Success -> AllMusicsView(
            musics = (state.allMusicsState as FetchingState.Success<List<Music>>).data,
            onClick = {
                coroutineScope.launch {
                    playbackController.setPlayerLists((state.allMusicsState as FetchingState.Success<List<Music>>).data)
                    playbackController.setAndPlayMusic(it)
                    playerScreenSwipeableState.animateTo(
                        targetValue = PlayerScreenSheetStates.EXPANDED,
                        anim = tween(Constants.AnimationDuration.normal)
                    )
                }
            }
        )
    }
}

@Composable
private fun AllMusicsView(
    musics: List<Music>,
    onClick: (Music) -> Unit
) {
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