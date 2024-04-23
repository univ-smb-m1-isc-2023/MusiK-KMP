package com.github.enteraname74.composable.search

import androidx.compose.animation.core.tween
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.SwipeableState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.focus.FocusManager
import com.github.enteraname74.Constants
import com.github.enteraname74.composable.MusicRow
import com.github.enteraname74.composable.PlayerSpacer
import com.github.enteraname74.di.injectElement
import com.github.enteraname74.domain.model.Music
import com.github.enteraname74.model.PlaybackController
import com.github.enteraname74.type.PlayerScreenSheetStates
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun SearchMusics(
    playerScreenSwipeableState: SwipeableState<PlayerScreenSheetStates>,
    searchText: String,
    musics: List<Music>,
    playbackController: PlaybackController = injectElement(),
    focusManager: FocusManager,
) {
    val coroutineScope = rememberCoroutineScope()

    LazyColumn {
        val foundedMusics = musics.filter {
            it.name.lowercase().contains(searchText.lowercase())
                    || it.artist.lowercase().contains(searchText.lowercase())
                    || it.album.lowercase().contains(searchText.lowercase())

        }

        if (foundedMusics.isNotEmpty()) {
            items(foundedMusics) { music ->
                MusicRow(
                    music = music,
                    onClick = { selectedMusic ->
                        coroutineScope.launch {
                            focusManager.clearFocus()
                            playerScreenSwipeableState.animateTo(
                                PlayerScreenSheetStates.EXPANDED,
                                tween(Constants.AnimationDuration.normal)
                            )
                        }.invokeOnCompletion { _ ->
                            playbackController.setPlayerLists(foundedMusics)
                            playbackController.setAndPlayMusic(selectedMusic)
                        }
                    }
                )
            }
        }
        item {
            PlayerSpacer()
        }
    }
}
