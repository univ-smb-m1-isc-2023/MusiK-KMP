package com.github.enteraname74.screens

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import com.github.enteraname74.composable.LoadingView
import com.github.enteraname74.composable.MusicRow
import com.github.enteraname74.domain.model.Music
import com.github.enteraname74.event.MainScreenEvent
import com.github.enteraname74.type.FetchingState
import com.github.enteraname74.viewmodel.MainScreenViewModel

@Composable
fun MainScreen(
    viewModel: MainScreenViewModel
) {
    val state by viewModel.handler.state.collectAsState()

    LaunchedEffect(key1 = null) {
        viewModel.handler.onEvent(MainScreenEvent.FetchMusics)
    }

    when(state.allMusicsState) {
        is FetchingState.Error -> LoadingView(message = (state.allMusicsState as FetchingState.Error).message)
        is FetchingState.Loading -> LoadingView(message = (state.allMusicsState as FetchingState.Loading).message)
        is FetchingState.Success -> AllMusicsView(musics = (state.allMusicsState as FetchingState.Success<List<Music>>).data)
    }
}

@Composable
private fun AllMusicsView(musics: List<Music>) {
    LazyColumn {
        items(musics, key = {
            it.id
        }) {
            MusicRow(
                music = it,
                onClick = {}
            )
        }
    }
}